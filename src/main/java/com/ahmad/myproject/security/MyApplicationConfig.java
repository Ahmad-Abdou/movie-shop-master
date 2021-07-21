package com.ahmad.myproject.security;

import com.ahmad.myproject.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static com.ahmad.myproject.registeration.appuser.AppUserRole.ADMIN;


@Configuration
@EnableWebSecurity
public class MyApplicationConfig extends WebSecurityConfigurerAdapter {

    private AppUserService appUserService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/**","/api/movie").permitAll()
                .antMatchers("/admin/api/**").hasRole(ADMIN.name())
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and().logout().logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }
}
