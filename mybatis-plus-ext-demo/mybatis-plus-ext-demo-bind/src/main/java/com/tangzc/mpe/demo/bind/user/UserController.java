package com.tangzc.mpe.demo.bind.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tangzc.mpe.bind.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("testAggFunc")
    public void testAggFunc() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class);
        List<User> users = userMapper.selectList(queryWrapper);
        Binder.bindOn(users, User::getHobbyNum);
        System.out.println(users.size());
    }
}
