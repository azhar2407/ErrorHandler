package com.gco.partner.services.exception.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.gco.partner.services.exception.annotation.ExceptionHandlers;
import com.gco.partner.services.exception.handler.IExceptionHandler;

@Component
public class ExceptionHandlerFrameWork<T>
{
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * resolves the exception handler beans based on the names
	 * 
	 * @param exception
	 * @param handlers
	 * */
	@SuppressWarnings("unchecked")
	private List<IExceptionHandler<T>> resolveHandlers(final T exception,final List<String> handlers)
	{
		final List<IExceptionHandler<T>> exceptionHandlers = new ArrayList<>();
		CollectionUtils.forAllDo(handlers, new Closure()
			{
				
				@Override
				public void execute(Object handlerName)
				{	
					final String handleName = String.valueOf(handlerName);
					exceptionHandlers.add((IExceptionHandler<T>)applicationContext.getBean(handleName));
				}
			});
		return exceptionHandlers;
	}
	
	/**
	 * invokes the appropriate handler
	 * 
	 * @param exception
	 * @param handlers
	 * */
	@SuppressWarnings("unchecked")
	private void handleException(T exception, List<IExceptionHandler<T>> handlers)
	{
		CollectionUtils.forAllDo(handlers, new Closure()
			{
				
				@Override
				public void execute(Object handler)
				{
					((IExceptionHandler<T>) handler).handleException(exception);
				}
			});
	}
	
	public void handleException(T exception, String[] handlerNames)
	{
		ExceptionHandlers handlersAnnotation = exception.getClass()
				.getAnnotation(ExceptionHandlers.class);

		Set<String> handlerSet = new LinkedHashSet<String>(Arrays.asList(handlerNames));

		if (handlersAnnotation != null)
		{
			handlerSet.addAll(Arrays.asList(handlersAnnotation.value()));
		}
		
		List<String> handlersList = new ArrayList<>(handlerSet);
		
		if(!CollectionUtils.isEmpty(handlersList))
		{
			List<IExceptionHandler<T>> handlers = resolveHandlers(exception, handlersList);
			handleException(exception, handlers);
		}
	}
}
