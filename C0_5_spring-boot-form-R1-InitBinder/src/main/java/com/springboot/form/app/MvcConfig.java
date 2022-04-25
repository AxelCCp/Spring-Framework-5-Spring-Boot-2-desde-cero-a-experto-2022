package com.springboot.form.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tiempoTranscurridoInterceptor).addPathPatterns("/form5/**");
	}
	
	@Autowired
	@Qualifier("tiempoTranscurridoInterceptor")
	private HandlerInterceptor tiempoTranscurridoInterceptor;
}
