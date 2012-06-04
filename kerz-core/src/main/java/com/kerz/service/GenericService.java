package com.kerz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface GenericService<T>
{
	void delete(T o);
	
	List<T> findByExample(T example);
	
	T save(T o);
	
	T update(T o);
}
