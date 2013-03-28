package com.kerz.jackson2;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class CustomModule extends SimpleModule
{
	private static final long serialVersionUID = 1L;
	public static final Version VERSION = new Version(1, 0, 0, null, null, null);
	// public static final Version VERSION = new Version(1, 0, 0, null);
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
