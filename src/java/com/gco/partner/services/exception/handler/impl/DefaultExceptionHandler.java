package com.gco.partner.services.exception.handler.impl;

import org.springframework.stereotype.Component;

import com.gco.partner.services.exception.handler.IExceptionHandler;

@Component
public class DefaultExceptionHandler<T> implements IExceptionHandler<T>
{

	@Override
	public void handleException(T baseException)
	{
		
	}
}
