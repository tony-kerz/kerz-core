package com.kerz.test;

import java.lang.reflect.Constructor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import com.kerz.test.domain.ProtectedConstructor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SomeTest
{
	@Autowired
	@Qualifier("myLong")
	private Long myLong;
	
	@Test
	public void basic()
	{
		System.out.println("myLong=" + myLong);
	}
	
	@Test
	public void reflect()
	{
		try
		{
			Object o = ProtectedConstructor.class.newInstance();
		}
		catch (Throwable t)
		{
			System.out.println("caught: " + t);
		}
	}
	
	@Test
	public void reflect2()
	{
		try
		{
			Class<ProtectedConstructor> c = ProtectedConstructor.class;
			Constructor<ProtectedConstructor> ct = c.getDeclaredConstructor(null);
			ReflectionUtils.makeAccessible(ct);
			Object o = ct.newInstance(null);
		}
		catch (Throwable t)
		{
			System.out.println("caught: " + t);
		}
	}
}
