package com.cohen.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cohen.entity.User;

@Controller
public class TestParamsController {

    @RequestMapping(value = "/testParams", method = RequestMethod.POST)
    public String testParams(@RequestParam(value = "username") String username,
            @RequestParam(value = "password", required = false) Integer password) {
        System.out.println("testParams!username = " + username + ",password = " + password);
        return "success";
    }

    @RequestMapping(value = "/testPojoParams", method = RequestMethod.POST)
    public String testPojoParams(User user) {
        System.out.println("testPojoParams " + user);
        return "success";
    }

    @RequestMapping(value = "/testWriterParams", method = RequestMethod.POST)
    public void testWriterParams(Writer out) throws IOException {
        out.write("hello world!");
    }

    @RequestMapping(value = "/testModelAndViewParams", method = RequestMethod.POST)
    public ModelAndView testModelAndViewParams(User user) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("success");
        mv.addObject("time", new Date());
        return mv;
    }
}
