package com.kerz.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

public class StringHelper
{
	private static final String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String getRandomString(int length)
	{
		Assert.isTrue(length > 0);
		
		SecureRandom random = new SecureRandom();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
		{
			sb.append(charset.charAt(random.nextInt(charset.length())));
		}
		
		return sb.toString();
	}
	
	public static Set<String> setFromCsv(String csv)
	{
		Set<String> scope = new HashSet<String>(Arrays.asList(csv.split(",")));
		return scope;
	}
	
	public static String csvFromSet(Set<String> set)
	{
		StringBuilder csv = new StringBuilder();
		String comma = "";
		for (String scopeElement : set)
		{
			csv.append(comma).append(scopeElement);
			comma = ",";
		}
		return csv.toString();
	}
}