package com.opl.cbdc.config.controller;

import com.opl.cbdc.config.utils.*;
import com.opl.cbdc.config.utils.namematch.*;
import com.opl.cbdc.config.utils.namematch.models.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/name")
public class NameMatchController {

	@Autowired
	private NameMatchingUtils matchingUtils;

	@SkipInterceptor
	@RequestMapping(value = "/match", method = RequestMethod.POST)
	public String sendNameMatch(@RequestBody NameMatchRequest nameMatchProxy) {
		try {
			matchingUtils.checkNameMatch(nameMatchProxy);
			return "success";
		} catch (Exception e) {
			log.error("Error while get value by key ==>", e);
			return "faild";
		}
	}

}
