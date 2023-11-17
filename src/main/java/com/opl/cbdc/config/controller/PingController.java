package com.opl.cbdc.config.controller;

import com.opl.cbdc.config.utils.*;
import org.json.simple.*;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class PingController {

	private static final Logger logger = LoggerFactory.getLogger(PingController.class);

	@SuppressWarnings("unchecked")
	@SkipInterceptor
	@GetMapping(value = "/ping")
	public JSONObject ping() {
		logger.info("CHECK SERVICE STATUS =====================>");
		JSONObject obj = new JSONObject();
		obj.put("status", 200);
		obj.put("message", "Service is working fine!!");
		return obj;
	}

}
