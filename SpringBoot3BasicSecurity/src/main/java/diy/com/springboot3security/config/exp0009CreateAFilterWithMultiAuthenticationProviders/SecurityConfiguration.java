package diy.com.springboot3security.config.exp0009CreateAFilterWithMultiAuthenticationProviders;


import diy.com.springboot3security.config.exp0008CustomFilterAuthenticateWithHeadersOrParams.MySecurityHeaderFilter;
import diy.com.springboot3security.config.exp0008CustomFilterAuthenticateWithHeadersOrParams.MySecurityParamFilter;
import diy.com.springboot3security.config.exp0009CreateAFilterWithMultiAuthenticationProviders.authproviders.MySecurityAuthenticationProvider;
import diy.com.springboot3security.config.exp0009CreateAFilterWithMultiAuthenticationProviders.authproviders.TestAuthenticationProvider;
import diy.com.springboot3security.config.exp0009CreateAFilterWithMultiAuthenticationProviders.securityfilters.MySecurityFilter;
import diy.com.springboot3security.persistent.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    final private UsersRepository usersRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEventPublisher publisher) throws Exception {

        List<AuthenticationProvider> lijst = new ArrayList<>();
        lijst.add(new MySecurityAuthenticationProvider(passwordEncoder(), usersRepository));
        lijst.add(new TestAuthenticationProvider());
        var providerManager = new ProviderManager(lijst);
        providerManager.setAuthenticationEventPublisher(publisher);


        http
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("ROLE_USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN");
                    authConfig.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new MySecurityFilter(providerManager), UsernamePasswordAuthenticationFilter.class)
                .formLogin(withDefaults()) // Login with browser and Build in Form
                .httpBasic(withDefaults()); // Login with Insomnia or Postman and Basic Auth
        return http.build();
    }

    @Bean
    UserDetailsService myUserDetailsService() {
        return new MyUserDetailsService(this.usersRepository);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successEvent() {
        return event -> {
            System.err.println("Success Login " + event.getAuthentication().getClass().getName() + " - " + event.getAuthentication().getName());
        };
    }

    @Bean
    public ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
        return event -> {
            System.err.println("Bad Credentials Login " + event.getAuthentication().getClass().getName() + " - " + event.getAuthentication().getName());
        };
    }
}
