package com.nftverse.nftversejavasdk.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nftverse.nftversejavasdk.dto.AppTokenMasterDto;
import com.nftverse.nftversejavasdk.dto.UserValidDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Value("${authservice_url}")
    private String authUrl;


    public UserValidDTO authenticate(String authToken)  throws Exception{
        try {
            ObjectMapper mapper = new ObjectMapper();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(this.authUrl + "/authorize")
                    .addHeader("content-type", "application/json")
                    .addHeader("X-Auth-Token", authToken)
                    .build(); // defaults to GET

            Response response = client.newCall(request).execute();

            UserValidDTO userValidDTO = mapper.readValue(response.body().byteStream(), UserValidDTO.class);
            return userValidDTO;
        } catch (Exception e){
            throw new Exception("Incorrect auth token");
        }
    }
    public AppTokenMasterDto authenticateAppToken(String appToken) throws Exception{
        try {
            ObjectMapper mapper = new ObjectMapper();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(this.authUrl + "/appToken/authorize")
                    .addHeader("content-type", "application/json")
                    .addHeader("X-App-Token", appToken)
                    .build(); // defaults to GET

            Response response = client.newCall(request).execute();

            AppTokenMasterDto appTokenMasterDto = mapper.readValue(response.body().byteStream(), AppTokenMasterDto.class);
            return appTokenMasterDto;
        } catch (Exception e){
            throw new Exception("Incorrect App Token");
        }
    }
}
