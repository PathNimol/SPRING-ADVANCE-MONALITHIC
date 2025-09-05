package org.hrd.homeworkspringdatajpa.model.enity;

import jakarta.persistence.*;
import lombok.*;
import org.hrd.homeworkspringdatajpa.base.BaseController;
import org.hrd.homeworkspringdatajpa.model.dto.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product extends BaseController {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    private String description;

    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .productId(id)
                .name(name)
                .unitPrice(unitPrice)
                .description(description)
                .build();
    }

}
