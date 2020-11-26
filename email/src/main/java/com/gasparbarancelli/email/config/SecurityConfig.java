package com.gasparbarancelli.email.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   private static final String[] AUTH_WHITELIST = {
           "/swagger-ui",
           "/swagger-ui/**",
           "/documentation",
           "/api-documentation",
           "/api-documentation/**"
   };

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers(AUTH_WHITELIST).permitAll()
              .antMatchers("/**").authenticated()
              .and()
              .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
   }

}
