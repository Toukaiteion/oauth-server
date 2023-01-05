package com.hseea.advance.oauthservice.config;

import com.hseea.advance.oauthservice.entity.TokenEnhanceModel;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class CustomJwtTokenConverter extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication authentication){
        TokenEnhanceModel principal = (TokenEnhanceModel)authentication.getUserAuthentication().getPrincipal();
        final Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("userId", principal.getUserId());
        additionalInformation.put("username", principal.getUsername());
        additionalInformation.put("mobile", principal.getTel());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInformation);
        return super.enhance(oAuth2AccessToken, authentication);
    }
}
