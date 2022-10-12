package com.nftverse.nftversejavasdk.dto;

import com.nftverse.nftversejavasdk.enums.Blockchain;
import lombok.Data;

@Data
public class ExternalUserWalletSetupDto {
    String externalUserId; // this is the external userId
    Long nftVerseUserId; //nftverse's internal userId
    Blockchain blockchain;
    String mobile;
    String email;
}
