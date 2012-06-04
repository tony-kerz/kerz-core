package com.kerz.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmValidator.class)
@Documented
public @interface Confirm
{
	String confirmProperty();
	
	Class<?>[] groups() default {};
	
	String message() default "{com.kerz.validation.constraints.confirm}";
	
	Class<? extends Payload>[] payload() default {};
	String property();
}
