/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fc.fc.backend.dao;

import com.fc.fc.backend.bean.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author pradeep
 */
public interface UserDao extends CrudRepository<User, Long>{
    public User findByUsername(String username);
    public User findById(long id);
    public Iterable<User> findAll();
}
