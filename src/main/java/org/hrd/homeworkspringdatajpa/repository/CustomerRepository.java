package org.hrd.homeworkspringdatajpa.repository;

import org.hrd.homeworkspringdatajpa.model.enity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
