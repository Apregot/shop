package com.melnikovsavva.shop.rest.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.melnikovsavva.shop.rest.dto.cake.Cake;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Schema(description = "Info about purchase")
@Validated
public class Purchase {

//    @Null
//    @Schema(description = "Id of order", required = false)
//    @JsonProperty("id")
//    private Long id;

//    @NotNull
//    @Schema(description = "cake", required = true)
//    @JsonProperty("payment")
//    private Cake cake;

//    @NotNull
//    @Schema(description = "order", required = true)
//    @JsonProperty("payment")
//    private Order order;

    private Long id;

    @NotNull
    @Schema(description = "amount of cakes", required = true)
    @JsonProperty("payment")
    private Integer number;

}
