package com.kerz.beans;

import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author tony
 * 
 *         <p>
 *         it is not valid to include and exclude the same property in a single
 *         filterO
 *         </p>
 * 
 * 
 */
public class PropertyFilter
{
	private Class<?> beanClass;
	private Set<String> excludeProperties;
	private Set<String> includeProperties;

	public Class<?> getBeanClass()
	{
		return beanClass;
	}

	public Set<String> getExcludeProperties()
	{
		return excludeProperties;
	}

	public Set<String> getIncludeProperties()
	{
		return includeProperties;
	}

	public PropertyStatus getPropertyStatus(String propertyName)
	{
		return new PropertyStatus(includeProperties == null ? true : includeProperties.contains(propertyName),
				excludeProperties == null ? false : excludeProperties.contains(propertyName));
	}

	public void setBeanClass(Class<?> beanClass)
	{
		this.beanClass = beanClass;
	}

	public void setExcludeProperties(Set<String> excludeProperties)
	{
		this.excludeProperties = excludeProperties;
	}

	public void setIncludeProperties(Set<String> includeProperties)
	{
		this.includeProperties = includeProperties;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
