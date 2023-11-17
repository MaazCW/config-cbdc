package com.opl.cbdc.config.utils;

import com.opl.cbdc.common.api.auth.model.*;
import com.opl.cbdc.common.api.auth.utils.*;
import com.opl.cbdc.common.client.auth.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.method.*;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private AuthClient authClient;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HandlerMethod method  = null;
		if(handler instanceof HandlerMethod) {
			method = (HandlerMethod)handler;
		}

		if(AuthCredentialUtils.isDecryptClient(request)) {
			response.setHeader("is_decrypt", "true");
		}

		if(request.getRequestURI().contains("/kcc/process/aiu")){
			request.setAttribute("is_decrypt", "true");
		}


		// SKIP TOKEN AUTHENTICATION IF METHOD HAVE SkipInterceptor ANNOTATION OR ITS COMES FROM /error
		if(method != null && method.getMethod().isAnnotationPresent(SkipInterceptor.class)) {
			logger.info("Auth Skipped for Request URL : {}"  , request.getRequestURI());
			if(AuthCredentialUtils.isClient(request)){
				response.setHeader("req_auth", "true");
			}
			request.setAttribute(AuthCredentialUtils.SESSION_OBJ_KEY, new AuthClientResponse());
			return Boolean.TRUE;
		}

		if(request.getRequestURI().contains("actuator")){
//			logger.info("For accuator : {}"  , request.getRequestURI());
			return Boolean.TRUE;
		}

		if(request.getRequestURI().contains("/error")) {
			logger.info("Error for Request URL : {}"  , request.getRequestURI());
			return Boolean.TRUE;
		}


		if(AuthCredentialUtils.isClient(request)) {
			response.setHeader("req_auth", "true");
			request.setAttribute(AuthCredentialUtils.SESSION_OBJ_KEY, new AuthClientResponse());
			return true;
		}

		AuthRequest authRequest = AuthCredentialUtils.httpResToAuthReq(request);
		if(authRequest == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.warn("Bad Request, Token is null or empty !!");
			return Boolean.FALSE;
		}

		AuthClientResponse authResponse = authClient.isAccessTokenValidOrNot(authRequest);
		if (authResponse == null || !authResponse.isAuthenticate()) {
			logger.warn("Unauthorized Request, Access token expire or invalid");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		// USE THIS CLASS IN CONTROLLER AS ===>   @RequestAttribute(AuthCredentialUtils.SESSION_OBJ_KEY) AuthClientResponse authResponse
		request.setAttribute(AuthCredentialUtils.SESSION_OBJ_KEY, authResponse);
		return true;
	}

}
