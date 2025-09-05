package org.hrd.homeworkspringdatajpa.model.dto.response;

import lombok.*;
import org.hrd.homeworkspringdatajpa.base.BaseController;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseController {
    private Long productId;
    private String name;
    private BigDecimal unitPrice;
    private String description;

}
