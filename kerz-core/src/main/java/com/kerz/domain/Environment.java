package com.kerz.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Environment
{
	@DateTimeFormat(iso = ISO.DATE)
	public Date getDate()
	{
		return new Date();
	}
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	public Date getDateTime()
	{
		return new Date();
	}
	
	@DateTimeFormat(pattern = "HH:mm:ss.SSS")
	public Date getTime()
	{
		return new Date();
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
