/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fc.fc.backend.service;

import com.fc.fc.backend.bean.User;
import com.fc.fc.backend.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pradeep
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
    public User findById(long id) {
        return userDao.findById(id);
    }
    
    public List<User> findAll() {
        Iterable<User> user = userDao.findAll();
        List<User> result = new ArrayList<>();
        user.forEach(result::add);
        return result;
    }
}
