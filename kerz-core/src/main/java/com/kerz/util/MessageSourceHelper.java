package com.kerz.util;

import org.springframework.context.MessageSource;

public class MessageSourceHelper
{
	public static String getMessage(MessageSource messageSource, String code, Object... args)
	{
		return messageSource.getMessage(code, args, "{" + code + "}", null);
	}
	
	public static String getMessage(MessageSource messageSource, String code)
	{
		return messageSource.getMessage(code, null, "{" + code + "}", null);
	}
}
