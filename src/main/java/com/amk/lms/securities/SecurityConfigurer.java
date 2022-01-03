package com.amk.lms.securities;

import com.amk.lms.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthFilter authFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());

        return  provider;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
               .authorizeRequests()
               .antMatchers("/home/v1/hello").permitAll()
               .antMatchers("/user/v1/login").permitAll()
               .antMatchers("/user/v1/users/create").permitAll()
               .antMatchers(
                       "/v2/api-docs",
                       "/swagger-resources/**",
                       "/swagger-ui.html",
                       "/webjars/**",
                       "/swagger.json",
                       "/swagger-resources",
                       "/swagger-resources/configuration/ui",
                       "/swagger-resources/configuration/security").permitAll()
               .antMatchers("/swagger-ui/**", "/javainuse-openapi/**").permitAll()
//               .antMatchers("/customer/v1/**").permitAll()

               .anyRequest().authenticated()
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
