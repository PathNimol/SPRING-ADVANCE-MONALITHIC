package org.hrd.homeworkspringdatajpa.model.enity;

import jakarta.persistence.*;
import lombok.*;
import org.hrd.homeworkspringdatajpa.base.BaseController;
import org.hrd.homeworkspringdatajpa.model.dto.response.CustomerResponse;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
@Builder
public class Customer extends BaseController {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String name;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerAccount customerAccount;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Order> orders;

    public CustomerResponse toResponse() {
        return CustomerResponse.builder()
                .customerId(id)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .username(customerAccount != null ? customerAccount.getUsername() : null)
                .password(customerAccount != null ? customerAccount.getPassword() : null)
                .isActive(customerAccount != null ? customerAccount.getIsActive() : null)
                .build();
    }

}