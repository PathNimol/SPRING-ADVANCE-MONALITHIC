package org.hrd.homeworkspringdatajpa.repository;

import org.hrd.homeworkspringdatajpa.model.enity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
