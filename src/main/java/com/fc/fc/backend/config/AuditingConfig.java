/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fc.fc.backend.config;

import com.fc.fc.backend.config.security.AuthenticationUser;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 *
 * @author jaynam
 */

public class AuditingConfig {

//    @Bean
//    public AuditorAware<Long> createAuditorProvider() {
//        return new SecurityAuditor();
//    }
//
//    @Bean
//    public AuditingEntityListener createAuditingListener() {
//        return new AuditingEntityListener();
//    }
//
//    public static class SecurityAuditor implements AuditorAware<Long> {
//
//        @Override
//        public Optional<Long> getCurrentAuditor() {
//            Long id = null;
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//            if (securityContext.getAuthentication() != null && (securityContext.getAuthentication() instanceof OAuth2Authentication)) {
//                OAuth2Authentication authentication = (OAuth2Authentication) securityContext.getAuthentication();
//                AuthenticationUser authenticationUser = (AuthenticationUser) authentication.getPrincipal();
//                if (authenticationUser.getSessionDataBean() != null) {
//                    id = authenticationUser.getSessionDataBean().getId();
//                } else {
//                    id = 0l;
//                }
//            }
//            return Optional.of(id);
//        }
//    }

}
