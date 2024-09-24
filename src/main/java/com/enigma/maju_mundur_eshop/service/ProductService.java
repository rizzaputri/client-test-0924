package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.dto.request.NewProductRequest;
import com.enigma.maju_mundur_eshop.dto.request.UpdateProductRequest;
import com.enigma.maju_mundur_eshop.dto.response.ProductResponse;
import com.enigma.maju_mundur_eshop.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(NewProductRequest request);
    Product getById(String id);
    ProductResponse getProductById(String id);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(UpdateProductRequest request);
    void deleteProduct(String id);
}
