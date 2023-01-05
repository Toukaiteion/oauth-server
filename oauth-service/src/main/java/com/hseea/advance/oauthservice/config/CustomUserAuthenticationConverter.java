package com.hseea.advance.oauthservice.config;

import com.hseea.advance.oauthservice.entity.TokenEnhanceModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    @Override
    public Authentication extractAuthentication(Map<String, ?> map){
        if (map.containsKey("user_name")) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            String username = (String) map.get("username");
            String mobile = (String) map.get("mobile");
            Integer userId = (Integer) map.get("userId");
            TokenEnhanceModel user = new TokenEnhanceModel(userId, username, "N/A", mobile, authorities);
            return new UsernamePasswordAuthenticationToken(user, "N/A", authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map){
        Object authorities = map.get("authorities");
        if (authorities instanceof String){
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection){
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be eight a String or a Collection");
    }
}
