package org.hrd.homeworkspringdatajpa.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hrd.homeworkspringdatajpa.exception.NotFoundException;
import org.hrd.homeworkspringdatajpa.model.dto.requst.OrderRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.OrderResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.enity.*;
import org.hrd.homeworkspringdatajpa.model.enums.OrderProperty;
import org.hrd.homeworkspringdatajpa.model.enums.OrderStatus;
import org.hrd.homeworkspringdatajpa.repository.CustomerRepository;
import org.hrd.homeworkspringdatajpa.repository.OrderRepository;
import org.hrd.homeworkspringdatajpa.repository.ProductRepository;
import org.hrd.homeworkspringdatajpa.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    @Override
    @Transactional
    public List<OrderResponse> createOrdersForCustomer(Long customerId, List<OrderRequest> orderRequest) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        Order order = Order.builder()
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        Map<Long, Integer> productQuantityMap = new HashMap<>();

        for (OrderRequest request : orderRequest) {
            productQuantityMap.merge(request.getProductId(), request.getQuantity(), Integer::sum);
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : productQuantityMap.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new NotFoundException("Product not found with id: " + entry.getKey()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItem.setId(new OrderItemId(savedOrder.getId(), product.getId()));

            orderItems.add(orderItem);
        }

        savedOrder.setOrderItem(orderItems);

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getProduct().getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        savedOrder.setTotalAmount(totalAmount);

        return List.of(orderRepository.save(savedOrder).toResponse()) ;

    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()  -> new NotFoundException("Order not found with id: " + orderId));
        return order.toResponse();
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(()  -> new NotFoundException("Order not found with id: " + orderId));
        order.setStatus(status);
        return orderRepository.save(order).toResponse() ;
    }

    @Override
    public void deleteOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()  -> new NotFoundException("Order not found with id: " + orderId));
        orderRepository.delete(order);
    }
    @Override
    public PageResponse<OrderResponse> listOrdersByCustomerId(
            Long customerId, int page, int size, OrderProperty orderProperty, Sort.Direction direction) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        Page<Order> orderPage = orderRepository.findByCustomer(customer,
                PageRequest.of(page - 1, size, Sort.by(direction, orderProperty.getFieldName())));

        List<OrderResponse> orderResponses = orderPage.stream()
                .map(Order::toResponse)
                .toList();

        PageResponse.Pagination pagination = new PageResponse.Pagination(
                orderPage.getTotalElements(),
                orderPage.getNumber() + 1,
                orderPage.getSize(),
                orderPage.getTotalPages()
        );

        return new PageResponse<>(orderResponses, pagination);
    }


}
