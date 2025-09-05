package org.hrd.homeworkspringdatajpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hrd.homeworkspringdatajpa.base.ApiResponse;
import org.hrd.homeworkspringdatajpa.model.dto.requst.OrderRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.OrderResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.enums.OrderProperty;
import org.hrd.homeworkspringdatajpa.model.enums.OrderStatus;
import org.hrd.homeworkspringdatajpa.service.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //Create order(s) for a customer
    @PostMapping("/{customer-id}")
    @Operation(summary = "Create order(s) for a customer")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> createOrdersForCustomer(@Positive @PathVariable("customer-id") Long customerId, @Valid @RequestBody List<OrderRequest> requests) {
        ApiResponse<List<OrderResponse>> response = ApiResponse.<List<OrderResponse>>builder()
                .message("Orders created successfully!")
                .payload(orderService.createOrdersForCustomer(customerId,requests))
                .status("CREATED")
                .build();
        return ResponseEntity.ok(response);
    }

    // Get order details by ID
    @GetMapping("/{order-id}")
    @Operation(summary = "Get order details by ID")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderDetails(@Positive @PathVariable("order-id") Long orderId) {
        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Order details by ID: " + orderId + " successfully!")
                .payload(orderService.getOrderDetails(orderId))
                .status("FOUND")
                .build();
        return ResponseEntity.ok(response);
    }

    // List orders by customer (paginated)
    @GetMapping("/customers/{customer-id}")
    @Operation(summary = "List orders by customer ID (paginated)")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> listOrdersByCustomerId(@Positive @PathVariable("customer-id") Long customerId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, OrderProperty orderProperty, Sort.Direction direction) {

        PageResponse<OrderResponse> orderResponse = orderService.listOrdersByCustomerId(customerId, page, size, orderProperty, direction);

        ApiResponse<PageResponse<OrderResponse>> response = ApiResponse.<PageResponse<OrderResponse>>builder()
                .message("Orders by customer ID: " + customerId + " successfully!")
                .payload(orderResponse)
                .status("FOUND")
                .build();
        return ResponseEntity.ok(response);
    }

    // Update order status
    @PutMapping("/{order-id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@Positive @PathVariable("order-id") Long orderId, @RequestParam OrderStatus status){
        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Order status updated successfully!")
                .payload(orderService.updateOrderStatus(orderId, status))
                .status("UPDATED")
                .build();
        return  ResponseEntity.ok(response);
    }

    // Delete an order by ID
    @DeleteMapping("/{order-id}")
    @Operation(summary = "Delete an order by ID")
    public ResponseEntity<ApiResponse<OrderResponse>> deleteOrderById(@Positive @PathVariable("order-id") Long orderId) {
        orderService.deleteOrderById(orderId);
        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Order status updated successfully!")
                .payload(null)
                .status("DELETE")
                .build();
        return  ResponseEntity.ok(response);
    }
}
