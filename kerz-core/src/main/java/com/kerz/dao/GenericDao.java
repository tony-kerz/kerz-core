package com.kerz.dao;

import java.util.List;

public interface GenericDao<T>
{
	void delete(T o);
	
	List<T> findAll();
	
	List<T> findByExample(T example);
	
	T findById(Object id);
	
	T save(T o);
	
	T update(T o);
}
