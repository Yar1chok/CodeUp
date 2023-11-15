package springClasses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> {
                auth.requestMatchers("/customLogin", "/", "/registration").permitAll();
                auth.anyRequest().authenticated();
                auth.requestMatchers("/menu").hasAnyRole("USER", "ADMIN");
                })
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/customLogin")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/menu", true)
                                .failureUrl("/customLogin?errorMessage=Невырный логин или пароль")
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}