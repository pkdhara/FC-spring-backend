package com.fc.fc.backend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 *
 * @author Satyajit
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    @Autowired
    AuthExceptionEntryPoint authExceptionEntryPoint;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().cacheControl();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                //static resources
                .antMatchers("/").permitAll()
                .antMatchers("/encryption/**").permitAll()
                .antMatchers("/formOfParticular/**").permitAll()
                .antMatchers("/api/user/loginas").permitAll()
                .antMatchers("/api2/**").permitAll()
                .antMatchers("/api/user/newuserregistration").permitAll()
                .antMatchers("/api/user/otpverification/**").permitAll()
                .antMatchers("/api/user/generatesystemuser").permitAll()
                .antMatchers("/api/user").permitAll()
                //                .antMatchers("/index.html").permitAll()
                //                .antMatchers("/app/**").permitAll()
                //                .antMatchers("/common-files/**").permitAll()
                //                .antMatchers("/enduser/**").permitAll()
                //                .antMatchers("/eroobroobridge/**").permitAll()
                //                .antMatchers("/images/**").permitAll()
                //                .antMatchers("/superadmin/**").permitAll()

                //restrict all except above
                .antMatchers("/agd-ui/**").permitAll()
                .antMatchers("/agd-ui -2.0/**").permitAll()
                .antMatchers("/api/**").access("#oauth2.hasScope('write')");
        http.csrf().disable();
        http.httpBasic().authenticationEntryPoint(authExceptionEntryPoint) ;

    }
}
