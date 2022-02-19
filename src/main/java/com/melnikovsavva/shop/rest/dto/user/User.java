package com.melnikovsavva.shop.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Setter
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
