package org.hrd.homeworkspringdatajpa.model.dto.requst;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;
}
