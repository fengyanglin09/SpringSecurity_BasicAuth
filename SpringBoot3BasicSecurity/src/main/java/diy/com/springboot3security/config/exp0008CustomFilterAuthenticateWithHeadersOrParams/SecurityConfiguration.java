package diy.com.springboot3security.config.exp0008CustomFilterAuthenticateWithHeadersOrParams;


import diy.com.springboot3security.persistent.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    final private UsersRepository usersRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("ROLE_USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN");
                    authConfig.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new MySecurityHeaderFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new MySecurityParamFilter(), MySecurityHeaderFilter.class)
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
