package com.hseea.advance.oauthservice.service.impl;

import com.hseea.advance.oauthservice.entity.TokenEnhanceModel;
import com.hseea.advance.oauthservice.entity.UserModel;
import com.hseea.advance.oauthservice.mapper.UserMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
