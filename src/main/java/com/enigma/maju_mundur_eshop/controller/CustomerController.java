package com.enigma.maju_mundur_eshop.controller;

import com.enigma.maju_mundur_eshop.constant.APIUrl;
import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.UpdateCustomerRequest;
import com.enigma.maju_mundur_eshop.dto.response.CommonResponse;
import com.enigma.maju_mundur_eshop.dto.response.PrivateCustomerResponse;
import com.enigma.maju_mundur_eshop.dto.response.PublicCustomerResponse;
import com.enigma.maju_mundur_eshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping(path = "/profile")
    public ResponseEntity<CommonResponse<PrivateCustomerResponse>> getCustomerById() {
        PrivateCustomerResponse customer = customerService.getCustomerById();
        CommonResponse<PrivateCustomerResponse> response = CommonResponse
                .<PrivateCustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + customer.getName())
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<PublicCustomerResponse>>> getAllCustomers() {
        List<PublicCustomerResponse> customers = customerService.getAllCustomers();
        CommonResponse<List<PublicCustomerResponse>> response = CommonResponse
                .<List<PublicCustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + "all data")
                .data(customers)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping
    public ResponseEntity<CommonResponse<PrivateCustomerResponse>> updateCustomer(
            @RequestBody UpdateCustomerRequest request
    ) {
        PrivateCustomerResponse updatedCustomer = customerService.updateCustomer(request);
        CommonResponse<PrivateCustomerResponse> response = CommonResponse
                .<PrivateCustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.UPDATE_SUCCESS + updatedCustomer.getName())
                .data(updatedCustomer)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        CommonResponse<String> response = CommonResponse
                .<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.DELETE_SUCCESS + "Customer with id : " + id)
                .build();
        return ResponseEntity.ok(response);
    }
}
