package edu.northeastern.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.entity.User;
import edu.northeastern.reggie.mapper.UserMapper;
import edu.northeastern.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
