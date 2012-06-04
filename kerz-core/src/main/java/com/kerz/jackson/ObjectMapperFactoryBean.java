package com.kerz.jackson;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

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
		 objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		 objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
		 false);
		 // mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
		 // false);
		 objectMapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
		 objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
		 false);
		
//		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
