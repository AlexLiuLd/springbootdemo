package com.szyciov.springbootdemo.service;

import com.szyciov.springbootdemo.dao.UserMapper;
import com.szyciov.springbootdemo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public List<User> getUserList()
    {
        LOGGER.info("#####开始调用UserService方法....");
        return userMapper.getUserList();
    }

    public void TestJob()
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOGGER.info((sdf.format(new Date())+"*********任务每5秒执行一次进入测试"));
    }
}