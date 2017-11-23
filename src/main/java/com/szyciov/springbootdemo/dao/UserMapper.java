package com.szyciov.springbootdemo.dao;

import com.szyciov.springbootdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getUserList();
}
