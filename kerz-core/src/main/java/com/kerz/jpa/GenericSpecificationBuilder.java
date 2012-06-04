package com.kerz.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class GenericSpecificationBuilder<T>
{
	@PersistenceContext
	private EntityManager m_entityManager;
	private Logger m_log = LoggerFactory.getLogger(GenericSpecificationBuilder.class);
	private Class<T> m_persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericSpecificationBuilder()
	{
		m_persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public Specification<T> matchesExample(final T example)
	{
		Assert.notNull(m_entityManager, "entity manager required");
		return new Specification<T>()
		{
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
			{
				Predicate result = null;
				// Root<T> root = criteriaQuery.from(m_persistentClass);
				Metamodel metaModel = m_entityManager.getMetamodel();
				ManagedType<T> managedType = metaModel.managedType(m_persistentClass);
				m_log.debug("managed type={}", managedType.getJavaType());
				Set<SingularAttribute<? super T, ?>> attrs = managedType.getSingularAttributes();
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (attrs != null)
				{
					for (SingularAttribute<? super T, ?> attr : attrs)
					{
						m_log.debug("attr: member={}", attr.getJavaMember());
						if (attr.getPersistentAttributeType() == Attribute.PersistentAttributeType.BASIC)
						{
							Member member = attr.getJavaMember();
							if (member instanceof Field)
							{
								Field field = (Field) member;
								Object value = null;
								try
								{
									value = field.get(example);
								}
								catch (Throwable t)
								{
									throw new RuntimeException(t);
								}
								if (value != null)
								{
									if (field.getType() == String.class)
									{
										String stringValue = (String) value;
										
										if (StringUtils.hasText(stringValue))
										{
											@SuppressWarnings("unchecked")
											Path<String> path = (Path<String>) root.get(attr);
											Predicate predicate = criteriaBuilder.like(path, stringValue);
											m_log.debug("adding string like predicate: value={}", stringValue);
											predicates.add(predicate);
										}
										else
										{
											m_log.debug("string value has no text, skipping...");
										}
									}
								}
								else
								{
									m_log.debug("attr value is null, skipping...");
								}
							}
							else
							{
								m_log.debug("attr not field, skipping...");
							}
						}
						else
						{
							m_log.debug("attr not basic, skipping...");
						}
					}
					
					// predicate list populated
					Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);
					result = criteriaBuilder.and(predicateArray);
				}
				return result;
			}
		};
	}
}
