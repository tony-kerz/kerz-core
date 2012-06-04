package com.kerz.morphia;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Version;
import com.kerz.domain.Identifiable;

public abstract class MorphiaIdentifiable implements Serializable, Identifiable<String>
{
	private static final long serialVersionUID = 1L;
	
	@Id
	private ObjectId id;
	
	@Version
	long version;
	
	public String getId()
	{
		String result = null;
		if (id != null)
		{
			result = id.toString();
		}
		return result;
	}
}
