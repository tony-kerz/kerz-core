package com.kerz.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;

public class ConfirmValidator implements ConstraintValidator<Confirm, Object>
{
	private Confirm confirm;
	
	public void initialize(Confirm confirm)
	{
		this.confirm = confirm;
	}
	
	public boolean isValid(Object o, ConstraintValidatorContext cvc)
	{
		Assert.notNull(o, "object required");
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(o);
		Object propVal = bw.getPropertyValue(confirm.property());
		Object confirmPropVal = bw.getPropertyValue(confirm.confirmProperty());
		return propVal.equals(confirmPropVal);
	}
}
