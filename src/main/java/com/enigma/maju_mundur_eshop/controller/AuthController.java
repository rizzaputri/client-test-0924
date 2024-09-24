package com.enigma.maju_mundur_eshop.controller;

import com.enigma.maju_mundur_eshop.constant.APIUrl;
import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.LoginRequest;
import com.enigma.maju_mundur_eshop.dto.request.RegisterCustomerRequest;
import com.enigma.maju_mundur_eshop.dto.request.RegisterMerchantRequest;
import com.enigma.maju_mundur_eshop.dto.response.CommonResponse;
import com.enigma.maju_mundur_eshop.dto.response.LoginResponse;
import com.enigma.maju_mundur_eshop.dto.response.RegisterResponse;
import com.enigma.maju_mundur_eshop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.AUTH_API)
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register-customers")
    public ResponseEntity<CommonResponse<?>> registerCustomer(
            @RequestBody RegisterCustomerRequest request
    ) {
        RegisterResponse register = authService.registerCustomer(request);
        CommonResponse<RegisterResponse> response = CommonResponse
                .<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ConstantMessage.REGISTER_SUCCESS)
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/register-merchants")
    public ResponseEntity<CommonResponse<?>> registerMerchant(
            @RequestBody RegisterMerchantRequest request
    ) {
        RegisterResponse register = authService.registerMerchant(request);
        CommonResponse<RegisterResponse> response = CommonResponse
                .<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ConstantMessage.REGISTER_SUCCESS)
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(
            @RequestBody LoginRequest loginRequest
    ) {
        LoginResponse login = authService.login(loginRequest);
        CommonResponse<LoginResponse> response = CommonResponse
                .<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.LOGIN_SUCCESS)
                .data(login)
                .build();
        return ResponseEntity.ok(response);
    }
}
