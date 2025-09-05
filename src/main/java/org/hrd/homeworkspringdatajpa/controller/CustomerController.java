package org.hrd.homeworkspringdatajpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hrd.homeworkspringdatajpa.base.ApiResponse;
import org.hrd.homeworkspringdatajpa.model.dto.requst.CustomerRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.CustomerResponse;
import org.hrd.homeworkspringdatajpa.model.enums.CustomerProperty;
import org.hrd.homeworkspringdatajpa.service.CustomerService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    // 1. Get All Customers
    @GetMapping
    @Operation(summary = "Get paginated list of customers")
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponse>>> getAllCustomers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam CustomerProperty customerProperty,
            @RequestParam Sort.Direction direction) {

        PageResponse<CustomerResponse> customerPage = customerService.getAllCustomers(page, size, customerProperty, direction);

        ApiResponse<PageResponse<CustomerResponse>> response = ApiResponse.<PageResponse<CustomerResponse>>builder()
                .message("All customers fetched successfully!")
                .payload(customerPage)
                .status("FOUND")
                .build();

        return ResponseEntity.ok(response);
    }

    // 2. Get a customer by ID
    @GetMapping("/{customer-id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable("customer-id") Long customerId) {
        ApiResponse<CustomerResponse> response = ApiResponse.<CustomerResponse>builder()
                .message("Customer with ID: " + customerId + " fetched successfully!")
                .payload(customerService.getCustomerById(customerId))
                .status("FOUND")
                .build();
        return ResponseEntity.ok(response);
    }

    // 3. Create a customer
    @PostMapping
    @Operation(summary = "Create a customer")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest request) {
        ApiResponse<CustomerResponse> response = ApiResponse.<CustomerResponse>builder()
                .message("Customer created successfully!")
                .payload(customerService.createCustomer(request))
                .status("CREATED")
                .build();
        return ResponseEntity.ok(response);
    }

    // 4. Update a customer by ID
    @PutMapping("/{customer-id}")
    @Operation(summary = "Update a customer by ID")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomerById(
            @PathVariable("customer-id") Long customerId, @Valid @RequestBody CustomerRequest request) {
        ApiResponse<CustomerResponse> response = ApiResponse.<CustomerResponse>builder()
                .message("Customer with ID: " + customerId + " updated successfully!")
                .payload(customerService.updateCustomerById(customerId, request))
                .status("UPDATED")
                .build();
        return ResponseEntity.ok(response);
    }

    // 5. Delete a customer by ID
    @DeleteMapping("/{customer-id}")
    @Operation(summary = "Delete a customer by ID")
    public ResponseEntity<ApiResponse<CustomerResponse>> deleteCustomerById(@PathVariable("customer-id") Long customerId) {
        customerService.deleteCustomerById(customerId);
        ApiResponse<CustomerResponse> response = ApiResponse.<CustomerResponse>builder()
                .message("Customer with ID: " + customerId + " deleted successfully!")
                .payload(null)
                .status("DELETED")
                .build();
        return ResponseEntity.ok(response);
    }
}