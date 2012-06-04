package com.kerz.validation.constraints;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.GroupSequence;
import javax.validation.Validator;
import javax.validation.constraints.Size;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kerz.validation.constraints.ValidateMe.ClassLevelGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ConfirmTest
{
	@Autowired
	private Validator validator;
	
	@Test
	public void invalid()
	{
		ValidateMe vm = new ValidateMe();
		vm.setProp("foo");
		vm.setPropConfirm("fooo");
		Set<ConstraintViolation<ValidateMe>> cvs = validator.validate(vm);
		Assert.assertTrue(cvs.size() > 0);
		for (ConstraintViolation<ValidateMe> cv : cvs)
		{
			System.out.println("cv=" + cv);
		}
	}
	
	@Test
	public void invalidGroups()
	{
		ValidateMe vm = new ValidateMe();
		vm.setProp("f"); // should fail prior to Confirm constraint...
		vm.setPropConfirm("fooo");
		Set<ConstraintViolation<ValidateMe>> cvs = validator.validate(vm);
		Assert.assertEquals(1, cvs.size());
		for (ConstraintViolation<ValidateMe> cv : cvs)
		{
			System.out.println("cv=" + cv);
		}
	}
	
	@Test
	public void valid()
	{
		ValidateMe vm = new ValidateMe();
		vm.setProp("foo");
		vm.setPropConfirm("foo");
		Set<ConstraintViolation<ValidateMe>> cvs = validator.validate(vm);
		Assert.assertTrue(cvs.size() == 0);
	}
}

@GroupSequence({ ValidateMe.class, ClassLevelGroup.class })
@Confirm(property = "prop", confirmProperty = "propConfirm", groups = { ClassLevelGroup.class })
class ValidateMe
{
	public interface ClassLevelGroup
	{
	}
	
	@Size(min = 2, max = 10)
	private String prop;
	
	private String propConfirm;
	
	public String getProp()
	{
		return prop;
	}
	
	public String getPropConfirm()
	{
		return propConfirm;
	}
	public void setProp(String prop)
	{
		this.prop = prop;
	}
	
	public void setPropConfirm(String propConfirm)
	{
		this.propConfirm = propConfirm;
	}
}
