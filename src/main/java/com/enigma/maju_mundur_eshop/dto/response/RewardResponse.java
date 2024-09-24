package com.enigma.maju_mundur_eshop.dto.response;

import com.enigma.maju_mundur_eshop.constant.RewardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardResponse {
    private String id;
    private RewardType type;
    private Boolean status;
    private String customerId;
}
