package com.melnikovsavva.shop.db.users;

import com.melnikovsavva.shop.db.orders.OrderEntity;
import com.melnikovsavva.shop.db.orders.OrderRepository;
import com.melnikovsavva.shop.db.orders.enums.OrderStatus;
import com.melnikovsavva.shop.exception.UserExistException;
import com.melnikovsavva.shop.rest.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.getById(id);
        return mapUserEntityToUser(userEntity, new User());
    }

    @Override
    public User getOrCreateUser(User user) {
        if(user.getNumber().equals("")) {
            return user;
        }
        UserEntity userEntity = userRepository.findUserEntityByNumber(user.getNumber());
        if(userEntity != null){
            user.setId(userEntity.getId());
            return user;
        }
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setName(user.getName());
        newUserEntity.setNumber(user.getNumber());
        Long id = userRepository.save(newUserEntity).getId();
        user.setId(id);
        return user;
    }

    @Override
    public List<User> getSomeUsers(Integer page){
        Pageable limit = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        List<UserEntity> userEntities = userRepository.findAll(limit).toList();
        return userEntities
                .stream()
                .map(ue -> mapUserEntityToUser(ue, new User()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(User user) {
        UserEntity userEntity = userRepository.getById(user.getId());
        userEntity.setName(user.getName());
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public User getUserByNumber(String number) {
        UserEntity userEntity = userRepository.findUserEntityByNumber(number);;
        return mapUserEntityToUser(userEntity, new User());
    }

    @Override
    public void deleteUser(String number) throws UserExistException {
        if(!userRepository.existsByNumber(number) ) {
            throw new UserExistException("User with NUMBER "+number+ " can't delete");
        }
        else {
            userRepository.deleteById( userRepository.findUserEntityByNumber(number).getId());
        }
    }



//|| !FinishAllOrders(number)
    boolean FinishAllOrders(String number){
        List <OrderEntity> orderList = orderRepository.findOrderEntitiesByUser_Number(number);
        for (OrderEntity el: orderList){
            if (!el.getStatus().equals(OrderStatus.FINISHED)) return false;
        }
        return true;
    }


    private User mapUserEntityToUser(UserEntity userEntity, User user){
        user.setId(userEntity.getId());
        user.setNumber(userEntity.getNumber());
        user.setName(userEntity.getName());
        return user;
    }


}
