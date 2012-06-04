package com.kerz.jpa;

import java.io.Serializable;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.data.domain.Persistable;

public class JpaHelper
{
	@SuppressWarnings("unchecked")
	public static <ID extends Serializable> ID getIdDirect(Persistable<ID> persistable)
	{
		if (persistable instanceof HibernateProxy)
		{
			LazyInitializer lazyInitializer = ((HibernateProxy) persistable).getHibernateLazyInitializer();
			if (lazyInitializer.isUninitialized())
			{
				return (ID) lazyInitializer.getIdentifier();
			}
		}
		return persistable.getId();
	}
}
