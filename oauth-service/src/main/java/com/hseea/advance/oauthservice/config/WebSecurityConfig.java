package com.hseea.advance.oauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    * ResourceServerConfig也重写了相同的方法，但是其order小，即优先级高，会覆盖这里的配置；
    * 其他重写方法不受影响
    * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().ignoringAntMatchers("/oauth-service/**")
//                .and()
//                .authorizeRequests().antMatchers("/**").permitAll();
        super.configure(http);
    }
    /*
     * 密码加密工具类，不可逆
     * */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    /*
     * 是为了实现oauth2的password模式必须要指定的授权管理bean
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
