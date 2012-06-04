package com.kerz.beans;

import java.beans.PropertyEditor;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class PropertyEditorRegistrar implements org.springframework.beans.PropertyEditorRegistrar, InitializingBean
{
	private Map<Class<?>, PropertyEditor> m_propertyEditorMap;
	
	// @Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(m_propertyEditorMap, "property editor map required");
	}
	
	// @Override
	public void registerCustomEditors(PropertyEditorRegistry registry)
	{
		for (Entry<Class<?>, PropertyEditor> entry : m_propertyEditorMap.entrySet())
		{
			registry.registerCustomEditor(entry.getKey(), entry.getValue());
		}
	}
	
	public void setPropertyEditorMap(Map<Class<?>, PropertyEditor> propertyEditorMap)
	{
		m_propertyEditorMap = propertyEditorMap;
	}
}
