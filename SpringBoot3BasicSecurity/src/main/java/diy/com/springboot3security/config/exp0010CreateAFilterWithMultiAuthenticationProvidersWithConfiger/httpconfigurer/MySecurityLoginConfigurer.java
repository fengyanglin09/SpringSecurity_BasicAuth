package diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.httpconfigurer;




import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.authproviders.MySecurityAuthenticationProvider;
import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.authproviders.TestAuthenticationProvider;
import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.securityfilters.MySecurityFilter;
import diy.com.springboot3security.persistent.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@RequiredArgsConstructor
public class MySecurityLoginConfigurer extends AbstractHttpConfigurer<MySecurityLoginConfigurer, HttpSecurity>{

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;


    @Override
    public void init(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(new MySecurityAuthenticationProvider(passwordEncoder, usersRepository))
                .authenticationProvider(new TestAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new MySecurityFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

}
