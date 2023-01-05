package com.hseea.advance.oauthservice.controller;

import com.hseea.advance.oauthapi.entity.User;
import com.hseea.advance.oauthservice.common.Constant;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.OAUTH_SERVICE)
public class UserController {
    @PostMapping("/user")
    public User createUser(@RequestBody User user){
        System.out.println(user);
        return new User();
    }
}
