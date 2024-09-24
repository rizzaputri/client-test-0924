package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.RewardRequest;
import com.enigma.maju_mundur_eshop.dto.response.RewardResponse;
import com.enigma.maju_mundur_eshop.entity.Customer;
import com.enigma.maju_mundur_eshop.entity.Reward;
import com.enigma.maju_mundur_eshop.repository.RewardRepository;
import com.enigma.maju_mundur_eshop.service.CustomerService;
import com.enigma.maju_mundur_eshop.service.RewardService;
import com.enigma.maju_mundur_eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;

    private final UserService userService;
    private final CustomerService customerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addReward(Reward reward) {
        rewardRepository.save(reward);
    }

    @Transactional(readOnly = true)
    @Override
    public Reward getById(String id) {
        return rewardRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<RewardResponse> getAllRewards() {
        Customer customer = customerService.getByUsername(userService.getByContext().getUsername());
        List<Reward> rewards = rewardRepository.findAllByCustomerId(customer.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND)
        );
        return rewards.stream().map(reward -> RewardResponse.builder()
                .id(reward.getId())
                .type(reward.getType())
                .status(reward.getStatus())
                .customerId(reward.getCustomer().getId())
                .build()
        ).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RewardResponse claimReward(RewardRequest request) {
        Reward reward = getById(request.getRewardId());
        reward.setStatus(true);
        return RewardResponse.builder()
                .id(reward.getId())
                .type(reward.getType())
                .status(reward.getStatus())
                .customerId(reward.getCustomer().getId())
                .build();
    }
}
