package com.nftverse.nftversejavasdk.dto;

import com.nftverse.nftversejavasdk.enums.Blockchain;
import lombok.Data;

@Data
public class Recipient {
    private Long userId;
    private String externalUserId;
    private String address;
    private Blockchain blockchain;
}
