package com.nftverse.nftversejavasdk.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExternalNftDto {
    String name;
    String description;
    Double price;
    String type;
    Long collectionId;
    String ipfsHash;
    String imageUrl;
    String videoUrl;
    String modelUrl;
    String mainExternalAssetId;
    List<String> supportingExternalAssetIds;
    Long mainAssetId;
    List<Long> supportingAssetIds;
    List<Properties> properties;
    Recipient recipient;
}
