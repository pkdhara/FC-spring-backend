/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fc.fc.backend.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *
 * @author pradeep
 */
public class SessionDataBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private Boolean isActive;
    @Autowired
    @Lazy
    @Qualifier("tokenStore")
    private transient TokenStore tokenStore;

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
    
    
    public SessionDataBean() {
    }

    public SessionDataBean(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.isActive = user.isActive();
    }

    public SessionDataBean(SessionDataBean sessionDataBean) {
        this.id = sessionDataBean.getId();
        this.username = sessionDataBean.getUsername();
        this.isActive = sessionDataBean.isIsActive();
    }

    public void update() {
        System.out.println("tokenstore::: " + tokenStore);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        OAuth2Authentication authentication = (OAuth2Authentication) securityContext.getAuthentication();

        Collection<OAuth2AccessToken> tokenCollection = tokenStore.findTokensByClientIdAndUserName("agd-ui", this.getUsername());
        tokenCollection.stream().forEach((oToken) -> {
            tokenStore.storeAccessToken(oToken, authentication);
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   

    

}
