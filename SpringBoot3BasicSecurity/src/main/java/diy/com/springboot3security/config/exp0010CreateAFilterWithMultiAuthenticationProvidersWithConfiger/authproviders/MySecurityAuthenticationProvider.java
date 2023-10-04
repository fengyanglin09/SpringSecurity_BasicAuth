package diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.authproviders;

import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.MyUser;
import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.MyUserDetailsService;
import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.securityauthentication.MySecurityAuthentication;
import diy.com.springboot3security.persistent.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MySecurityAuthenticationProvider implements AuthenticationProvider {

//    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.err.println("MySecurityAuthenticationProvider ");
        var authRequest = (MySecurityAuthentication) authentication;
        var password = authRequest.getPassword();
        var username = authRequest.getName();

        if ("Test".equals(username)) {
            return null;//return null to not authenticate by this authentication provider
        }

        final MyUserDetailsService myUserDetailsService = new MyUserDetailsService(this.usersRepository);

        final MyUser user = (MyUser) myUserDetailsService.loadUserByUsername(username);

        if (!this.passwordEncoder.matches(password.toString(), user.getPassword()) || !user.getUsername().equals(username)) {
            throw new BadCredentialsException("Invalid credentials!");
        }
        return MySecurityAuthentication.authenticated(user);
    }


    // the provider will be invoked if MySecurityAuthentication is passed as the parameter
    @Override
    public boolean supports(Class<?> authentication) {
        return MySecurityAuthentication.class.isAssignableFrom(authentication);
    }
}
