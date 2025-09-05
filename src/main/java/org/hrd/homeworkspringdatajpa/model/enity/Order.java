package org.hrd.homeworkspringdatajpa.model.enity;
import jakarta.persistence.*;
import lombok.*;
import org.hrd.homeworkspringdatajpa.base.BaseController;
import org.hrd.homeworkspringdatajpa.model.dto.response.OrderResponse;
import org.hrd.homeworkspringdatajpa.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order extends BaseController {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItem;

    public OrderResponse toResponse() {
        return OrderResponse.builder()
                .orderId(id)
                .orderDate(orderDate)
                .totalAmount(totalAmount)
                .status(status)
                .customerResponse(customer.toResponse())
                .productResponses(orderItem.stream()
                        .map(orderItem -> orderItem.getProduct().toResponse())
                        .collect(Collectors.toList()))
                .build();
    }

}