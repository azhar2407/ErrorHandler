package com.gco.partner.services.exception.handler;

public interface IExceptionHandler<T>
{
	public void handleException(final T baseException);
}
