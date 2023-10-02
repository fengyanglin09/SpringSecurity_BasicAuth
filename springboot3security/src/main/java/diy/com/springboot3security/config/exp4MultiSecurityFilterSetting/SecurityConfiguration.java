package diy.com.springboot3security.config.exp4MultiSecurityFilterSetting;


import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    @Order(100)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/user")
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    @Order(101)
    SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin")
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    @Order(102)
    SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/")
                .authorizeHttpRequests(authConfig -> {
                    authConfig.anyRequest().permitAll();
                })
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    @Order(103)
    SecurityFilterChain securityFilterChain3(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authConfig -> {
                    authConfig.anyRequest().denyAll();
                })
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }

    @Bean
    DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:testdb")
                .username("sa")
                .password("")
                .build();
    }


    @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        return args -> {
            UserDetails user = User.builder()
                    .username("user")
                    .password("{noop}password")
                    .roles("USER")
                    .build();
            UserDetails admin = User.builder()
                    .username("admin")
                    .password("{noop}password")
                    .roles("USER", "ADMIN")
                    .build();
            jdbcUserDetailsManager.createUser(user);
            jdbcUserDetailsManager.createUser(admin);
        };
    }
}
