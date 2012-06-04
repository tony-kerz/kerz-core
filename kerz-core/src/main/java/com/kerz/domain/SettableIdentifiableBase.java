package com.kerz.domain;

public class SettableIdentifiableBase<T> extends IdentifiableBase<T> implements SettableIdentifiable<T>
{
	public SettableIdentifiableBase()
	{
	}
	
	public SettableIdentifiableBase(T id)
	{
		setId(id);
	}
	
	@Override
	public void setId(T id)
	{
		this.id = id;
	}
}
