package org.hrd.homeworkspringdatajpa.service;

import org.hrd.homeworkspringdatajpa.model.dto.requst.CustomerRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.CustomerResponse;
import org.hrd.homeworkspringdatajpa.model.enums.CustomerProperty;
import org.springframework.data.domain.Sort;

public interface CustomerService {
    PageResponse getAllCustomers(int page, int size, CustomerProperty customerProperty, Sort.Direction direction);

    CustomerResponse getCustomerById(Long customerId);

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomerById(Long customerId, CustomerRequest request);

    void deleteCustomerById(Long customerId);
}
