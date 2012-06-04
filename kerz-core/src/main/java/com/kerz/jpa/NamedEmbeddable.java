package com.kerz.jpa;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEmbeddable
{
	private String name;
	
	protected NamedEmbeddable()
	{
	}
	
	public NamedEmbeddable(String name)
	{
		super();
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
