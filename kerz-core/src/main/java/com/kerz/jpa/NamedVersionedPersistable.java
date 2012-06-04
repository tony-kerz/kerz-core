package com.kerz.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class NamedVersionedPersistable<PK extends Serializable> extends NamedPersistable<PK>
{
	private static final long serialVersionUID = 1L;

	@Version
	private Integer version;
	
	public Integer getVersion()
	{
		return version;
	}
	
	protected NamedVersionedPersistable()
	{
	}
	
	public NamedVersionedPersistable(String name)
	{
		super(name);
	}
}
