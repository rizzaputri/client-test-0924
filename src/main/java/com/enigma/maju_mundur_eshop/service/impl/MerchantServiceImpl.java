package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.UpdateMerchantRequest;
import com.enigma.maju_mundur_eshop.dto.response.PrivateMerchantResponse;
import com.enigma.maju_mundur_eshop.dto.response.PublicMerchantResponse;
import com.enigma.maju_mundur_eshop.entity.Merchant;
import com.enigma.maju_mundur_eshop.entity.UserAccount;
import com.enigma.maju_mundur_eshop.repository.MerchantRepository;
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
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final UserService userService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMerchant(Merchant merchant) {
        merchantRepository.saveAndFlush(merchant);
    }

    @Transactional(readOnly = true)
    @Override
    public Merchant getById(String id) {
        return merchantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Merchant getByUsername(String username) {
        return merchantRepository.findByUserAccountUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateMerchantResponse getMerchantById() {
        UserAccount userAccount = userService.getByContext();
        Merchant merchant = getByUsername(userAccount.getUsername());
        return privateResponseBuilder(merchant);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PublicMerchantResponse> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.findAll();
        return merchants.stream().map(this::publicResponseBuilder).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PrivateMerchantResponse updateMerchant(UpdateMerchantRequest request) {
        validationUtil.validate(request);
        UserAccount userAccount = userService.getByContext();
        Merchant merchant = getByUsername(userAccount.getUsername());

        if (request.getShopName() != null) {
            merchant.setShopName(request.getShopName());
        }
        if (request.getEmail() != null) {
            merchant.setEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            merchant.setAddress(request.getAddress());
        }
        if (request.getSnsLink() != null) {
            merchant.setSnsLink(request.getSnsLink());
        }
        if (request.getUsername() != null) {
            userAccount.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            userAccount.setPassword(request.getPassword());
        }

        return privateResponseBuilder(merchant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMerchant(String id) {
        Merchant merchant = getById(id);
        merchantRepository.delete(merchant);
    }

    private PrivateMerchantResponse privateResponseBuilder(Merchant merchant) {
        return PrivateMerchantResponse.builder()
                .id(merchant.getId())
                .shopName(merchant.getShopName())
                .email(merchant.getEmail())
                .address(merchant.getAddress())
                .snsLink(merchant.getSnsLink())
                .userAccountId(merchant.getUserAccount().getId())
                .username(merchant.getUserAccount().getUsername())
                .build();
    }

    private PublicMerchantResponse publicResponseBuilder(Merchant merchant) {
        return PublicMerchantResponse.builder()
                .shopName(merchant.getShopName())
                .address(merchant.getAddress())
                .snsLink(merchant.getSnsLink())
                .build();
    }
}
