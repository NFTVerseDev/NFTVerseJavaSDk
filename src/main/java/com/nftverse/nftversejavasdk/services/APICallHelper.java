package com.nftverse.nftversejavasdk.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nftverse.nftversejavasdk.dto.AppTokenMasterDto;
import com.nftverse.nftversejavasdk.dto.AssetsDto;
import com.nftverse.nftversejavasdk.dto.ExternalNftDto;
import com.nftverse.nftversejavasdk.dto.StatusDto;
import com.nftverse.nftversejavasdk.enums.AssetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;


@Service
public class APICallHelper {
    private final Environment env;

    private final static Logger logger = LoggerFactory.getLogger(APICallHelper.class);

    public APICallHelper(Environment env) {
        this.env = env;
    }

    private String getApiBaseUrl(){
        return env.getProperty("blockchainservice.url");
    }

    private String getAppToken() {
        return env.getProperty("blockchainservice.appToken");
    }

    public CompletableFuture<AssetsDto> uploadAsset(MultipartFile multipartFile,String type,String externalAssetId,AppTokenMasterDto appTokenMasterDto) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OkHttpClient client = new OkHttpClient();
            AssetsDto assetsDto=new AssetsDto();
            assetsDto.setExternalAssetId(externalAssetId);
            assetsDto.setType(AssetType.valueOf(type));

            Gson gson = new Gson();
            String json = gson.toJson(assetsDto);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url(this.getApiBaseUrl() + "/external/asset/upload")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("X-App-Token", this.getAppToken())
                    .build(); // defaults to GET
            Response response = client.newCall(request).execute();
            CompletableFuture<AssetsDto> assetsDtoCompletableFuture = mapper.readValue(response.body().byteStream(), new TypeReference<CompletableFuture<AssetsDto>>() {});
            return assetsDtoCompletableFuture;
        } catch (Exception e){
            //log exception;
            logger.error("APICallHelper: error in createUser: " + e.getMessage());
            return null;
        }
    }

    public CompletableFuture<StatusDto> mintAndTransferNft(ExternalNftDto externalNftDto, AppTokenMasterDto appTokenMasterDto) throws Exception {

        try {
            ObjectMapper mapper = new ObjectMapper();
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();
            String json = gson.toJson(externalNftDto);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url(this.getApiBaseUrl() + "/external/nft/mintAndTransfer")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("X-App-Token", this.getAppToken())
                    .build(); // defaults to GET
            Response response = client.newCall(request).execute();
            CompletableFuture<StatusDto> statusDtoCompletableFuture = mapper.readValue(response.body().byteStream(), new TypeReference<CompletableFuture<StatusDto>>() {});
            return statusDtoCompletableFuture;
        } catch (Exception e){
            //log exception;
            logger.error("APICallHelper: error in createUser: " + e.getMessage());
            return null;
        }
    }

}
