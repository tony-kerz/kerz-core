package com.kerz.jackson2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper>
{
	private Logger log = LoggerFactory.getLogger(ObjectMapperFactoryBean.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	public void setModule(Module module)
	{
		log.debug("registering module={}", module);
		objectMapper.registerModule(module);
	}

	public ObjectMapper getObject() throws Exception
	{
		//objectMapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);

		return objectMapper;
	}

	public Class<ObjectMapper> getObjectType()
	{
		return ObjectMapper.class;
	}

	public boolean isSingleton()
	{
		return true;
	}
}
