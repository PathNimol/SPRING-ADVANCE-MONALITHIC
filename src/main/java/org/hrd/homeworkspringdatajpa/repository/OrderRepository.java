package org.hrd.homeworkspringdatajpa.repository;

import org.hrd.homeworkspringdatajpa.model.dto.response.OrderResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.enity.Customer;
import org.hrd.homeworkspringdatajpa.model.enity.Order;
import org.hrd.homeworkspringdatajpa.model.enity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCustomer(Customer customer, Pageable pageable);
}
