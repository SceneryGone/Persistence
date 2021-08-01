package com.future.dao;

import com.future.db.User;

import java.util.List;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-08-01 14:22
 */
public interface UserDao {

    List<User> selectAll();

    User selectOne(User user);

}
