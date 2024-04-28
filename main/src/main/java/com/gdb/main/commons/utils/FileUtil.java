package com.gdb.main.commons.utils;

import com.gdb.main.commons.exception.ImageNotSuffixException;
import com.gdb.main.commons.responseResult.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Mr.Gui
 */
@Slf4j
@Configuration
public class FileUtil {
    /**
     * 返回文件后缀
     * @param file 上传的文件对象
     * @return 返回文件的后缀名（注意返回的文件名是带 . 的）
     */
    public static String getFileSuffix(MultipartFile file) {
        //获取原文件名
        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            throw new ImageNotSuffixException(ResponseEnum.IMAGE_NOT_SUFFIX.getResultMsg());
        }
        int index = fileName.indexOf(".");
        return fileName.substring(index);
    }

    /**
     * 保存图片
     * @param mFile    上传的图片文件对象
     * @param filePath 服务器真实保存图片的文件对象的绝对路径
     */
    public static void saveFile(MultipartFile mFile , String filePath) {
        File file = new File(filePath);
        //查看文件夹是否存在，不存在则创建
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        log.info(filePath);
        try {
            mFile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新文件名
     * @param suffix 文件后缀名
     * @return 返回新生成的文件名
     */
    public static String getNewFileName(String suffix) {
        String date = DateUtil.formatDateTime(new Date(), "yyyyMMdd");
        return date + UUID.randomUUID() + suffix;
    }
}