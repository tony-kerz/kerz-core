package com.kerz.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ConversationState implements Serializable
{
	private static final long serialVersionUID = 1L;
	private boolean active = false;
	private Map<Object, Object> map = new HashMap<Object, Object>();
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz)
	{
		return (T) map.get(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> clazz)
	{
		return (T) map.get(key);
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public Object put(Object key, Object val)
	{
		setActive(true);
		return map.put(key, val);
	}
	
	public void setActive(boolean active)
	{
		if (active != this.active)
		{
			this.active = active;
			if (!active)
			{
				map.clear();
			}
		}
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
