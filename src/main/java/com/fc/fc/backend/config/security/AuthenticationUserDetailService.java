package com.fc.fc.backend.config.security;

import com.fc.fc.backend.bean.SessionDataBean;
import com.fc.fc.backend.bean.User;
import com.fc.fc.backend.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AuthenticationUserDetailService
        implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {

        User user = userService.findByUsername(username);

        SessionDataBean sessionDataBean = null;

        sessionDataBean = new SessionDataBean(user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getAccess()));//static as of now need to enhance once implemented properly
        return new AuthenticationUser(sessionDataBean, user.getPassword(), true, true,
                true, authorities);
    }
}
