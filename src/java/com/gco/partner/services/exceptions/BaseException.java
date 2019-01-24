package com.gco.partner.services.exceptions;

import com.gco.partner.services.exception.annotation.ExceptionHandlers;

@ExceptionHandlers({"DefaultExceptionHandler"})
public class BaseException extends RuntimeException
{
	private static final long serialVersionUID = -6981395447033650078L;
	
	public BaseException(Exception exception)
	{

	}
}
