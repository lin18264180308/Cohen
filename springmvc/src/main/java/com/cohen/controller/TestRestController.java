package com.cohen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/springmvc")
public class TestRestController {

    /**
     * 增
     */
    @RequestMapping(value = "/testRest", method = RequestMethod.POST)
    public String testRestPost() {
        System.out.println("增：test POST request of rest!");
        return "success";
    }

    /**
     * 删
     * 
     * @param id
     */
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
    public String testRestDelete(@PathVariable Integer id) {
        System.out.println("删：test DELETE request of rest! parameter id = " + id);
        return "success";
    }

    /**
     * 查
     * 
     * @param id
     */
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.GET)
    public String testRestGet(@PathVariable Integer id) {
        System.out.println("查：test GET request of rest! parameter id = " + id);
        return "success";
    }

    /**
     * 改
     * 
     * @param id
     */
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.PUT)
    public String testRestPut(@PathVariable Integer id) {
        System.out.println("改：test PUT request of rest! parameter id = " + id);
        return "success";
    }
}
