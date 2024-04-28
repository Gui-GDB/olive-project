package com.gdb.main.commons.utils;

import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;


/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-07 22:31
 * @description: 图片和Base64编码的转换
 **/
@Slf4j
public class ImageWithBase64 {

    /**
     * 网络图片转换Base64的方法
     * @param netImagePath 网络图片的URL
     */
    public static String netImageToBase64(String netImagePath) throws IOException {
        String strNetImageToBase64;
        // 创建URL
        URL url = new URL(netImagePath);
        // 创建链接
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        //直接将内容以流的形式获取
        InputStream is = conn.getInputStream();
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        strNetImageToBase64 = encoder.encodeToString(is.readAllBytes());
        // 关闭流
        is.close();
        return strNetImageToBase64;
    }

    /**
     * 将base64编码的字符串转化为图片
     * @param base64Code 编码字符串
     * @param imagePath 图片保存路径
     * @param suffix 图片保存的后缀名
     */
    public static void convertBase64ToImage(String base64Code, String imagePath, String suffix) {
        BufferedImage image;
        byte[] imageByte;
        try {
            imageByte = DatatypeConverter.parseBase64Binary(base64Code);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(new ByteArrayInputStream(imageByte));
            bis.close();
            FileOutputStream out = new FileOutputStream(imagePath);
            ImageIO.write(image, suffix, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    /**
     * 本地图片转换Base64的方法
     * @param imgPath
     */

/*    private static void ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));
    }*/
}
