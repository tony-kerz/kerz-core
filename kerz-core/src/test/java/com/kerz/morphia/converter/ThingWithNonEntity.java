package com.kerz.morphia.converter;

import org.joda.time.DateTime;

import com.google.code.morphia.annotations.Converters;
import com.google.code.morphia.annotations.Entity;
import com.kerz.morphia.MorphiaIdentifiable;

@Entity
@Converters(DateTimeConverter.class)
public class ThingWithNonEntity extends MorphiaIdentifiable
{
	private static final long serialVersionUID = 1L;
	
	private DateTime dateTime;
	
	public DateTime getDateTime()
	{
		return dateTime;
	}
	
	public void setDateTime(DateTime dateTime)
	{
		this.dateTime = dateTime;
	}
}
