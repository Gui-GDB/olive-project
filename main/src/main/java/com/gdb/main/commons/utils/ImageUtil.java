package com.gdb.main.commons.utils;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.png.PngProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.gdb.main.commons.exception.ImageTypeErrorException;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.pojo.vo.imageInfoVo.GPSInfoVO;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-18 20:11
 * @description: 获取图片的信息
 **/

@Slf4j
public class ImageUtil {
    /**
     * 获取图片的 GPS 信息
     *
     * @param file 获取信息的图片文件
     * @return 返回封装的 GPS 信息对象
     */
    public static GPSInfoVO getGPSInfo(File file) throws JpegProcessingException, IOException, PngProcessingException {
        Metadata metadata;
        //获取图片上传格式
        String name = file.getName();
        String suffix = name.substring(name.indexOf(".") + 1);
        if ("jpg".equalsIgnoreCase(suffix) || "jpeg".equalsIgnoreCase(suffix)) {
            metadata = JpegMetadataReader.readMetadata(file);
        } else if ("png".equalsIgnoreCase(suffix)) {
            metadata = PngMetadataReader.readMetadata(file);
        } else {
            throw new ImageTypeErrorException(ResponseEnum.IMAGE_TYPE_ERROR.getResultMsg());
        }
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        if (Objects.nonNull(gpsDirectory)) {
            GeoLocation geoLocation = gpsDirectory.getGeoLocation();
            GPSInfoVO gpsVo = GPSInfoVO.builder()
                    .longitude(geoLocation.getLongitude())
                    .latitude(geoLocation.getLatitude()).build();
            log.info(gpsVo.toString());
            return gpsVo;
        }
        return null;
    }

    /**
     * 生成缩略图
     *
     * @param oidPath   原文件路径
     * @param smallSize 文件压缩倍数
     */
    public static void imageGenerateSmall(String oidPath, double smallSize) {
        try {
            File bigFile = new File(oidPath);
            Image image = ImageIO.read(bigFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            int widthSmall = (int) (width / smallSize);
            int heightSmall = (int) (height / smallSize);
            BufferedImage buff = new BufferedImage(widthSmall, heightSmall, BufferedImage.TYPE_INT_RGB);
            Graphics g = buff.getGraphics();
            g.drawImage(image, 0, 0, widthSmall, heightSmall, null);
            g.dispose();
            ImageIO.write(buff, "jpg", new File(oidPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片压缩，图片大小超过指定大小，自动按比例压缩到指定大小以下
     *
     * @param fileSize 文件大小
     * @param oidPath  原文件路径
     * @param maxSize 文件的最大值
     */
    public static void imageCompress(long fileSize, String oidPath, int maxSize) {
        if (fileSize > maxSize) {
            int smallSize = (int) (fileSize % maxSize == 0 ? fileSize / maxSize : fileSize / maxSize + 1);
            double size = Math.ceil(Math.sqrt(smallSize));
            ImageUtil.imageGenerateSmall(oidPath, size);
        }
    }
}



