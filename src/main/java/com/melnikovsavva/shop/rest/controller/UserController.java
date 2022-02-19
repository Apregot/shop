package com.melnikovsavva.shop.rest.controller;

import com.melnikovsavva.shop.exception.UserExistException;
import com.melnikovsavva.shop.db.goods.CakeService;
import com.melnikovsavva.shop.db.orders.OrderService;
import com.melnikovsavva.shop.rest.dto.cake.Cake;
import com.melnikovsavva.shop.rest.dto.cake.Cakes;
import com.melnikovsavva.shop.rest.dto.order.Order;
import com.melnikovsavva.shop.db.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    private final CakeService cakesService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public UserController(CakeService cakesService, OrderService orderService, UserService userService) {
        this.cakesService = cakesService;
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping(value = "cakes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cakes getCakes(){
        return cakesService.getCakes();
    }


    @GetMapping(value = "cake/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cake getCakeById(@PathVariable Long id){
        return cakesService.getCake(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "cake",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createCake(@Valid @RequestBody Cake newCake){
        cakesService.addCake(newCake);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "addOrder",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order newOrder){
        //try {
            userService.getOrCreateUser(newOrder.getUser());
  //      }
//        catch (UserExistException ignored){
//
//        }
        orderService.createOrder(newOrder);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }





    //    public CakeController(){
//        Cake cake1 = new Cake();
//        cake1.setId(1L);
//        cake1.setName("Napoleon");
//        cake1.setPrice(new BigDecimal(100));
//        cake1.setWeight(new BigDecimal(250));
//        cake1.setCalories(new BigDecimal(800));
//        cake1.setImage("cake1.jpg");
//
//        Cake cake2 = new Cake();
//        cake2.setId(2L);
//        cake2.setName("Rose");
//        cake2.setPrice(new BigDecimal(100));
//        cake2.setWeight(new BigDecimal(250));
//        cake1.setCalories(new BigDecimal(800));
//        cake2.setImage("cake1.jpg");
//
//        List<Cake> tmp = new ArrayList<Cake>();
//        tmp.add(cake1);
//        tmp.add(cake2);
//
//        cakeList.setCakeList(tmp);
//    }
}
