package org.hrd.homeworkspringdatajpa.model.dto.requst;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hrd.homeworkspringdatajpa.model.enity.Customer;
import org.hrd.homeworkspringdatajpa.model.enity.CustomerAccount;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{8,10}$", message = "Phone number must be 8–10 digits")
    private String phoneNumber;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public Customer toEntity() {
        return Customer.builder()
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .customerAccount(CustomerAccount.builder()
                        .username(username)
                        .password(password)
                        .build())
                .build();
    }
}
