package com.opl.cbdc.config.utils;

import com.opl.cbdc.utils.common.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.io.*;
import java.util.*;
import java.util.Map.*;

@SuppressWarnings("unchecked")
@Component
public class RestTemplateUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateUtils.class);

	public <T> T post(String url, Object reqeust, Class<?> clazz) {
		RestTemplate restTemplate = new RestTemplate();
		String errorDetails = null;
		LOGGER.info("Calling POST API : [{}]", url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> req = new HttpEntity<>(reqeust, headers);
		try {
			return (T) restTemplate.postForObject(url, req, clazz);
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Error : ", e);
			LOGGER.warn("Error Body: {}", e.getResponseBodyAsString());
			errorDetails = e.getResponseBodyAsString();
		}
		return getResponse(errorDetails, clazz);
	}

	public <T> T get(String url, Class<?> clazz) {
		RestTemplate restTemplate = new RestTemplate();
		String errorDetails = null;
		LOGGER.info("Calling GET API : [{}]", url);
		try {
			return (T) restTemplate.getForObject(url, clazz);
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Error : ", e);
			LOGGER.warn("Error Body: {}", e.getResponseBodyAsString());
			errorDetails = e.getResponseBodyAsString();
		}
		return getResponse(errorDetails, clazz);
	}

	public <T> T get(String url, Class<?> clazz, Map<String, String> header) {
		RestTemplate restTemplate = new RestTemplate();
		String errorDetails = null;
		LOGGER.info("Calling GET API With Header : [{}]", url);
		HttpHeaders headers = new HttpHeaders();
		for (Entry<String, String> entry : header.entrySet()) {
			headers.set(entry.getKey(), entry.getValue());
		}
		HttpEntity<Object> req = new HttpEntity<>(null, headers);
		try {
			return (T) restTemplate.exchange(url, HttpMethod.GET, req, clazz).getBody();
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Error : ", e);
			LOGGER.warn("Error Body: {}", e.getResponseBodyAsString());
			errorDetails = e.getResponseBodyAsString();
		}
		return getResponse(errorDetails, clazz);
	}

	public <T> T post(String url, Object reqeust, Class<?> clazz, Map<String, String> header) {
		RestTemplate restTemplate = new RestTemplate();
		String errorDetails = null;
		LOGGER.info("Calling POST API With Header : [{}]", url);
		HttpHeaders headers = new HttpHeaders();
		if (!OPLUtils.isObjectNullOrEmpty(header)) {
			for (Entry<String, String> entry : header.entrySet()) {
				headers.set(entry.getKey(), entry.getValue());
			}
		}
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> req = new HttpEntity<>(reqeust, headers);
		try {
			return (T) restTemplate.postForObject(url, req, clazz);
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Error : ", e);
			LOGGER.warn("Error Body: {}", e.getResponseBodyAsString());
			errorDetails = e.getResponseBodyAsString();
		}
		return getResponse(errorDetails, clazz);
	}

	public <T> T post(String url, Object reqeust, Class<?> clazz, Map<String, String> header, MediaType contentType)
			throws HttpClientErrorException {
		RestTemplate restTemplate = new RestTemplate();
		String errorDetails = null;
		LOGGER.info("Calling POST API With Header : [{}]", url);
		HttpHeaders headers = new HttpHeaders();
		if (!OPLUtils.isObjectNullOrEmpty(header)) {
			for (Entry<String, String> entry : header.entrySet()) {
				headers.set(entry.getKey(), entry.getValue());
			}
		}
		headers.setContentType(contentType);
		HttpEntity<Object> req = new HttpEntity<>(reqeust, headers);
		try {
			return (T) restTemplate.postForObject(url, req, clazz);
		} catch (HttpStatusCodeException e) {
			LOGGER.error("Error : ", e);
			LOGGER.warn("Error Body: {}", e.getResponseBodyAsString());
			errorDetails = e.getResponseBodyAsString();
		}
		return getResponse(errorDetails, clazz);
	}

	public <T> T getResponse(String errorDetails, Class<?> clazz) {
		if (!OPLUtils.isObjectNullOrEmpty(errorDetails)) {
			try {
				return (T) MultipleJSONObjectHelper.getObjectFromString(errorDetails, clazz);
			} catch (IOException e) {
				LOGGER.error("Error while Converting String Response to Entity : ", e);
			}
		}
		return null;
	}

}
