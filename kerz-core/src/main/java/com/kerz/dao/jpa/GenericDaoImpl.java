package com.kerz.dao.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
import org.springframework.stereotype.Repository;

import com.kerz.dao.GenericDao;

@Repository
public abstract class GenericDaoImpl<T> implements GenericDao<T>
{
	private EntityManager m_entityManager;
	private Query m_getAllQuery;
	private Logger m_log = LoggerFactory.getLogger(GenericDaoImpl.class);
	private Class<T> m_persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericDaoImpl()
	{
		m_persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void delete(T o)
	{
		m_entityManager.remove(o);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		if (m_getAllQuery == null)
		{
			String queryString = "select o from " + m_persistentClass.getSimpleName() + " o";
			m_log.debug("queryString={}", queryString);
			m_getAllQuery = m_entityManager.createQuery(queryString);
		}
		return (List<T>) m_getAllQuery.getResultList();
	}
	
	public List<T> findByExample(T example)
	{
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(m_persistentClass);
		Root<T> root = criteriaQuery.from(m_persistentClass);
		Metamodel metaModel = entityManager.getMetamodel();
		ManagedType<T> managedType = metaModel.managedType(m_persistentClass);
		m_log.debug("managed type={}", managedType);
		Set<SingularAttribute<? super T, ?>> attrs = managedType.getSingularAttributes();
		List<Predicate> predicates = new ArrayList<Predicate>();
		List<T> result = null;
		if (attrs != null)
		{
			for (SingularAttribute<? super T, ?> attr : attrs)
			{
				m_log.debug("attr={}", attr);
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
								
								@SuppressWarnings("unchecked")
								Path<String> path = (Path<String>) root.get(attr);
								Predicate predicate = criteriaBuilder.like(path, stringValue);
								m_log.debug("adding predicate {}", predicate);
								predicates.add(predicate);
							}
						}
					}
				}
			}
			
			// predicate list populated
			Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);
			Predicate andPredicate = criteriaBuilder.and(predicateArray);
			criteriaQuery.where(andPredicate);
			TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
			result = typedQuery.getResultList();
		}
		
		return result;
	}
	
	public T findById(Object id)
	{
		return (T) m_entityManager.find(m_persistentClass, id);
	}
	
	protected EntityManager getEntityManager()
	{
		return m_entityManager;
	}
	
	public T save(T o)
	{
		m_entityManager.persist(o);
		return o;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager)
	{
		m_entityManager = entityManager;
	}
	
	public T update(T o)
	{
		return m_entityManager.merge(o);
	}
}
