package com.hseea.advance.oauthservice.config;

import com.hseea.advance.oauthservice.service.impl.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer //开启授权服务
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    AuthenticationManager authenticationManager;
    @Autowired
    MyUserDetailServiceImpl myUserDetailService;
    @Autowired
    DataSource dataSource;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception{
        //允许表单提交，允许客户端访问oauth2授权接口，否则请求token会返回401
        security.allowFormAuthenticationForClients()
                //允许已授权用户访问checkToken接口和获取token接口
                .checkTokenAccess("isAuthenticated()")
                //获取public_key的端点
                .tokenKeyAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("client-1").secret(passwordEncoder.encode("client-1-password"))
//                .authorizedGrantTypes("authorization_code","password","refresh_token")
//                .scopes("all")
//                .accessTokenValiditySeconds(3600);
        JdbcClientDetailsServiceBuilder jdbcClientDetailsServiceBuilder = clients.jdbc(dataSource);
        jdbcClientDetailsServiceBuilder.passwordEncoder(passwordEncoder);
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter());
        endpoints.tokenStore(jwtTokenStore());
        //用于支持密码模式
        endpoints.authenticationManager(authenticationManager).userDetailsService(myUserDetailService);
    }

    @Bean
    public JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){

        JwtAccessTokenConverter jwtAccessTokenConverter = new CustomJwtTokenConverter();
        //jwtAccessTokenConverter.setSigningKey("hseea@smartplatform");
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter);
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair(){
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("oauth.jks"), "hseeaeesh".toCharArray());
        return factory.getKeyPair("oauth2");
    }
}
