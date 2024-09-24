package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.dto.request.UpdateCustomerRequest;
import com.enigma.maju_mundur_eshop.dto.response.PrivateCustomerResponse;
import com.enigma.maju_mundur_eshop.dto.response.PublicCustomerResponse;
import com.enigma.maju_mundur_eshop.entity.Customer;

import java.util.List;

public interface CustomerService {
    void createCustomer(Customer customer);
    Customer getById(String id);
    Customer getByUsername(String username);
    PrivateCustomerResponse getCustomerById();
    List<PublicCustomerResponse> getAllCustomers();
    PrivateCustomerResponse updateCustomer(UpdateCustomerRequest request);
    void deleteCustomer(String id);
}
