package com.kerz.morphia.converter;

import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.converters.SimpleValueConverter;
import com.google.code.morphia.converters.TypeConverter;
import com.google.code.morphia.mapping.MappedField;
import com.google.code.morphia.mapping.MappingException;

@SuppressWarnings("rawtypes")
public class DateTimeConverter extends TypeConverter implements SimpleValueConverter
{
	public DateTimeConverter()
	{
		super(DateTime.class);
		log.debug("");
	}
	
	private Logger log = LoggerFactory.getLogger(DateTimeConverter.class);
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException
	{
		if (log.isDebugEnabled())
		{
			log.debug("target-class={}, from-db-object={}, optional-extra-info={}", new Object[] { targetClass, fromDBObject,
			    optionalExtraInfo });
		}
		Object result = null;
		if (fromDBObject != null)
		{
			Date date = (Date) fromDBObject;
			result = new DateTime(date.getTime());
		}
		return result;
	}
	
	@Override
	public Object encode(Object value, MappedField optionalExtraInfo)
	{
		log.debug("value={}, optional-extra-info={}", value, optionalExtraInfo);
		DateTime dateTime = (DateTime) value;
		// store as 'plain old' java.util.Date
		return dateTime.toDate();
	}
}
