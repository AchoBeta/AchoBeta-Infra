package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.oauth.config.auth.dto.LoginDataDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;
/**
 * <span>
 *     custom login token
 * </span>
 * 
 * @author jettcc in 2023/11/4
 * @version 1.0
 */
public class AuthenticationToken extends AbstractAuthenticationToken {
 
    private final Object principal;
    private final Object credentials;
    private LoginDataDetails details;
 
    public AuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
    }
 
    public AuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }
 
    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials, LoginDataDetails  details) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.details = details;
    }
 
    @Override
    public Object getCredentials() {
        return this.credentials;
    }
 
    @Override
    public Object getPrincipal() {
        return this.principal;
    }
 
    public LoginDataDetails getLoginData() {
        return this.details;
    }
 
    public void setLoginData(LoginDataDetails details) {
        this.details = details;
    }
 
    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
