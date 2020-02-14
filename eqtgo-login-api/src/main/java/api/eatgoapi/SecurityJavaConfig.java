package api.eatgoapi;

import api.eatgoapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;
import java.io.UnsupportedEncodingException;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .formLogin().disable()
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil() throws UnsupportedEncodingException {
        return new JwtUtil(secret);
    }
}
