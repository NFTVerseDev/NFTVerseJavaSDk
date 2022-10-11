package com.nftverse.nftversejavasdk.dto;

import lombok.Data;


@Data
public class FileUploadDto {
    private String fileUrl;
    private String fileName;
    private String ipfsHash;
    private Long pinSize;
}

