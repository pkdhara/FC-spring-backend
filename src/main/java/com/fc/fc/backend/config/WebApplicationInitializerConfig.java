package com.fc.fc.backend.config;


import com.fc.fc.backend.bean.SessionDataBean;
import com.fc.fc.backend.config.security.AuthenticationUser;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 *
 * @author Satyajit
 */
@Configuration
public class WebApplicationInitializerConfig {

//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        LOGGER.info("Set the Timezone to UTC by default");
//        //Commented for now as we are not having timezone management now
////        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }
//
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SessionDataBean sessionDataBean() {        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        OAuth2Authentication authentication = (OAuth2Authentication) securityContext.getAuthentication();
        AuthenticationUser authenticationUser = (AuthenticationUser) authentication.getPrincipal();
        return authenticationUser.getSessionDataBean();
    }
}
