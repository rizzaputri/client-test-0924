package com.enigma.maju_mundur_eshop.controller;

import com.enigma.maju_mundur_eshop.constant.APIUrl;
import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.UpdateMerchantRequest;
import com.enigma.maju_mundur_eshop.dto.response.CommonResponse;
import com.enigma.maju_mundur_eshop.dto.response.PrivateMerchantResponse;
import com.enigma.maju_mundur_eshop.dto.response.PublicMerchantResponse;
import com.enigma.maju_mundur_eshop.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.MERCHANT_API)
public class MerchantController {
    private final MerchantService merchantService;

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    @GetMapping(path = "/profile")
    public ResponseEntity<CommonResponse<PrivateMerchantResponse>> getMerchantById() {
        PrivateMerchantResponse merchant = merchantService.getMerchantById();
        CommonResponse<PrivateMerchantResponse> response = CommonResponse
                .<PrivateMerchantResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + merchant.getShopName())
                .data(merchant)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<PublicMerchantResponse>>> getAllMerchants() {
        List<PublicMerchantResponse> merchants = merchantService.getAllMerchants();
        CommonResponse<List<PublicMerchantResponse>> response = CommonResponse
                .<List<PublicMerchantResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + "all data")
                .data(merchants)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    @PutMapping
    public ResponseEntity<CommonResponse<PrivateMerchantResponse>> updateMerchant(
            @RequestBody UpdateMerchantRequest request
    ) {
        PrivateMerchantResponse updatedMerchant = merchantService.updateMerchant(request);
        CommonResponse<PrivateMerchantResponse> response = CommonResponse
                .<PrivateMerchantResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.UPDATE_SUCCESS + updatedMerchant.getShopName())
                .data(updatedMerchant)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>> deleteMerchant(@PathVariable String id) {
        merchantService.deleteMerchant(id);
        CommonResponse<String> response = CommonResponse
                .<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.DELETE_SUCCESS + "Merchant with id : " + id)
                .build();
        return ResponseEntity.ok(response);
    }
}
