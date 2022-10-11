//package com.nftverse.nftversejavasdk.services;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nftverse.nftversejavasdk.dao.AssetsRepository;
//import com.nftverse.nftversejavasdk.dto.AssetsDto;
//import com.nftverse.nftversejavasdk.dto.FileUploadDto;
//import com.nftverse.nftversejavasdk.enums.AssetType;
//import okhttp3.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Objects;
//
//public class FileUploadService {
//    private final Environment env;
//    private final String pinataGatewayUrl = "https://gateway.pinata.cloud/ipfs/";
//    private AmazonS3 s3client;
//    @Value("${awsendpointUrl}")
//    private String endpointUrl;
//    @Value("${awsbucketName}")
//    private String bucketName;
//    @Value("${awsaccessKey}")
//    private String accessKey;
//    @Value("${awssecretKey}")
//    private String secretKey;
//    private final AssetsRepository assetsRepository;
//    Logger logger = LoggerFactory.getLogger(FileUploadService.class);
//
//    public FileUploadService(Environment env, AmazonS3 s3client, AssetsRepository assetsRepository) {
//        this.env = env;
//        this.s3client = s3client;
//        this.assetsRepository = assetsRepository;
//    }
//
//    public AssetsDto uploadFile(MultipartFile multipartFile, Long marketplaceId, String type, Long userId, String externalAssetId) throws Exception {
//        try {
//            File file = convertMultiPartToFile(multipartFile);
//            String fileNameFromFile = generateFileName(multipartFile);
//            String fileName = "assets/" + marketplaceId.toString() + "/web3/" + type +"/" + userId.toString() + "/" + fileNameFromFile;
//
//            HashMap map = this.uploadFileToPinata(fileNameFromFile, file);
//            FileUploadDto fileUploadDto = this.uploadFileTos3bucket(fileName, file);
//            file.delete();
//            if(map != null) {
//                fileUploadDto.setIpfsHash(map.get("IpfsHash").toString());
//                fileUploadDto.setPinSize(Long.valueOf(map.get("PinSize").toString()));
//
//                if(map.get("isDuplicate") != null && map.get("isDuplicate").equals(Boolean.TRUE)){
//                    AssetsDto assetsDomain = this.getMarketplaceAssetDetail(fileUploadDto.getIpfsHash());
//                    if(assetsDomain != null && !Objects.equals(assetsDomain.getMarketplaceId(), marketplaceId)){
//                        throw new Exception("Duplicate file from other marketplace");
//                    }
//                    if(assetsDomain != null){
//                        return assetsDomain;
//                    }
//                }
//            }
//            return this.saveUploadedAsset(fileUploadDto, marketplaceId, userId, type, externalAssetId);
//        } catch (Exception e){
//            throw new Exception(e.getCause());
//        }
//    }
//
//    private HashMap uploadFileToPinata(String fileName, File file) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("file",fileName,
//                            RequestBody.create(MediaType.parse("application/octet-stream"),
//                                    file))
//                    .build();
//            Request request = new Request.Builder()
//                    .url("https://api.pinata.cloud/pinning/pinFileToIPFS")
//                    .method("POST", body)
//                    .addHeader("Authorization", "Bearer "+ this.getPinataApiKeys())
//                    .addHeader("Content-Type", "application/json")
//                    .build();
//            Response response = client.newCall(request).execute();
////            String jsonStr = response.body().string();
////            JsonNode jsonNode = mapper.readTree(jsonStr);
////            PinataResponseDto responseDto = mapper.readValue(jsonStr, PinataResponseDto.class);
////            PinataResponseDto responseDto1 = mapper.treeToValue(jsonNode, PinataResponseDto.class);
////            ResponseBody res = response.body();
////            String result = res.string();
//            HashMap map = mapper.readValue(response.body().byteStream(), new TypeReference<HashMap>(){});
//            return map;
//        } catch (Exception e){
//            logger.error("Error while uploading to pinata", e);
//            return null;
//        }
//    }
//
//    private String getPinataApiKeys(){
//        return env.getProperty("pinata.secretAccessToken");
//    }
//
//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
//
//    private String generateFileName(MultipartFile multiPart) {
//        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_").replace("(", "-").replace(")", "-");
//    }
//
//    public FileUploadDto uploadFileTos3bucket(String fileName, File file) throws Exception {
//        FileUploadDto fileUploadDto = new FileUploadDto();
//        try {
//            String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
//            s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//            fileUploadDto.setFileUrl(fileUrl);
//            fileUploadDto.setFileName(fileName);
//            return fileUploadDto;
//        } catch (Exception e) {
//            logger.error("Error while uploading file to s3", e);
//            return fileUploadDto;
//        }
//    }
//    public AssetsDto getMarketplaceAssetDetail(String ipfsHash) throws Exception {
//        return this.assetsRepository.findByIpfsHash(ipfsHash);
//    }
//    public AssetsDto saveUploadedAsset(FileUploadDto fileUploadDto, Long marketplaceId, Long userId, String type, String externalAssetId) {
//        AssetsDto assetsDto = new AssetsDto();
//        assetsDto.setIpfsHash(fileUploadDto.getIpfsHash());
//        assetsDto.setMarketplaceId(marketplaceId);
//        assetsDto.setUserId(userId);
//        assetsDto.setMinted(Boolean.FALSE);
//        assetsDto.setPinSize(fileUploadDto.getPinSize());
//        assetsDto.setS3url(fileUploadDto.getFileName());
//        assetsDto.setType(AssetType.valueOf(type));
//        assetsDto.setExternalAssetId(externalAssetId);
//        return this.saveAssets(assetsDto);
//    }
//    public AssetsDto saveAssets(AssetsDto assetsDto) {
//        return this.assetsRepository.save(assetsDto);
//    }
//}
