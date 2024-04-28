package com.gdb.main.commons.utils;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;
import java.io.IOException;


/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-07 18:20
 * @description: 通过API_KEY和SECRET_KEY获取百度云的access_token令牌
 **/

public class GetBaiduAccessTokenUtil {
    private GetBaiduAccessTokenUtil(){}
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static String getAccessToken(String API_KEY, String SECRET_KEY) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_id="+ API_KEY +"&client_secret="+ SECRET_KEY +"&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        //将结果中的字符串转为Map集合，然后将access_token提取出来
        JSONObject jsonObject = JSONObject.parseObject(response.body().string());
        return jsonObject.getString("access_token");
    }
}
