package application.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/resources/**","/static/**", "/images/**", "/css/**","/customLogin", "/", "/registration").permitAll()
                        .requestMatchers("/menu").hasAnyRole("USER", "ADMIN")
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/customLogin")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/menu")
                                .failureUrl("/customLogin?errorMessage=Неверный логин или пароль")
                )
                .logout(logoutForm ->
                        logoutForm
                                .logoutSuccessUrl("/")
                )
                .httpBasic(http ->
                        http
                                .authenticationEntryPoint(
                                        (request, response, authException) ->
                                                response
                                                        .sendRedirect("/")))
                .build();
    }
}