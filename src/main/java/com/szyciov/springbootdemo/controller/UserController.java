package com.szyciov.springbootdemo.controller;

import com.szyciov.springbootdemo.entity.User;
import com.szyciov.springbootdemo.service.UserService;
import com.szyciov.springbootdemo.util.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("User/Index")
    public ModelAndView UserIndex(HttpServletRequest request) throws NoSuchAlgorithmException {
        Map<String, Object> model = new HashMap<String, Object>();
        List<User> userList= userService.getUserList();
        model.put("userList",userList);
        model.put("haha", SystemConfig.getSystemProperty("haha"));
        model.put("environment",SystemConfig.getSystemProperty("environment"));
        return new ModelAndView("user/index",model);
    }
}
