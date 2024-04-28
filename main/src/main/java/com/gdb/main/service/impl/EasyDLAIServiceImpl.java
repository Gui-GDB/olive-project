package com.gdb.main.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.gdb.main.commons.exception.ImageSizeOverException;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.commons.utils.FileUtil;
import com.gdb.main.commons.utils.ImageWithBase64;
import com.gdb.main.pojo.dto.EasyDLDTO;
import com.gdb.main.pojo.vo.easyDLVO.ClassificationResVo;
import com.gdb.main.pojo.vo.easyDLVO.IdentifyBugResVO;
import com.gdb.main.service.EasyDLAIService;
import jakarta.annotation.Resource;
import okhttp3.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.gdb.main.commons.utils.GetBaiduAccessTokenUtil.getAccessToken;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-24 13:54
 * @description: 图像分类相关的实现类
 **/

@Service
public class EasyDLAIServiceImpl implements EasyDLAIService {
    @Resource
    private Environment environment;
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public ClassificationResVo easyDLClassification(String imageUrl) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"url\":\"" + imageUrl + "\"}");
        Request request = new Request.Builder()
                .url(environment.getProperty("baidu.classification.url") + getAccessToken(environment.getProperty("baidu.API_Key"), environment.getProperty("baidu.Secret_Key")) + "&input_type=url")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();

        //将结果中的字符串转为Map集合，然后将返回结果提取出来
        JSONObject jsonObject = JSONObject.parseObject(response.body().string());
        List<EasyDLDTO> results = jsonObject.getList("results", EasyDLDTO.class);
        return new ClassificationResVo(results.get(0).getScore(), results.get(0).getName());
    }

    @Override
    public IdentifyBugResVO identifyBug(String base64OrURL, Integer choose) throws IOException {
        URL url;
        String base64;
        BufferedImage image;
        // todo 输出图片的地址,服务器上和本地是不同的，要注意区别,不知道为什么这里不能使用test路径
        String imagePath = environment.getProperty("linux.avatar") + FileUtil.getNewFileName(".jpg");
        //如果前端传递的参数是URL
        if (choose == 1) {
            url = new URL(base64OrURL);
            base64 = Base64.getEncoder().encodeToString(url.openStream().readAllBytes());
        } else {
            //将base64转化为图片保存
            ImageWithBase64.convertBase64ToImage(base64OrURL, imagePath, "jpg");
            base64 = base64OrURL;
            url = new URL(environment.getProperty("linux.server") + imagePath);
        }

        image = ImageIO.read(url);
        //图片太大
        if(base64.length() > 4* 1024 *1024) {
            throw new ImageSizeOverException(ResponseEnum.IMAGE_SIZE_OVER.getResultMsg());
        }
        //发送请求
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"image\":\"" + base64 + "\"}");
        Request request = new Request.Builder()
                .url(environment.getProperty("baidu.identify.url") + getAccessToken(environment.getProperty("baidu.API_Key"), environment.getProperty("baidu.Secret_Key")))
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();

        //将结果中的字符串转为Map集合，然后将返回结果提取出来
        JSONObject jsonObject = JSONObject.parseObject(response.body().string());
        List<EasyDLDTO> results = jsonObject.getList("results", EasyDLDTO.class);

        List<IdentifyBugResVO.IdentifyBug> bugList = new ArrayList<>();

        //对图片进行标注
        Graphics g = image.getGraphics();
        // 画笔颜色
        g.setColor(Color.RED);
        //设置字体样式
        g.setFont(new Font("Dialog", Font.PLAIN, 50));
        //处理多个标注的地方
        for (int i = 1; i <= results.size(); i++) {
            EasyDLDTO var = results.get(i - 1);
            // 矩形框 (原点x坐标，原点y坐标，矩形的长，矩形的宽)
            g.drawRect(var.getLocation().getLeft(), var.getLocation().getTop(), var.getLocation().getWidth(), var.getLocation().getHeight());
            g.drawString(String.valueOf(i), var.getLocation().getLeft() + var.getLocation().getWidth() / 2, var.getLocation().getTop() + var.getLocation().getHeight() / 2);
            IdentifyBugResVO.IdentifyBug bug = new IdentifyBugResVO.IdentifyBug(var.getScore(), var.getName());
            bugList.add(bug);
        }

        // todo 输出图片的地址,服务器上和本地是不同的，要注意区别,不知道为什么这里不能使用test路径
        FileOutputStream out = new FileOutputStream(imagePath);
        ImageIO.write(image, "jpg", out);
        out.close();
        //todo 这里只有在服务器的时候才能调用这个函数
        String strNetImageToBase64 = ImageWithBase64.netImageToBase64(environment.getProperty("linux.server") + imagePath);
        return IdentifyBugResVO.builder()
                .base64(strNetImageToBase64)
                .bugs(bugList)
                .build();
    }
}
