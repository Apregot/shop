package com.melnikovsavva.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "Short Info about cakes")
@Validated
public class Cake {
    //@NotNull
    @Schema(description = "Id of cake", required = true)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Schema(description = "Name of cake", required = true)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Schema(description = "Amount of calories", required = true)
    @JsonProperty("calories")
    private BigDecimal calories;

    @NotNull
    @Schema(description = "Image Relative Url", required = true)
    @JsonProperty("image")
    private String image;

    @NotNull
    @Schema(description = "Cake price", required = true)
    @JsonProperty("price")
    private BigDecimal price;

    @NotNull
    @Schema(description = "Cake weight", required = true)
    @JsonProperty("weight")
    private BigDecimal weight;

}
