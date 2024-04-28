package com.gdb.main.controller;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngProcessingException;
import com.gdb.main.commons.exception.ImageNoGPSInfoException;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.commons.responseResult.ResponseResult;
import com.gdb.main.commons.utils.GPSTransToAMapUtil;
import com.gdb.main.commons.utils.ImageUtil;
import com.gdb.main.pojo.vo.imageInfoVo.GPSInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-18 20:36
 * @description: 和图像信息相关的接口
 **/

@RequestMapping("/image")
@Tag(name = "图像相关的API", description = "获取图像的相关信息")
@RestController
public class ImageInfoController {
    @Resource
    private Environment environment;

    @Operation(
            summary = "获取图像的经纬度信息"
    )
    @PostMapping("/GPSInfo")
    public ResponseResult<GPSInfoVO> getImageGPSInfo(MultipartFile multipartFile) throws IOException, JpegProcessingException, PngProcessingException {
        multipartFile.getInputStream();
        String path = environment.getProperty("linux.avatar")  + multipartFile.getOriginalFilename();
        File file = new File(path);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            // 底层也是通过io流写入文件file
            FileCopyUtils.copy(multipartFile.getBytes(), file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //获取图片的经纬度数据
        GPSInfoVO gpsInfoVO = ImageUtil.getGPSInfo(file);
        //如果返回的结果为 null 则说明给定的图片没有 GPS 信息
        if (gpsInfoVO == null) {
            throw new ImageNoGPSInfoException(ResponseEnum.IMAGE_NO_GPS_INFO.getResultMsg());
        }
        //将GPS经纬度 ---> 高德经纬度
        GPSTransToAMapUtil.AMap transform = GPSTransToAMapUtil.transform(gpsInfoVO.getLongitude(), gpsInfoVO.getLatitude());
        //封装数据返回
        gpsInfoVO.setLatitude(transform.latitude());
        gpsInfoVO.setLongitude(transform.longitude());
        return ResponseResult.success(gpsInfoVO);
    }
}
