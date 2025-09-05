package org.hrd.homeworkspringdatajpa.repository;

import org.hrd.homeworkspringdatajpa.model.enity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
