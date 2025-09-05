package org.hrd.homeworkspringdatajpa.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.hrd.homeworkspringdatajpa.exception.NotFoundException;
import org.hrd.homeworkspringdatajpa.model.dto.requst.CustomerRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.CustomerResponse;
import org.hrd.homeworkspringdatajpa.model.enity.Customer;
import org.hrd.homeworkspringdatajpa.model.enity.CustomerAccount;
import org.hrd.homeworkspringdatajpa.model.enums.CustomerProperty;
import org.hrd.homeworkspringdatajpa.repository.CustomerRepository;
import org.hrd.homeworkspringdatajpa.service.CustomerService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public PageResponse<CustomerResponse> getAllCustomers(int page, int size, CustomerProperty customerProperty, Sort.Direction direction) {
        Page<Customer> customerPage = customerRepository.findAll(
                PageRequest.of(page - 1, size, Sort.by(direction, customerProperty.name().toLowerCase()))
        );

        List<CustomerResponse> customerResponses = customerPage.map(Customer::toResponse).toList();

        PageResponse.Pagination pagination = new PageResponse.Pagination(
                customerPage.getTotalElements(),
                customerPage.getNumber() + 1,
                customerPage.getSize(),
                customerPage.getTotalPages()
        );

        return new PageResponse<>(customerResponses, pagination);
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + customerId));
        return customer.toResponse();
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        try {
            Customer customer = request.toEntity();

            CustomerAccount account = customer.getCustomerAccount();
            if (account != null) {
                account.setCustomer(customer); // ensure bidirectional link
                if (account.getIsActive() == null) {
                    account.setIsActive(true);
                }
            }

            return customerRepository.save(customer).toResponse();
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Username or phone number already exists");
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Customer account cannot be null");
        }
    }

    @Override
    public CustomerResponse updateCustomerById(Long customerId, CustomerRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + customerId));

        customer.setName(trim(request.getName()));
        customer.setAddress(trim(request.getAddress()));
        customer.setPhoneNumber(trim(request.getPhoneNumber()));

        CustomerAccount account = customer.getCustomerAccount();
        if (account == null) {
            account = new CustomerAccount();
            account.setIsActive(true);
        }

        account.setUsername(trim(request.getUsername()));
        account.setPassword(trim(request.getPassword()));
        account.setCustomer(customer);

        customer.setCustomerAccount(account);

        try {
            return customerRepository.save(customer).toResponse();
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Username or phone number already exists");
        }
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + customerId));

        if (customer.getCustomerAccount() != null) {
            customer.getCustomerAccount().setCustomer(null);
        }

        customerRepository.delete(customer);
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
