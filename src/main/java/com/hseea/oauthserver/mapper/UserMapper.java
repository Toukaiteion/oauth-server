package com.hseea.oauthserver.mapper;

import com.hseea.oauthserver.entity.UserModel;

public interface UserMapper {
    UserModel findUserByName(String username);
}
