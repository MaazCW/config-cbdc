package com.opl.cbdc.config.controller;

import org.springframework.boot.web.servlet.error.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import javax.servlet.*;
import javax.servlet.http.*;

@Controller
public class MyErrorController implements ErrorController {
	
	@RequestMapping(value = { "/error" })
	public ModelAndView handleError(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		ModelAndView modelAndView = new ModelAndView();
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				modelAndView.setViewName("404");
				modelAndView.setStatus(HttpStatus.NOT_FOUND);
				return modelAndView;
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				modelAndView.setViewName("500");
				modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				return modelAndView;
			} else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
				modelAndView.setViewName("405");
				modelAndView.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
				return modelAndView;
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				modelAndView.setViewName("400");
				modelAndView.setStatus(HttpStatus.BAD_REQUEST);
				return modelAndView;
			} else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
				modelAndView.setViewName("401");
				modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
				return modelAndView;
			}
		}
		modelAndView.setViewName("404");
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		return modelAndView;
	}

	public String getErrorPath() {
		return "/error";
	}

}