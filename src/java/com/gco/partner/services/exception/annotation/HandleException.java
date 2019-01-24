package com.gco.partner.services.exception.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gco.partner.services.exception.constants.ExceptionHandlerConstants;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandleException
{
	public Class<?> exceptionType() default Exception.class;
	public String[] handlers() default {ExceptionHandlerConstants.DEFAULT_EXCEPTION_HANDLER};
}
