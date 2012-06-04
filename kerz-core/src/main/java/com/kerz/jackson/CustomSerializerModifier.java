package com.kerz.jackson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanPropertyWriter;
import org.codehaus.jackson.map.ser.BeanSerializerModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kerz.beans.PropertyProcessor;

public class CustomSerializerModifier extends BeanSerializerModifier
{
	private Logger log = LoggerFactory.getLogger(CustomSerializerModifier.class);
	PropertyProcessor propertyProcessor;

	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BasicBeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties)
	{
		Class<?> beanClass = beanDesc.getBeanClass();
		log.debug("bean-class=[{}]", beanClass);

		Set<String> includedProperties = propertyProcessor.getIncludedProperties(beanClass);
		Set<BeanPropertyWriter> removeProperties = new HashSet<BeanPropertyWriter>();

		for (BeanPropertyWriter beanPropertyWriter : beanProperties)
		{
			String propertyName = beanPropertyWriter.getName();

			log.debug("processing property=[{}] from bean-serializer-builder", propertyName);

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
