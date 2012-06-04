package com.kerz.util;

import java.lang.reflect.Constructor;

import org.springframework.util.ReflectionUtils;

import com.kerz.text.FormattedException;

public class ClassHelper
{
	public static String getClassName(Object o)
	{
		if (o != null)
		{
			return o.getClass().getName();
		}
		else
		{
			return "null";
		}
	}

	/**
	 * allows construction of classes even with protected or private constructors
	 * 
	 * @param <T>
	 * @param clazz
	 * @return instance of class
	 */
	public static <T> T newInstance(Class<T> clazz)
	{
		T result = null;
		try
		{
			Constructor<T> ctor = clazz.getDeclaredConstructor((Class<?>[]) null);
			ReflectionUtils.makeAccessible(ctor);
			result = ctor.newInstance((Object[]) null);
		}
		catch (Throwable t)
		{
			throw new FormattedException(t, "unable to obtain new instance for class={0}", clazz);
		}
		return result;
	}
}
