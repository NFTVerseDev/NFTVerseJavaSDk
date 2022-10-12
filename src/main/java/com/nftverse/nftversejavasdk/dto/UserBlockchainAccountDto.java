package com.nftverse.nftversejavasdk.dto;

import com.nftverse.nftversejavasdk.enums.Blockchain;
import com.nftverse.nftversejavasdk.enums.Wallet;
import lombok.Data;

@Data
public class UserBlockchainAccountDto {
    private Long id;

    private Long userId;

    private String marketplaceAddress;

    private String address;

    private Boolean nftCapability;

    private Boolean ftCapability;

    private Blockchain blockchain;

    private Wallet wallet;

    private String externalUserId;
}
