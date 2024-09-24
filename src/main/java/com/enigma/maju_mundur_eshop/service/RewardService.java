package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.dto.request.RewardRequest;
import com.enigma.maju_mundur_eshop.dto.response.RewardResponse;
import com.enigma.maju_mundur_eshop.entity.Reward;

import java.util.List;

public interface RewardService {
    void addReward(Reward reward);
    Reward getById(String id);
    List<RewardResponse> getAllRewards();
    RewardResponse claimReward(RewardRequest request);
}
