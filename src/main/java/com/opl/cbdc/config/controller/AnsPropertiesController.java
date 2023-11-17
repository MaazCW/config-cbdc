package com.opl.cbdc.config.controller;

import com.opl.cbdc.config.utils.*;
import com.opl.cbdc.utils.common.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ans_properties")
public class AnsPropertiesController {
	
	@Autowired
	private AnsProperties ansProperties;

	@SkipInterceptor
	@RequestMapping(value = "/get_by_key/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommonResponse> getByKey(@PathVariable String code) {
		try {
			String value = ansProperties.getValue(code);
			if(OPLUtils.isObjectNullOrEmpty(value)) {
				return new ResponseEntity<CommonResponse>(
						new CommonResponse("No Data Found", HttpStatus.OK.value(), Boolean.FALSE),
						HttpStatus.OK);	
			} 
			return new ResponseEntity<CommonResponse>(
					new CommonResponse("Successfully data found !!", value, HttpStatus.OK.value(), Boolean.TRUE),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while get value by key ==>", e);
			return new ResponseEntity<CommonResponse>(
					new CommonResponse("Something went wrong..!", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE),
					HttpStatus.OK);
		}
	}
	
}
