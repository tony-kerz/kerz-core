package com.kerz.domain;

public class IdentifiableBase<T> implements Identifiable<T>
{
	protected T id;
	
	public T getId()
	{
		return id;
	}
}
