package com.nftverse.nftversejavasdk.controller;

import com.nftverse.nftversejavasdk.dto.*;
import com.nftverse.nftversejavasdk.services.SdkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping
public class SdkController {
 private final SdkService sdkService;

 public SdkController(SdkService sdkService) {
  this.sdkService = sdkService;
 }

 @PostMapping(path = "/v1/testing/upload/asset")
 @ResponseBody
 public CompletableFuture<AssetsDto> uploadExternalAsset(@RequestPart(value = "file") MultipartFile multipartFile,@RequestParam(required = false) String type, @RequestParam(required = false) String externalAssetId, @CurrentlyLoggedUser AppTokenMasterDto appTokenMasterDto) throws Exception {
   return this.sdkService.uploadExternalAsset(multipartFile,type,externalAssetId,appTokenMasterDto);
 }
 @PostMapping(path = "/v1/testing/nft/mintAndTransfer")
 @ResponseBody
 public CompletableFuture<StatusDto> mintAndTransferNft(@RequestBody ExternalNftDto externalNftDto, @CurrentlyLoggedUser AppTokenMasterDto appTokenMasterDto) throws Exception {
   return this.sdkService.mintAndTransferNft(externalNftDto,appTokenMasterDto);
 }
}
