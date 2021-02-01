package com.fc.fc.backend.config.security;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Satyajit
 */
@Configuration
@EnableAuthorizationServer
public class AuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationUserDetailService securityUserService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    @Lazy
    @Qualifier("tokenStore")
    private transient TokenStore tokenStore;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("isAuthenticated()");
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.tokenStore(tokenStore());
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(securityUserService);
        
        DefaultTokenServices tokenService = (DefaultTokenServices) endpoints.getConsumerTokenServices();
        tokenService.setAccessTokenValiditySeconds(1800);
        tokenService.setRefreshTokenValiditySeconds(24 * 60 * 60);
        tokenService.setClientDetailsService(new JdbcClientDetailsService(dataSource));
        endpoints.tokenServices(tokenService);
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    
    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String grantType = request.getParameter("grant_type");
        
        if (grantType != null && grantType.equals("password")) {
            //    System.out.println("In FAILED::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            String username = (String) event.getAuthentication().getPrincipal();
            System.out.println(username + "sddd");
        }
    }
    
    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event) {
        
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String grantType = request.getParameter("grant_type");
        
        if (grantType != null && grantType.equals("password")) {
            Authentication authenticationUser = event.getAuthentication();
            String clientId = request.getParameter("client_id");
            try {
                Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, authenticationUser.getName());
                if (tokens != null) {
                    tokens.stream().map((token) -> {
                        tokenStore.removeRefreshToken(token.getRefreshToken());
                        return token;
                    }).forEach((token) -> {
                        tokenStore.removeAccessToken(token);
                    });
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }
}
