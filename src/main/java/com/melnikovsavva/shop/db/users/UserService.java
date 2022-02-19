package com.melnikovsavva.shop.db.users;

import com.melnikovsavva.shop.exception.UserExistException;
import com.melnikovsavva.shop.rest.dto.user.User;

import java.util.List;

public interface UserService {
//    User getUser();
//    void addUser(User user)throws UserExistException;

    User getOrCreateUser(User user);
    User getUserById(Long id);
    List<User> getSomeUsers(Integer page);
    void updateUser(User user);
    User getUserByNumber(String number);
}
