package com.kerz.jackson;

import java.io.IOException;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class BindingResultSerializer extends JsonSerializer<BindingResult> implements MessageSourceAware
{
	private Logger log = LoggerFactory.getLogger(BindingResultSerializer.class);
	private MessageSource messageSource;

	@Override
	public Class<BindingResult> handledType()
	{
		// return super.handledType();
		return BindingResult.class;
	}

	@Override
	public void serialize(BindingResult value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException
	{
		log.debug("binding-result={}", value);

		Locale locale = null;

		jgen.writeStartObject();
		jgen.writeFieldName("has-errors");
		jgen.writeBoolean(value.getErrorCount() > 0);

		int globalErrorCount = value.getGlobalErrorCount();
		if (globalErrorCount > 0)
		{
			jgen.writeFieldName("global-error-count");
			jgen.writeNumber(globalErrorCount);

			jgen.writeArrayFieldStart("global-errors");
			for (ObjectError objectError : value.getGlobalErrors())
			{
				jgen.writeStartObject();
				jgen.writeFieldName("object");
				jgen.writeString(objectError.getObjectName());
				jgen.writeFieldName("message");
				jgen.writeString(messageSource.getMessage(objectError, locale));
				jgen.writeEndObject();
			}
			jgen.writeEndArray();
		}

		int fieldErrorCount = value.getFieldErrorCount();
		if (fieldErrorCount > 0)
		{
			jgen.writeFieldName("field-error-count");
			jgen.writeNumber(fieldErrorCount);

			jgen.writeArrayFieldStart("field-errors");
			for (FieldError fieldError : value.getFieldErrors())
			{
				jgen.writeStartObject();
				jgen.writeFieldName("object");
				jgen.writeString(fieldError.getObjectName());
				jgen.writeFieldName("field");
				jgen.writeString(fieldError.getField());
				jgen.writeFieldName("rejected-value");
				jgen.writeObject(fieldError.getRejectedValue());
				jgen.writeFieldName("message");
				jgen.writeString(messageSource.getMessage(fieldError, locale));
				jgen.writeEndObject();
			}
			jgen.writeEndArray();
		}

		jgen.writeEndObject();
	}

	@Override
	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
}
