package com.kerz.jackson2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.kerz.beans.PropertyProcessor;

public class CustomSerializerModifier extends BeanSerializerModifier
{
	private Logger log = LoggerFactory.getLogger(CustomSerializerModifier.class);
	PropertyProcessor propertyProcessor;

	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties)
	{
		Class<?> beanClass = beanDesc.getBeanClass();
		log.debug("bean-class=[{}]", beanClass);

		Set<String> includedProperties = propertyProcessor.getIncludedProperties(beanClass);
		Set<BeanPropertyWriter> removeProperties = new HashSet<BeanPropertyWriter>();

		for (BeanPropertyWriter beanPropertyWriter : beanProperties)
		{
			String propertyName = beanPropertyWriter.getName();

			log.debug("processing property=[{}]", propertyName, beanClass);

			if (!includedProperties.contains(propertyName))
			{
				log.debug("property-processor include-list does not contain=[{}], removing", propertyName);
				removeProperties.add(beanPropertyWriter);
			}
		}

		for (BeanPropertyWriter beanPropertyWriter : removeProperties)
		{
			beanProperties.remove(beanPropertyWriter);
		}

		return super.changeProperties(config, beanDesc, beanProperties);
	}

	public PropertyProcessor getPropertyProcessor()
	{
		return propertyProcessor;
	}

	public void setPropertyProcessor(PropertyProcessor propertyProcessor)
	{
		this.propertyProcessor = propertyProcessor;
	}
}
