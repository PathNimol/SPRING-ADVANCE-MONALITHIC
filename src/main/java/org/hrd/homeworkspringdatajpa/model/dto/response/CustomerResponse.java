package org.hrd.homeworkspringdatajpa.model.dto.response;

import lombok.*;
import org.hrd.homeworkspringdatajpa.model.enity.Customer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private Long customerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
    private Boolean isActive;

}
