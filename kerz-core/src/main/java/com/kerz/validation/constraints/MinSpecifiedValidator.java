package com.kerz.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;

public class MinSpecifiedValidator implements ConstraintValidator<MinSpecified, Object>
{
	private int m_min;
	private String[] m_properties;
	
	//@Override
	public void initialize(MinSpecified a)
	{
		m_properties = a.value();
		m_min = a.min();
	}
	
	//@Override
	public boolean isValid(Object o, ConstraintValidatorContext cvc)
	{
		Assert.notNull(o, "object required");
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(o);
		int specifiedCount = 0;
		for (int i = 0; i < m_properties.length; i++)
		{
			Object p = bw.getPropertyValue(m_properties[i]);
			if (p != null)
			{
				specifiedCount++;
			}
		}
		return specifiedCount >= m_min;
	}
}
