package com.kerz.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

@MappedSuperclass
public abstract class NamedPersistable<PK extends Serializable> extends AbstractPersistable<PK>
{
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2)
	private String name;

	protected NamedPersistable()
	{
	}

	public NamedPersistable(String name)
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		NamedPersistable<PK> other = (NamedPersistable<PK>) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}
}
