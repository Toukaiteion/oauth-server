package com.hseea.advance.oauthservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class TokenEnhanceModel extends User {

    private String username;

    private Integer userId;

    private String tel;

    public TokenEnhanceModel(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
    }

    public TokenEnhanceModel(Integer userId, String username, String password, String tel, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.username = username;
        this.tel = tel;
    }
}
