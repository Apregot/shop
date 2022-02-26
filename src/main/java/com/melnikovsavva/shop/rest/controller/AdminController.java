package com.melnikovsavva.shop.rest.controller;

import com.melnikovsavva.shop.db.goods.CakeService;
import com.melnikovsavva.shop.db.orders.OrderService;
import com.melnikovsavva.shop.db.users.UserService;
import com.melnikovsavva.shop.rest.dto.cake.Cake;
import com.melnikovsavva.shop.rest.dto.cake.Cakes;
import com.melnikovsavva.shop.rest.dto.order.Order;
import com.melnikovsavva.shop.rest.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${IMAGE_UPLOAD_PATH}")
    private String pathUpload;
    @Autowired
    private HttpServletRequest request;


    private final UserService userService;
    private final OrderService orderService;
    private final CakeService cakeService;

    @Autowired
    public AdminController(UserService userService, OrderService orderService, CakeService cakeService){
        this.userService = userService;
        this.orderService = orderService;
        this.cakeService = cakeService;
    }

    @GetMapping("")
    public String admin(){
        return "admin";
    }

    @GetMapping("/user")
    public String addUserForm(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "") String number, Model model){
        User user = new User();
        List<User> userList;
        if(!number.equals(""))
            userList = Stream.of(userService.getUserByNumber(number)).collect(Collectors.toList());
        else
            userList = userService.getSomeUsers(Integer.valueOf(page));
        model.addAttribute("user", user);
        model.addAttribute("usersList", userList);
        model.addAttribute("page", Integer.valueOf(page));
        return "user";
    }

    @PostMapping("/user")
    public String addUserSubmit(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        userService.getOrCreateUser(user);
        return "user";
    }

    @PostMapping("/updateCommonInfoAboutUser")
    public String updateCommonInfoAboutUser(@ModelAttribute User user){
        userService.updateUser(user);
        return "redirect:/admin/user";
    }



    @GetMapping("/orders")
    public String orderPage(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "") String number, Model model){
        Order order = new Order();
        List<Order> orderList;
        if(!number.equals(""))
            orderList = orderService.getOrderByNumber(number, Integer.valueOf(page));
        else
            orderList = orderService.getSomeOrder(Integer.valueOf(page));
        List<Cake> cakeList = cakeService.getSomeCake(0, 100);
        model.addAttribute("cakeList", cakeList);
        model.addAttribute("order", order);
        model.addAttribute("orderList", orderList);
        model.addAttribute("page", Integer.valueOf(page));
        return "orders";
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable Long id, Model model){
        Order order = orderService.getOrderById(id);
        List<Cake> cakeList = cakeService.getCakes().getCakeList();
        model.addAttribute("cakeList", cakeList);
        model.addAttribute("order", order);
        return "order";
    }

    @PostMapping("/addOrder")
    public String addOrder(@ModelAttribute Order order){
        orderService.createOrder(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/updateCommonInfoAboutOrder")
    public String updateOrderWithoutCakes(@ModelAttribute Order order){
        orderService.updateOrderWithoutCakes(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/updateOrder")
    public String updateOrder(@ModelAttribute Order order){
        orderService.updateOrder(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:http://localhost:9091/auth/realms/OnlineStore/protocol/openid-connect/logout?redirect_uri=http://localhost:8080/admin";
    }

    @GetMapping("/cakes")
    public String cakes(@RequestParam(defaultValue = "0") String page, Model model){
        Cake cake = new Cake();
        List<Cake> cakes = cakeService.getSomeCake(Integer.valueOf(page), 100);
        model.addAttribute("cake", cake);
        model.addAttribute("cakeList", cakes);
        return "cakes";
    }

    @PostMapping("/updateCommonInfoAboutCake")
    public String updateCommonInfoAboutCake(@ModelAttribute Cake cake){
        cakeService.updateCake(cake);
        return "redirect:/admin/cakes";
    }

    @PostMapping("/cake")
    public String createOrUpdateCake(@ModelAttribute Cake cake,
                                     @RequestParam MultipartFile imgFile){
        if(!imgFile.isEmpty()){
            String fileName = uploadImage(imgFile);
            cake.setImage(fileName);
        }
        cakeService.addCake(cake);
        return "redirect:/admin/cakes";
    }

    @GetMapping("/cake/{id}")
    public String cake(@PathVariable Long id, Model model){
        Cake cakeDetail = cakeService.getCake(id);
        model.addAttribute("cake", cakeDetail);
        model.addAttribute("lineSep", "\n");
        return "cake";
    }

    private String uploadImage(MultipartFile imgFile){

        Path relativePath = Paths.get("src/main/resources/static");
        //Path absolutePath = relativePath.toAbsolutePath();
        Path absolutePath = Paths.get("C:\\Users\\mdv75\\IdeaProjects\\shop\\src\\main\\resources\\static");
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
        File file = new File(absolutePath + "/" + fileName);
        try {
            imgFile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
