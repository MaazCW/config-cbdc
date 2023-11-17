package com.opl.cbdc.config.cacheSchedular;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.*;
import org.springframework.cache.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@EnableCaching
public class ClearCacheTasks {

    @Autowired
    private CacheManager cacheManager;

    private static final Logger log = LoggerFactory.getLogger(ClearCacheTasks.class);

    /*
     * at 9,12,15,18,21 hour at 5th min
     * */
    //@Scheduled(cron = "0 5 9,12,15,18,21 * * ?")

    /*
     * every 1 hour at 5th min cache will be clear
     * */
    @Scheduled(cron = "0 5 * * * ?")
    public void run() {
        log.info("-- In clear cache scheduler --" + new Date());
        Integer hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        for(String name:cacheManager.getCacheNames()){
            List<String> cacheName = new ArrayList<>();
            if(cacheManager.getCache(name).getName().contains("rpt") && (hour.equals(9) || hour.equals(12) || hour.equals(15) || hour.equals(18) || hour.equals(21))){
                cacheName.add(cacheManager.getCache(name).getName());
                cacheManager.getCache(name).clear();
            }else if(!cacheManager.getCache(name).getName().contains("rpt")){
                cacheName.add(cacheManager.getCache(name).getName());
                cacheManager.getCache(name).clear();
            }
            log.info(" -- removed cache -- : " + cacheName.toString());
        }
        log.info("-- Exit from clear cache scheduler --" + new Date());
    }
}
