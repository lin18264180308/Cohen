package com.cohen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    @RequestMapping(value = "/testMethod1")
    public String testMethod1() {
        System.out.println("testMethod1");
        return "success";
    }

    @RequestMapping(value = "/testMethod2", method = RequestMethod.POST)
    public String testMethod2(String username, String password) {
        System.out.println("testMethod2");
        System.out.println(username);
        System.out.println(password);
        return "success";
    }

    @RequestMapping(value = "/testMethod3", method = RequestMethod.POST, params = { "username", "password" })
    public String testMethod3(String username, String password) {
        System.out.println("testMethod3");
        System.out.println(username);
        System.out.println(password);
        return "success";
    }
}
