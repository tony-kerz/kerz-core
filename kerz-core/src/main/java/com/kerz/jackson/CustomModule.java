package com.kerz.jackson;

import java.util.List;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.BeanSerializerModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomModule extends SimpleModule
{
	//public static final Version VERSION = new Version(1, 0, 0, null, null, null);
	public static final Version VERSION = new Version(1, 0, 0, null);
	private List<BeanDeserializerModifier> deserializerModifiers;
	private List<BeanSerializerModifier> serializerModifiers;

	public void setSerializerModifiers(List<BeanSerializerModifier> serializerModifiers)
	{
		this.serializerModifiers = serializerModifiers;
	}

	private Logger log = LoggerFactory.getLogger(CustomModule.class);

	public CustomModule()
	{
		super(CustomModule.class.getName(), VERSION);
		log.debug("custom-module={}", this);
	}

	public void setDeserializerModifiers(List<BeanDeserializerModifier> deserializerModifiers)
	{
		this.deserializerModifiers = deserializerModifiers;
	}

	public void setSerializers(List<JsonSerializer<?>> serializers)
	{
		for (JsonSerializer<?> serializer : serializers)
		{
			log.debug("adding serializer={}", serializer);
			addSerializer(serializer);
		}
	}

	@Override
	public void setupModule(SetupContext context)
	{
		super.setupModule(context);

		if (deserializerModifiers != null)
		{
			for (BeanDeserializerModifier deserializerModifier : deserializerModifiers)
			{
				log.debug("adding deserializer-modifier={}", deserializerModifier);
				context.addBeanDeserializerModifier(deserializerModifier);
			}
		}		
		
		if (serializerModifiers != null)
		{
			for (BeanSerializerModifier serializerModifier : serializerModifiers)
			{
				log.debug("adding serializer-modifier={}", serializerModifier);
				context.addBeanSerializerModifier(serializerModifier);
			}
		}
	}
}
