package com.kerz.jackson;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.deser.BeanDeserializerBuilder;
import org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import org.codehaus.jackson.map.deser.SettableBeanProperty;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kerz.beans.PropertyProcessor;

public class CustomDeserializerModifier extends BeanDeserializerModifier
{
	private Logger log = LoggerFactory.getLogger(CustomDeserializerModifier.class);
	PropertyProcessor propertyProcessor;

	// jackson 3.x, not yet supported by spring
	// @Override
	// public List<BeanPropertyDefinition> updateProperties(DeserializationConfig
	// config, BeanDescription beanDesc,
	// List<BeanPropertyDefinition> propDefs)
	// {
	// log.debug("bean-desc={}", beanDesc);
	// // TODO Auto-generated method stub
	// return super.updateProperties(config, beanDesc, propDefs);
	// }
	//
	// @Override
	// public BeanDeserializerBuilder updateBuilder(DeserializationConfig config,
	// BeanDescription beanDesc,
	// BeanDeserializerBuilder builder)
	// {
	// log.debug("bean-desc={}", beanDesc);
	// // TODO Auto-generated method stub
	// return super.updateBuilder(config, beanDesc, builder);
	// }
	//
	// @Override
	// public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config,
	// BeanDescription beanDesc,
	// JsonDeserializer<?> deserializer)
	// {
	// log.debug("bean-desc={}", beanDesc);
	// // TODO Auto-generated method stub
	// return super.modifyDeserializer(config, beanDesc, deserializer);
	// }

	public void setPropertyProcessor(PropertyProcessor propertyProcessor)
	{
		this.propertyProcessor = propertyProcessor;
	}

	public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BasicBeanDescription beanDesc,
			BeanDeserializerBuilder builder)
	{
		Class<?> beanClass = beanDesc.getBeanClass();
		log.debug("bean-class=[{}]", beanClass);

		Set<String> includedProperties = propertyProcessor.getIncludedProperties(beanClass);
		Set<String> builderProperties = new HashSet<String>();

		// need to just collect these cause attempt to remove while iterating incurs
		// concurrent mod issues...
		//
		Iterator<SettableBeanProperty> propertyIterator = builder.getProperties();
		while (propertyIterator.hasNext())
		{
			builderProperties.add(propertyIterator.next().getName());
		}

		for (String propertyName : builderProperties)
		{
			log.debug("processing property=[{}] from bean-deserializer-builder", propertyName);

			if (!includedProperties.contains(propertyName))
			{
				log.debug("property-processor include-list does not contain=[{}], removing", propertyName);
				builder.removeProperty(propertyName);
			}
		}

		return super.updateBuilder(config, beanDesc, builder);
	}

}
