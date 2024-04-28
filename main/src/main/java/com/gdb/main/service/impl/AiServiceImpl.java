package com.gdb.main.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.gdb.main.service.AiService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.gdb.main.commons.utils.GetBaiduAccessTokenUtil.getAccessToken;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-28 14:43
 * @description:
 **/

@Slf4j
@Service
public class AiServiceImpl implements AiService {
    @Resource
    private Environment environment;

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    @Override
    public String getMessage(String messages) throws IOException {
        //设置请求参数格式
        MediaType mediaType = MediaType.parse("application/json");
        //生产请求体的参数
        RequestBody body = RequestBody.create(mediaType, messages);
        //发送请求
        Request request = new Request.Builder()
                .url(environment.getProperty("baidu.chat.url") + getAccessToken(environment.getProperty("baidu.chat.API_Key"), environment.getProperty("baidu.chat.Secret_Key")))
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        //将结果中的字符串转为Map集合，然后将问答内容提取出来
        JSONObject jsonObject = JSONObject.parseObject(response.body().string());
        log.info(jsonObject.getString("result"));
        return jsonObject.getString("result");
    }
}

