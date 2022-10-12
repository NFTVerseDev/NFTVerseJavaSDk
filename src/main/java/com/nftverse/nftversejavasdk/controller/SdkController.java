package com.nftverse.nftversejavasdk.controller;

import com.amazonaws.services.inspector2.model.BadRequestException;
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
 @GetMapping(path = "/v1/testing/asset/{externalAssetId}")
 @ResponseBody
 public AssetsDto getAssetDetails(@PathVariable String externalAssetId, @CurrentlyLoggedUser AppTokenMasterDto appTokenMasterDto) throws Exception, BadRequestException {
  return this.sdkService.getAssetDetails(externalAssetId, appTokenMasterDto);
 }

 @PostMapping(path = "/v1/testing/user/wallet")
 @ResponseBody
 public CompletableFuture<UserBlockchainAccountDto> getOrSetupExternalWallet(@RequestBody ExternalUserWalletSetupDto externalUserWalletSetupDto,  @CurrentlyLoggedUser AppTokenMasterDto appTokenMasterDto) throws Exception {
  //external call validator
  return this.sdkService.getOrSetupExternalWallet(externalUserWalletSetupDto, appTokenMasterDto);
 }


}
