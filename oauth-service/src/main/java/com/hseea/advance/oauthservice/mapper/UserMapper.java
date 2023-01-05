package com.hseea.advance.oauthservice.mapper;

import com.hseea.advance.oauthservice.entity.UserModel;

public interface UserMapper {
    UserModel findUserByName(String username);
}
