package com.hseea.oauthserver.Service.impl;

import com.hseea.oauthserver.entity.TokenEnhanceModel;
import com.hseea.oauthserver.entity.UserModel;
import com.hseea.oauthserver.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/*
* spring security中实现用户身份验证的一种方式，也是最简便的一种，另外还有结合authenticationProvider的方式
* */
@Slf4j
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    /*
    * @param 用户名
    * */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel userModel = userMapper.findUserByName(s);
        if (ObjectUtils.isEmpty(userModel)){
            throw new UsernameNotFoundException("not username");
        }else{
            String role = "ROLE_ADMIN";
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
            return new TokenEnhanceModel(userModel.getId(),
                    userModel.getUserName(),
                    userModel.getPassword(),
                    userModel.getTel(),
                    authorities);
        }
    }
}
