/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fc.fc.backend.controller;

import com.fc.fc.backend.bean.SessionDataBean;
import com.fc.fc.backend.bean.User;
import com.fc.fc.backend.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pradeep
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private SessionDataBean sessionDataBean;

    @GetMapping(value = "/user/{id}")
    public User findOne(@PathVariable Long id) {
        User entity = userService.findById(id);

        return entity;
    }

    @GetMapping(value = "/common/session")
    public SessionDataBean retrieveLoggedInUser() {
        return new SessionDataBean(sessionDataBean);
    }

    @GetMapping(value = "/user")
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping(value = "/user/accessfeature")
    public List<String> getAccessFeature(HttpServletRequest request) {

        List<String> accessCodes = new ArrayList<>();
        accessCodes.add("Users01");
        accessCodes.add("Users02");
        accessCodes.add("Admin01");
        accessCodes.add("Admin");
        return accessCodes;
    }
    
    @ResponseBody
    @GetMapping(value = "/user/getDashboardLocation")
    public String getDashboardLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessToken = request.getParameter("access_token");
        String username = sessionDataBean.getUsername();
        request.setAttribute("username", username);
        String jspParameter = request.getParameter("param_name");
                response.reset();
//        request.getRequestDispatcher("http://localhost:8443/loginByPass"+"?access_token=" 
//                + accessToken + "&username" + username)
//                .forward(request, response);
        System.out.println("sss " + jspParameter);
        response.sendRedirect("http://localhost:8443/loginByPass"+"?access_token=" 
                + accessToken + "&username" + username);
        return "";
    }
}
