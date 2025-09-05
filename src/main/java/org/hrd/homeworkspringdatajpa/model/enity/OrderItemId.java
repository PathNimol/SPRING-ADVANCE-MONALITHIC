package org.hrd.homeworkspringdatajpa.model.enity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class OrderItemId implements Serializable {

    private Long orderId;
    private Long productId;

}