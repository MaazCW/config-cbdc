package com.opl.cbdc.config.utils;

import org.springframework.context.annotation.*;
import org.springframework.format.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.method.support.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.*;

import java.util.*;

@Configuration
@EnableAsync
public class SpringConfiguration implements WebMvcConfigurer {

	@Bean
	AuthenticationInterceptor authenticationInterceptor() {
		return new AuthenticationInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RequestDataResolver());
	}

	@Bean
	public MappedInterceptor mappedInterceptor() {
		return new MappedInterceptor(null, // => maps to any repository
				authenticationInterceptor());
	}
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PathVariableConverter());
    }
}
