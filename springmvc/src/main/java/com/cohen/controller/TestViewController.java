package com.cohen.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cohen.entity.Address;
import com.cohen.entity.User;

@Controller
// @SessionAttributes(value = { "user" }, types = String.class)
public class TestViewController {
    /**
     * 目标方法的返回值可以是ModelAndView类型，其中可以包含视图和模型信息
     * SpringMVC会把ModelAndView中的Model中的数据遍历出来放到request域对象中
     */
    @RequestMapping(value = "/testModelAndViewParams", method = RequestMethod.POST)
    public ModelAndView testModelAndViewParams(User user) {
        ModelAndView mv = new ModelAndView("success");
        mv.addObject("time", new Date());
        return mv;
    }

    /**
     * 目标方法可以添加ModelMap/Model/Map类型的参数,springMVC会自动把该map参数放入ModelAndView中
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/testModelMapParams", method = RequestMethod.POST)
    public String testModelMapParams(Map<String, Object> map) {
        map.put("time", new Date());
        return "success";
    }

    /**
     * 通过类上的SessionAttributes注解，@SessionAttributes(value = { "user" }, types =
     * String.class)，可以将返回到request域中的属性放到session域中
     * 该注解只能放到类的上边
     */
    @RequestMapping(value = "/testSessionAttribute", method = RequestMethod.POST)
    public String testSessionAttribute(Map<String, Object> map) {
        User user = new User();
        user.setUsername("root");
        user.setPassword(123);
        Address addr = new Address();
        addr.setCity("济南");
        addr.setProvince("山东");
        user.setAddress(addr);
        map.put("user", user);
        map.put("string", "string");
        return "success";
    }

    /**
     * 当请求该controller的任意一个方法时，都会先执行注解了@ModelAttribute的方法
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            User user = new User();
            user.setUsername("root");
            user.setPassword(123);
            Address addr = new Address();
            addr.setCity("济南");
            addr.setProvince("山东");
            user.setAddress(addr);
            System.out.println("先从数据库查询user" + user);
            map.put("user", user);
        } else {
            System.out.println("id为空");
        }
    }

    @RequestMapping(value = "/testModelAttribute", method = RequestMethod.POST)
    public String testModelAttribute(User user) {
        System.out.println("修改：" + user);
        return "success";
    }
}
