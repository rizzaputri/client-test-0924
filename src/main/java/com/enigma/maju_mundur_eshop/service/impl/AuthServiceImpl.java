package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.UserRole;
import com.enigma.maju_mundur_eshop.dto.request.LoginRequest;
import com.enigma.maju_mundur_eshop.dto.request.RegisterCustomerRequest;
import com.enigma.maju_mundur_eshop.dto.request.RegisterMerchantRequest;
import com.enigma.maju_mundur_eshop.dto.response.LoginResponse;
import com.enigma.maju_mundur_eshop.dto.response.RegisterResponse;
import com.enigma.maju_mundur_eshop.entity.Customer;
import com.enigma.maju_mundur_eshop.entity.Merchant;
import com.enigma.maju_mundur_eshop.entity.Role;
import com.enigma.maju_mundur_eshop.entity.UserAccount;
import com.enigma.maju_mundur_eshop.repository.UserAccountRepository;
import com.enigma.maju_mundur_eshop.service.*;
import com.enigma.maju_mundur_eshop.utillity.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;

    private final RoleService roleService;
    private final MerchantService merchantService;
    private final CustomerService customerService;
    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ValidationUtil validationUtil;

    @Value("${maju_mundur_eshop.superadmin.username}")
    private String superAdminUsername;
    @Value("${maju_mundur_eshop.superadmin.password}")
    private String superAdminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserAccount> currentUserSuperAdmin = userAccountRepository
                .findByUsername(superAdminUsername);
        if (currentUserSuperAdmin.isPresent()) {
            return;
        }

        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        Role merchant = roleService.getOrSave(UserRole.ROLE_MERCHANT);
        Role superAdmin = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);
        UserAccount newSuperAdmin = UserAccount.builder()
                .username(superAdminUsername)
                .password(passwordEncoder.encode(superAdminPassword))
                .roles(List.of(customer, merchant, superAdmin))
                .isEnable(true)
                .build();
        userAccountRepository.save(newSuperAdmin);
    }

    @Override
    public RegisterResponse registerMerchant(RegisterMerchantRequest request) {
        validationUtil.validate(request);

        Role role = roleService.getOrSave(UserRole.ROLE_MERCHANT);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .roles(List.of(role))
                .isEnable(true)
                .build();
        userAccountRepository.saveAndFlush(userAccount);

        Merchant merchant = Merchant.builder()
                .shopName(request.getShopName())
                .email(request.getEmail())
                .userAccount(userAccount)
                .build();
        merchantService.createMerchant(merchant);

        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .roles(userAccount
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(RegisterCustomerRequest request) throws DataIntegrityViolationException {
        Role role = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .roles(List.of(role))
                .isEnable(true)
                .build();
        userAccountRepository.saveAndFlush(userAccount);

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .point(0)
                .userAccount(userAccount)
                .build();
        customerService.createCustomer(customer);

        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .roles(userAccount
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken(userAccount);

        return LoginResponse
                .builder()
                .token(token)
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();
    }
}
