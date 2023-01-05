package com.hseea.advance.oauthapi.api;

import com.hseea.advance.oauthapi.config.FeignHeaderConfig;
import com.hseea.advance.oauthapi.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "oauth-user-service",
        url = "http://localhost:8888/oauth-service",
        configuration = {FeignHeaderConfig.class})
public interface SSOUserService {
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    User createUser(User user);
}
