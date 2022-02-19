package com.melnikovsavva.shop.rest.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.melnikovsavva.shop.db.orders.enums.Delivery;
import com.melnikovsavva.shop.db.orders.enums.OrderStatus;
import com.melnikovsavva.shop.db.orders.enums.Payment;
import com.melnikovsavva.shop.rest.dto.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Info about order")
@Validated
public class Order {
    @Null
    @Schema(description = "Id of order", required = false)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Schema(description = "user info", required = true)
    @JsonProperty("user")
    private User user;

    @NotNull
    @Schema(description = "delivery need", required = true)
    @JsonProperty("delivery")
    private Delivery delivery;

    @NotNull
    @Schema(description = "payment type", required = true)
    @JsonProperty("payment")
    private Payment payment;

    @NotNull
    @Schema(description = "order status", required = true)
    @JsonProperty("status")
    private OrderStatus status;

    @NotNull
    @Schema(description = "delivery address", required = true)
    @JsonProperty("delivery_address")
    private String address;

    @NotNull
    @Schema(description = "delivery time", required = true)
    @JsonProperty("delivery_date")
    private String date;

    @NotNull
    @Schema(description = "purchases", required = true)
    @JsonProperty("purchases")
    private List<Purchase> purchases;

    @NotNull
    @Schema(description = "cake Ids", required = true)
    @JsonProperty("cakeIds")
    private List<Long> cakeIds;

}
