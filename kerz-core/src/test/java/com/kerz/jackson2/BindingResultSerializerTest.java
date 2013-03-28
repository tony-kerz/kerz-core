package com.kerz.jackson2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.databind.ObjectMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BindingResultSerializerTest
{
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void basic() throws Throwable
	{
		BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "object-name-1");
		
		bindingResult.addError(new ObjectError("object-name-1", null, new Object[] { "object-name-1" }, "the entire object [{0}] is toast"));
		
		bindingResult.addError(new FieldError("object-name-1", "field-name-1", "foo", false, null, new Object[] {"foo"}, "the field value [{0}] is lame"));
		bindingResult.addError(new FieldError("object-name-1", "field-name-2", "bar", false, null, new Object[] {"bar"}, "the field value [{0}] is also lame"));
		
		String json = objectMapper.writeValueAsString(bindingResult);
		System.out.println("binding-result json: " + json);
	}
}
