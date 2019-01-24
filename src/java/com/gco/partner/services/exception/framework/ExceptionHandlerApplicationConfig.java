package com.gco.partner.services.exception.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.gco.partner.services.exception.framework",
		"com.gco.partner.services.exception.aspect",
		"com.gco.partner.services.exception.handler.impl"})
public class ExceptionHandlerApplicationConfig
{

}
