package com.nftverse.nftversejavasdk.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AppTokenMasterDto {
    private Long id;
    private Long marketplaceId;
    private String appToken;
}
