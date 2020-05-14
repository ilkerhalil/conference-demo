package com.turcom.conferencedemo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(this.getClass());

    @Value("${app.version}")
    private String appVersion;

    @Value("${DB_HOST}")
    private String DbHost;

    @GetMapping
    @RequestMapping("/")
    public Map getStatus(){
        Logger.info("DB HOST " +DbHost );
        Map map = new HashMap<String,String>();
        map.put("app-version",appVersion);
        return map;
    }
}
