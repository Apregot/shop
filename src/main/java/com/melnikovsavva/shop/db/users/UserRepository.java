package com.melnikovsavva.shop.db.users;

import com.melnikovsavva.shop.rest.dto.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsUserEntityByNumber(String number);

    UserEntity findUserEntityByNumber(String number);


    List<UserEntity> findAll();
}
