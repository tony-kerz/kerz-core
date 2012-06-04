package com.kerz.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.Assert;

public class DefaultingMessageSource implements MessageSource, MessageSourceAware
{
	private MessageSource messageSource;
	
	@Override
	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	public String getMessage(String code, Object[] args)
	{
		Assert.notNull(messageSource, "message source required");
		return messageSource.getMessage(code, args, "{" + code + "}", null);
	}
	
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale)
	{
		Assert.notNull(messageSource, "message source required");
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}
	
	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException
	{
		Assert.notNull(messageSource, "message source required");
		return messageSource.getMessage(code, args, locale);
	}
	
	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException
	{
		Assert.notNull(messageSource, "message source required");
		return messageSource.getMessage(resolvable, locale);
	}
}
