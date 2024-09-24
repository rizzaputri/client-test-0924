package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.UpdateCustomerRequest;
import com.enigma.maju_mundur_eshop.dto.response.PrivateCustomerResponse;
import com.enigma.maju_mundur_eshop.dto.response.PublicCustomerResponse;
import com.enigma.maju_mundur_eshop.entity.Customer;
import com.enigma.maju_mundur_eshop.entity.Merchant;
import com.enigma.maju_mundur_eshop.entity.UserAccount;
import com.enigma.maju_mundur_eshop.repository.CustomerRepository;
import com.enigma.maju_mundur_eshop.service.CustomerService;
import com.enigma.maju_mundur_eshop.service.MerchantService;
import com.enigma.maju_mundur_eshop.service.UserService;
import com.enigma.maju_mundur_eshop.utillity.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final MerchantService merchantService;
    private final UserService userService;

    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCustomer(Customer customer) {
        customerRepository.saveAndFlush(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getByUsername(String username) {
        return customerRepository.findByUserAccountUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateCustomerResponse getCustomerById() {
        UserAccount userAccount = userService.getByContext();
        Customer customer = getByUsername(userAccount.getUsername());
        return privateResponseBuilder(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicCustomerResponse> getAllCustomers() {
        UserAccount userAccount = userService.getByContext();
        List<String> roles = userAccount.getAuthorities().stream()
                .map(Object::toString).toList();
        if (roles.contains("ROLE_SUPER_ADMIN")) {
            List<Customer> customers = customerRepository.findAll();
            return customers.stream().map(this::publicResponseBuilder).toList();
        } else if (roles.contains("ROLE_MERCHANT")) {
            Merchant merchant = merchantService.getByUsername(userAccount.getUsername());
            List<Customer> customers = customerRepository.findAllByMerchantId(merchant.getId());
            return customers.stream().map(this::publicResponseBuilder).toList();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ConstantMessage.USER_INVALID);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PrivateCustomerResponse updateCustomer(UpdateCustomerRequest request) {
        validationUtil.validate(request);
        UserAccount userAccount = userService.getByContext();
        Customer customer = getByUsername(userAccount.getUsername());

        if (request.getName() != null) {
            customer.setName(request.getName());
        }
        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }
        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getUsername() != null) {
            userAccount.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            userAccount.setPassword(request.getPassword());
        }

        return privateResponseBuilder(customer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCustomer(String id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
    }

    private PrivateCustomerResponse privateResponseBuilder(Customer customer) {
        return PrivateCustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .point(customer.getPoint())
                .userAccountId(customer.getUserAccount().getId())
                .username(customer.getUserAccount().getUsername())
                .build();
    }

    private PublicCustomerResponse publicResponseBuilder(Customer customer) {
        return PublicCustomerResponse.builder()
                .name(customer.getName())
                .point(customer.getPoint())
                .build();
    }
}
