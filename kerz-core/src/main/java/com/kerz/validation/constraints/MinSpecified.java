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
@Constraint(validatedBy = MinSpecifiedValidator.class)
@Documented
public @interface MinSpecified
{
	Class<?>[] groups() default {};
	
	String message() default "{com.kerz.validation.constraints.minspecified}";
	
	int min() default 1;
	
	Class<? extends Payload>[] payload() default {};
	
	String[] value();
}
