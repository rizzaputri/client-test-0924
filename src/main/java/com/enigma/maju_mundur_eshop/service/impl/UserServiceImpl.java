package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.entity.UserAccount;
import com.enigma.maju_mundur_eshop.repository.UserAccountRepository;
import com.enigma.maju_mundur_eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, ConstantMessage.USER_NOT_FOUND
                ));
    }

    @Transactional(readOnly = true)
    @Override
    public UserAccount getByUserId(String id) {
        return userAccountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ConstantMessage.USER_NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public UserAccount getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userAccountRepository.findByUsername(
                authentication.getPrincipal().toString())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                ConstantMessage.USER_NOT_FOUND));
    }
}
