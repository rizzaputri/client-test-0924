package com.enigma.maju_mundur_eshop.controller;

import com.enigma.maju_mundur_eshop.constant.APIUrl;
import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.RewardRequest;
import com.enigma.maju_mundur_eshop.dto.response.CommonResponse;
import com.enigma.maju_mundur_eshop.dto.response.RewardResponse;
import com.enigma.maju_mundur_eshop.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.REWARD_API)
public class RewardController {
    private final RewardService rewardService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<RewardResponse>>> getAllRewards() {
        List<RewardResponse> rewards = rewardService.getAllRewards();
        CommonResponse<List<RewardResponse>> response = CommonResponse
                .<List<RewardResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + "all rewards")
                .data(rewards)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping
    public ResponseEntity<CommonResponse<RewardResponse>> claimReward(
            @RequestBody RewardRequest request
    ) {
        RewardResponse reward = rewardService.claimReward(request);
        CommonResponse<RewardResponse> response = CommonResponse
                .<RewardResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.CLAIM_REWARD)
                .data(reward)
                .build();
        return ResponseEntity.ok(response);
    }
}
