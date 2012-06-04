package com.kerz.validation.constraints;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MinSpecifiedTest
{
	@MinSpecified({ "fieldOne", "fieldTwo" })
	class ValidateMe
	{
		private String fieldOne;
		
		private String fieldTwo;
		
		public String getFieldOne()
		{
			return fieldOne;
		}
		
		public String getFieldTwo()
		{
			return fieldTwo;
		}
		
		public void setFieldOne(String fieldOne)
		{
			this.fieldOne = fieldOne;
		}
		public void setFieldTwo(String fieldTwo)
		{
			this.fieldTwo = fieldTwo;
		}
	}
	
	@Autowired
	private Validator validator;
	
	@Test//(expected = java.lang.Throwable.class)
	public void invalid()
	{
		ValidateMe vm = new ValidateMe();
		Set<ConstraintViolation<ValidateMe>> cvs = validator.validate(vm);
		Assert.assertTrue(cvs.size() > 0);
		for (ConstraintViolation<ValidateMe> cv : cvs)
		{
			System.out.println("cv=" + cv);
		}
	}
	
	@Test
	public void valid()
	{
		ValidateMe vm = new ValidateMe();
		vm.setFieldOne("foo");
		Set<ConstraintViolation<ValidateMe>> cvs = validator.validate(vm);
		Assert.assertTrue(cvs.size() == 0);
		for (ConstraintViolation<ValidateMe> cv : cvs)
		{
			System.out.println(cv);
		}
	}
}
