package diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.securityauthentication;

import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;


/***
 * used for indicating if a user or a request is authenticated or not
 * */
public class MySecurityAuthentication implements Authentication {

    private static final long serialVersionUID = 1L;

    private final boolean isAuthenticated;
    private final String name;
    private final String password;
    private final MyUser myUser;
    private final Collection<GrantedAuthority> authorities;


    private MySecurityAuthentication(Collection<GrantedAuthority> authorities, String name, MyUser myUser, String password) {
        this.authorities = authorities;
        this.name = name;
        this.password = password;
        this.myUser = myUser;
        this.isAuthenticated = password == null;
    }


    //unauthenticated users have empty authority lists
    public static MySecurityAuthentication unauthenticated(String name, String password) {
        return new MySecurityAuthentication(Collections.emptyList(), name, null, password);
    }

    //authenticated users have non-empty authority lists
    public static MySecurityAuthentication authenticated(MyUser myUser) {
        return new MySecurityAuthentication(myUser.getAuthorities(), myUser.getUsername(), myUser, null);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return myUser;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Don't do this");
    }

    public String getPassword() {
        return password;
    }
}
