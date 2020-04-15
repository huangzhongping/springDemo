package com.example.springbootdemo.product.provider;

import com.alibaba.fastjson.JSON;
import com.example.springbootdemo.product.dto.AuthorizeDTO;
import com.example.springbootdemo.product.dto.UserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorizeProvider {

    public String getAccessToken(AuthorizeDTO authorizeDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = JSON.toJSONString(authorizeDTO);
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String string = response.body().string();
            //            System.out.println(string);
            String result = null;
            if (string.contains("&")) {
                String[] accessTokenSplit = string.split("&");
                if (accessTokenSplit.length > 0 && accessTokenSplit[0].contains("=")) {
                    String[] strSplit = accessTokenSplit[0].split("=");
                    result = strSplit[1];
                }

            }
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String string = response.body().string();
            UserDTO userDTO = JSON.parseObject(string, UserDTO.class);

            System.out.println(userDTO.toString());
            return userDTO;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
