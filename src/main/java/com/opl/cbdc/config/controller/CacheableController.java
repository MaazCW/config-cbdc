package com.opl.cbdc.config.controller;

import com.opl.cbdc.config.utils.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CacheableController {
	
	@Autowired
    private CacheManager cacheManager;

	private static final Logger logger = LoggerFactory.getLogger(CacheableController.class);

	@SkipInterceptor
	@GetMapping(value = "/clearCache")
	public String clearCache(){
	   for(String name:cacheManager.getCacheNames()){
	      cacheManager.getCache(name).clear();
	   }
	   logger.info("All Cache Cleared On "+new Date());
	   return "All Cache Cleared";
	}

}
