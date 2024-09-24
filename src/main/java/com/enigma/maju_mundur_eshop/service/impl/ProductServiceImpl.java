package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.NewProductRequest;
import com.enigma.maju_mundur_eshop.dto.request.UpdateProductRequest;
import com.enigma.maju_mundur_eshop.dto.response.ProductResponse;
import com.enigma.maju_mundur_eshop.entity.Merchant;
import com.enigma.maju_mundur_eshop.entity.Product;
import com.enigma.maju_mundur_eshop.repository.ProductRepository;
import com.enigma.maju_mundur_eshop.service.MerchantService;
import com.enigma.maju_mundur_eshop.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MerchantService merchantService;
    private final UserService userService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse createProduct(NewProductRequest request) {
        validationUtil.validate(request);
        String username = userService.getByContext().getUsername();
        Merchant merchant = merchantService.getByUsername(username);
        Product product = Product.builder()
                .productName(request.getProductName())
                .unitPrice(request.getUnitPrice())
                .stock(request.getStock())
                .merchant(merchant)
                .build();
        productRepository.saveAndFlush(product);
        return responseBuilder(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(String id) {
        Product product = getById(id);
        return responseBuilder(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::responseBuilder).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse updateProduct(UpdateProductRequest request) {
        Product requestedProduct = getById(request.getId());
        String requestedId = requestedProduct.getMerchant().getId();

        Merchant merchant = merchantService.getByUsername(userService.getByContext().getUsername());
        String requestingId = merchant.getId();

        if (!requestingId.equals(requestedId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ConstantMessage.USER_INVALID);
        }

        if (request.getProductName() != null) {
            requestedProduct.setProductName(request.getProductName());
        }
        if (request.getUnitPrice() != null) {
            requestedProduct.setUnitPrice(request.getUnitPrice());
        }
        if (request.getStock() != null) {
            requestedProduct.setStock(request.getStock());
        }

        return responseBuilder(requestedProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProduct(String id) {
        Product currentProduct = getById(id);
        productRepository.delete(currentProduct);
    }

    private ProductResponse responseBuilder(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .unitPrice(product.getUnitPrice())
                .stock(product.getStock())
                .merchantId(product.getMerchant().getId())
                .build();
    }
}
