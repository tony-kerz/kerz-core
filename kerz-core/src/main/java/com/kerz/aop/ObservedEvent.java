package com.kerz.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObservedEvent
{
	Object[] args;
	private Logger log = LoggerFactory.getLogger(ObservedEvent.class);
	Class<?> observedClass;
	String observedMethodName;
	String observedSimpleClassName;
	Map<String, Object> simpleArgMap;
	
	public ObservedEvent(Class<?> observedClass, String observedMethodName, Object[] args)
	{
		super();
		this.observedClass = observedClass;
		observedSimpleClassName = observedClass.getSimpleName();
		this.observedMethodName = observedMethodName;
		this.args = args;
		if (!ArrayUtils.isEmpty(args))
		{
			simpleArgMap = new HashMap<String, Object>(args.length);
			for (int i = 0; i < args.length; i++)
			{
				Object o = args[i];
				String key = StringUtils.uncapitalize(o.getClass().getSimpleName());
				if (simpleArgMap.containsKey(key))
				{
					log.debug("encountered duplicate key={}, abandoning attempt to generate simple arg map from args={}", key,
					    args);
					simpleArgMap = null;
					break;
				}
				simpleArgMap.put(key, o);
			}
		}
	}
	
	public void dispatch(Observer o)
	{
		Class<?> clazz = o.getClass();
		Class<?>[] argClasses = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++)
		{
			argClasses[i] = args[i].getClass();
		}
		Method m = null;
		String methodName = getMethodName();
		try
		{
			m = clazz.getMethod(methodName, argClasses);
		}
		catch (NoSuchMethodException nsme)
		{
			// acceptable if not found...
			if (log.isDebugEnabled())
			{
				log.debug("unable to find method={} on observer={} with arg-classes={}, ignoring...", new Object[] {
				    methodName, o.getClass().getName(), argClasses });
			}
		}
		if (m != null)
		{
			try
			{
				m.invoke(o, args);
			}
			catch (Throwable t)
			{
				throw new RuntimeException(t);
			}
		}
	}
	
	public Object[] getArgs()
	{
		return args;
	}
	
	private String getMethodName()
	{
		return "handle_" + observedSimpleClassName + "_" + observedMethodName;
	}
	
	public Class<?> getObservedClass()
	{
		return observedClass;
	}
	
	public String getObservedMethodName()
	{
		return observedMethodName;
	}
	
	public String getObservedSimpleClassName()
	{
		return observedSimpleClassName;
	}
	
	public Map<String, Object> getSimpleArgMap()
	{
		return simpleArgMap;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
