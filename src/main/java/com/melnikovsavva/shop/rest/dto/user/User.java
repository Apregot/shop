package com.melnikovsavva.shop.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.melnikovsavva.shop.orders.OrderEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Info about user")
@Validated
public class User {
    @Null
    @Schema(description = "Id of user", required = false)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Schema(description = "Phone number of user", required = true)
    @JsonProperty("number")
    private String number;

    @NotNull
    @Schema(description = "Name of user", required = true)
    @JsonProperty("name")
    private String name;

}
