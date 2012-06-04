package com.kerz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kerz.dao.GenericDao;

@Service
@Transactional
public class GenericServiceImpl<T> implements GenericService<T>
{
	private GenericDao<T> m_dao;
	
	public GenericServiceImpl(GenericDao<T> dao)
	{
		m_dao = dao;
	}
	
	public void delete(T o)
	{
		m_dao.delete(o);
	}
	
	public List<T> findByExample(T example)
	{
		return m_dao.findByExample(example);
	}
	
	protected GenericDao<T> getDao()
	{
		return m_dao;
	}
	
	public T save(T o)
	{
		return m_dao.save(o);
	}
	
	public T update(T o)
	{
		return m_dao.update(o);
	}
}
