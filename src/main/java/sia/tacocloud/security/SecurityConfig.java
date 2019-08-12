package sia.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

//    @Autowired
//    UserDetailsService userDetailsService;

    @Autowired
    UserRepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/design", "/orders")
                    .hasRole("USER")
                .antMatchers("/", "/**")
                    .permitAll()

                .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .usernameParameter("user")
                        .passwordParameter("pwd")

                .and()
                    .logout()
                        .logoutSuccessUrl("/")

                // ignores CRSF token on paths that have "/h2/"
                .and()
                    .csrf()
                        .ignoringAntMatchers("/h2/**")

                // allows connecting to H2 console
                .and()
                    .headers()
                        .frameOptions()
                        .sameOrigin();
    }

}
