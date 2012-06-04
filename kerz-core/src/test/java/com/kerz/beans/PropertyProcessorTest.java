package com.kerz.beans;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PropertyProcessorTest
{
	@Autowired
	@Qualifier("pp1")
	private PropertyProcessor pp1;

	@Autowired
	@Qualifier("pp2")
	private PropertyProcessor pp2;

	@Test
	public void basicInclude()
	{
		WidgetA w = new WidgetA();
		final Set<String> props = new HashSet<String>();

		pp1.process(w.getClass(), new PropertyCallback()
		{
			@Override
			public void doWithProperty(String propertyName)
			{
				props.add(propertyName);
			}
		});

		Assert.assertTrue(props.size() == 1);
		Assert.assertTrue(props.contains("prop1"));
	}

	@Test
	public void basicExclude()
	{
		WidgetA w = new WidgetA();
		final Set<String> props = new HashSet<String>();

		pp2.process(w.getClass(), new PropertyCallback()
		{
			@Override
			public void doWithProperty(String propertyName)
			{
				props.add(propertyName);
			}
		});

		Assert.assertTrue(props.size() == 2);
		Assert.assertTrue(props.contains("prop2"));
		Assert.assertTrue(props.contains("prop3"));
	}	
	
	@Test
	public void basicCache()
	{
		basicInclude();
		basicInclude();
		PropertyAccessor pa = new DirectFieldAccessor(pp1);
		@SuppressWarnings("unchecked")
		Map<Class<?>, Set<String>> map = (Map<Class<?>, Set<String>>) pa.getPropertyValue("includedPropertiesMap");
		Assert.assertTrue(map.get(WidgetA.class) != null);
	}
}

class WidgetA
{
	String prop1;
	String prop2;
	String prop3;

	public String getProp1()
	{
		return prop1;
	}

	public String getProp2()
	{
		return prop2;
	}

	public String getProp3()
	{
		return prop3;
	}

	public void setProp1(String prop1)
	{
		this.prop1 = prop1;
	}

	public void setProp2(String prop2)
	{
		this.prop2 = prop2;
	}

	public void setProp3(String prop3)
	{
		this.prop3 = prop3;
	}
}
