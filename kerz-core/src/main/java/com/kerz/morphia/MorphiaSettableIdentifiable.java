package com.kerz.morphia;

import java.io.Serializable;

import com.google.code.morphia.annotations.Id;
import com.kerz.domain.SettableIdentifiable;

public abstract class MorphiaSettableIdentifiable<T> implements Serializable, SettableIdentifiable<T>
{
	private static final long serialVersionUID = 1L;
	
	@Id
	private T id;
	
	public T getId()
	{
		return id;
	}
	
	@Override
	public void setId(T id)
	{
		this.id = id;
	}
}
