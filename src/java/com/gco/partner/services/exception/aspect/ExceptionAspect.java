package com.gco.partner.services.exception.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gco.partner.services.exception.annotation.HandleException;
import com.gco.partner.services.exception.annotation.HandleExceptions;
import com.gco.partner.services.exception.framework.ExceptionHandlerFrameWork;
import com.gco.partner.services.exceptions.BaseException;


@Component
@Aspect
public class ExceptionAspect
{
	@Autowired
	private ExceptionHandlerFrameWork<BaseException> exceptionHandlerFrameWork;

	/**
	 * This method handles exception from methods which are annotated with @HandleExceptions
	 * 
	 * @param joinPoint
	 * @param exception
	 * @param handleExceptions
	 * @return
	 * */
	@AfterThrowing(value = "execution(@com.gco.partner.services.exception.annotation.HandleExceptions * *(..)) "
			+ "&& @annotation(handleExceptions) ", throwing = "exception")
	public void handleMultipleExceptions(final JoinPoint joinPoint,
			final Exception exception, final HandleExceptions handleExceptions)
	{
		HandleException[] handleExceptionArr = handleExceptions.value();
		
        for (HandleException handleException : handleExceptionArr) 
        {
            Class<?> clazz = handleException.exceptionType();
            if (exception.getClass().equals(clazz) || clazz.isInstance(exception)) 
            {
                handleException(exception, handleException);
                break;
            }
        }
	}
	
	/**
	 * This method handles exception from methods which are annotated with @HandleException
	 * 
	 * @param joinPoint
	 * @param exception
	 * @param handleException
	 * @return
	 * */
	@AfterThrowing(value = "execution(@com.gco.partner.services.exception.annotation.HandleException * *(..)) "
			+ "&& @annotation(handleException) ", throwing = "exception")
	public void handleSingleException(final JoinPoint joinPoint,
			final Exception exception, final HandleException handleException)
	{
		handleException(exception, handleException);
	}
	
	/**
	 * Calls exception handler framework with appropriate exception and Handler
	 * 
	 * @param exception
	 * @param handleException
	 * */
	private void handleException(Exception exception, HandleException handleException)
	{
		BaseException baseException = null;
		if (exception instanceof BaseException)
		{
			baseException = (BaseException) exception;
		}
		else
		{
			baseException = new BaseException(exception);
		}

		exceptionHandlerFrameWork.handleException(baseException, handleException.handlers());
	}
            
            
}
