package com.nftverse.nftversejavasdk.dto;

import com.nftverse.nftversejavasdk.enums.AssetType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AssetsDto {
    private Long assetId;

    private String s3url;

    private String ipfsHash;

    private Long pinSize;

    private Long userId;

    private Long marketplaceId;

    private Boolean minted;

    private String status;

    private AssetType type;

    private String externalAssetId;


}
