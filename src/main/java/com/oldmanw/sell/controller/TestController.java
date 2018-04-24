package com.oldmanw.sell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam("url") String url) {
        /**
         * url之前要加http://或https://才能使用
         */
        return "redirect:" + url;

    }

}
