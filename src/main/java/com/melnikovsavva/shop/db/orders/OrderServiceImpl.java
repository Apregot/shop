package com.melnikovsavva.shop.db.orders;

import com.melnikovsavva.shop.db.goods.CakeEntity;
import com.melnikovsavva.shop.db.goods.CakeRepository;
import com.melnikovsavva.shop.db.orders.enums.OrderStatus;
import com.melnikovsavva.shop.db.users.UserEntity;
import com.melnikovsavva.shop.rest.dto.order.Order;
import com.melnikovsavva.shop.db.users.UserRepository;
import com.melnikovsavva.shop.rest.dto.order.Purchase;
import com.melnikovsavva.shop.rest.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CakeRepository cakeRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CakeRepository cakeRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cakeRepository = cakeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        mapCommonInfoOrderToOrderEntity(order, orderEntity);
        orderEntity.setStatus(OrderStatus.NEW);
        orderEntity.setUser(userRepository.findUserEntityByNumber(order.getUser().getNumber()));
        List<PurchaseEntity> purchases = new ArrayList<>();
        List<Purchase> purchaseList = order.getPurchases();

        order.getPurchases().forEach((p) -> {
            if(p.getId() != null) {
                CakeEntity cakeEntity = cakeRepository.getById(p.getId());
                PurchaseEntity purchaseEntity = new PurchaseEntity();
                //purchaseEntity.setCake();
                purchaseEntity.setCake(cakeEntity);
                purchaseEntity.setOrder(orderEntity);
                purchaseEntity.setNumber(p.getNumber());
                purchases.add(purchaseEntity);
            }
        });
        orderEntity.setPurchases(purchases);
        orderRepository.save(orderEntity);
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        return mapOrderEntityToOrder(orderEntity.get());
    }

    @Override
    public List<Order> getSomeOrder(Integer page) {
        Pageable limit = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "status"));
        //List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderEntity> orderEntities = orderRepository.findAll(limit).toList();
        //Log.info();
        return orderEntities.stream().map(this::mapOrderEntityToOrder).collect(Collectors.toList());
    }

    @Override
    public void updateOrderWithoutCakes(Order order) {
        Long id = order.getId();
        OrderEntity orderEntity = orderRepository.getById(id);
        orderEntity.setDelivery(order.getDelivery());
        orderEntity.setDeliveryDate(LocalDate.parse(order.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //orderEntity.setDeliveryDate(order.getDate());
        orderEntity.setDeliveryAddress(order.getAddress());
        orderEntity.setPayment(order.getPayment());
        orderEntity.setStatus(order.getStatus());
        orderRepository.save(orderEntity);
    }

    @Override
    public void updateOrder(Order order) {
        OrderEntity orderEntity = orderRepository.getById(order.getId());
        mapCommonInfoOrderToOrderEntity(order, orderEntity);
        orderEntity.setStatus(order.getStatus());

        Set<Long> oldIds = orderEntity.getPurchases()
                .stream()
                .map(purchaseEntity -> purchaseEntity.getCake().getId())
                .collect(Collectors.toSet());

        Set<Long> newIds = order.getPurchases()
                .stream()
                .map(Purchase::getId)
                .collect(Collectors.toSet());

        Set<Long> idsToRemove = setDifference(oldIds, newIds);

        Set<Long> idsToAdd = setDifference(newIds, oldIds);

        idsToRemove.forEach(i -> {
            List<PurchaseEntity> purchasesToDelete = orderEntity.getPurchases()
                    .stream()
                    .filter(j -> j.getCake().getId().equals(i))
                    .collect(Collectors.toList());
            orderEntity.getPurchases().removeAll(purchasesToDelete);
        });

        orderEntity
                .getPurchases()
                .forEach(purchaseEntity -> purchaseEntity
                        .setNumber(order.getPurchases()
                                .stream()
                                .filter(p->p.getId().equals(purchaseEntity.getCake().getId()))
                                .collect(Collectors.toList())
                                .get(0)
                                .getNumber()
                        )
                );

        idsToAdd.forEach(i -> {
            CakeEntity cakeEntity = cakeRepository.getById(i);
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCake(cakeEntity);
            purchaseEntity.setOrder(orderEntity);
            purchaseEntity.setNumber(order.getPurchases()
                    .stream()
                    .filter(p->p.getId().equals(i))
                    .collect(Collectors.toList())
                    .get(0)
                    .getNumber());
            orderEntity.getPurchases().add(purchaseEntity);
        });
        orderRepository.save(orderEntity);
    }

    @Override
    public List<Order> getOrderByNumber(String number, Integer page) {
        Pageable limit = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "status"));
        List<OrderEntity> orderEntities = orderRepository.findAllByUser_Number(number, limit);
        return orderEntities.stream().map(this::mapOrderEntityToOrder).collect(Collectors.toList());
    }

    private void mapCommonInfoOrderToOrderEntity(Order order, OrderEntity orderEntity){
        orderEntity.setDeliveryDate(LocalDate.parse(order.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //orderEntity.setDeliveryDate(order.getDate());
        orderEntity.setDelivery(order.getDelivery());
        orderEntity.setPayment(order.getPayment());
        orderEntity.setDeliveryAddress(order.getAddress());
    }
    private Order mapOrderEntityToOrder(OrderEntity orderEntity){
        Order order = new Order();
        order.setAddress(orderEntity.getDeliveryAddress());
        order.setDate(orderEntity.getDeliveryDate().toString());
        //order.setDate(orderEntity.getDeliveryDate());
        order.setPayment(orderEntity.getPayment());
        order.setStatus(orderEntity.getStatus());
        order.setId(orderEntity.getId());
        order.setDelivery(orderEntity.getDelivery());
        List<Purchase> purchases = orderEntity.getPurchases()
                .stream().map(c -> {
                    Purchase purchase = new Purchase();
                    purchase.setId(c.getCake().getId());
                    purchase.setNumber(c.getNumber());
                    return purchase;
        }).collect(Collectors.toList());
        List<Long> cakeIds = orderEntity.getPurchases()
                        .stream().map(PurchaseEntity::getId).collect(Collectors.toList());
        order.setCakeIds(cakeIds);
        order.setPurchases(purchases);
        User user = new User();
        UserEntity ue = orderEntity.getUser();
        user.setId(ue.getId());
        user.setNumber(ue.getNumber());
        user.setName(ue.getName());
        order.setUser(user);
        return order;
    }

    private <T> Set<T> setDifference(Set<T> set1, Set<T> set2){
        Set<T> diff = new HashSet<>(set1);
        diff.removeAll(set2);
        return diff;
    }
}
