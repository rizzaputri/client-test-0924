package com.enigma.maju_mundur_eshop.controller;

import com.enigma.maju_mundur_eshop.constant.APIUrl;
import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.NewProductRequest;
import com.enigma.maju_mundur_eshop.dto.request.UpdateProductRequest;
import com.enigma.maju_mundur_eshop.dto.response.CommonResponse;
import com.enigma.maju_mundur_eshop.dto.response.ProductResponse;
import com.enigma.maju_mundur_eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> createProduct(
            @RequestBody NewProductRequest request
    ) {
        ProductResponse product = productService.createProduct(request);
        CommonResponse<ProductResponse> commonResponse = CommonResponse
                .<ProductResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ConstantMessage.CREATE_SUCCESS + product.getProductName())
                .data(product)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getProductById(id);
        CommonResponse<ProductResponse> commonResponse = CommonResponse
                .<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + product.getProductName())
                .data(product)
                .build();
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        CommonResponse<List<ProductResponse>> commonResponse = CommonResponse
                .<List<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + "all data")
                .data(products)
                .build();
        return ResponseEntity.ok(commonResponse);
    }

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(
            @RequestBody UpdateProductRequest request
    ) {
        ProductResponse updatedProduct = productService.updateProduct(request);
        CommonResponse<ProductResponse> commonResponse = CommonResponse
                .<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.UPDATE_SUCCESS + updatedProduct.getProductName())
                .data(updatedProduct)
                .build();
        return ResponseEntity.ok(commonResponse);
    }

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        CommonResponse<String> commonResponse = CommonResponse
                .<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.DELETE_SUCCESS + "Product with id : " + id)
                .build();
        return ResponseEntity.ok(commonResponse);
    }
}
