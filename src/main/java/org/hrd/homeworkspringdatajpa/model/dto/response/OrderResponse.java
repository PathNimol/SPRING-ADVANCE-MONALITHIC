package org.hrd.homeworkspringdatajpa.model.dto.response;

import lombok.*;
import org.hrd.homeworkspringdatajpa.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private CustomerResponse customerResponse;
    private List<ProductResponse> productResponses;
}
