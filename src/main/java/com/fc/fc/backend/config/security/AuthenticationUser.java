package com.fc.fc.backend.config.security;

import com.fc.fc.backend.bean.SessionDataBean;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author pradeep
 */
public class AuthenticationUser extends User {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private SessionDataBean sessionDataBean;

    public SessionDataBean getSessionDataBean() {
        return sessionDataBean;
    }

    public void setSessionDataBean(SessionDataBean sessionDataBean) {
        this.sessionDataBean = sessionDataBean;
    }

    public AuthenticationUser(SessionDataBean sessionDatabean, String password, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(sessionDatabean.getUsername(), password, sessionDatabean.isIsActive(), accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.sessionDataBean = sessionDatabean;
    }
}
