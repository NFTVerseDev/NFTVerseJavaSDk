package com.nftverse.nftversejavasdk.services;

import com.nftverse.nftversejavasdk.dto.AppTokenMasterDto;
import com.nftverse.nftversejavasdk.dto.AssetsDto;
import com.nftverse.nftversejavasdk.dto.ExternalNftDto;
import com.nftverse.nftversejavasdk.dto.StatusDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
public class SdkService {
    private final APICallHelper apiCallHelper;



    public SdkService(APICallHelper apiCallHelper) {
        this.apiCallHelper = apiCallHelper;
    }

    public CompletableFuture<AssetsDto> uploadExternalAsset(MultipartFile multipartFile,String type,String externalAssetId,AppTokenMasterDto appTokenMasterDto) throws Exception {
        //here assetId is the external assetId
        return this.apiCallHelper.uploadAsset(multipartFile,type,externalAssetId,appTokenMasterDto);
    }

    public CompletableFuture<StatusDto> mintAndTransferNft(ExternalNftDto externalNftDto, AppTokenMasterDto appTokenMasterDto ) throws Exception {
        return this.apiCallHelper.mintAndTransferNft(externalNftDto,appTokenMasterDto);
    }
}
