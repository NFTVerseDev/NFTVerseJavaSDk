package com.nftverse.nftversejavasdk.dto;

import lombok.Data;

import java.util.List;

@Data
public class NFTMetadata {
    String name;
    String description;
    String type;
    Long collectionId;
    String ipfsHash;
    String imageUrl;
    String videoUrl;
    String modelUrl;
    List<String> supportingFiles;
    List<Properties> properties;
}
