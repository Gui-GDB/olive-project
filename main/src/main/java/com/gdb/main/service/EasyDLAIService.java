package com.gdb.main.service;

import com.gdb.main.pojo.vo.easyDLVO.ClassificationResVo;
import com.gdb.main.pojo.vo.easyDLVO.IdentifyBugResVO;

import java.io.IOException;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-24 13:41
 * @description: 百度智能云的 EasyDL 大模型相关的接口包装
 **/

public interface EasyDLAIService {
    /**
     *
     * @param imageUrl 识别图片的URL地址
     * @return 返回图像的识别结果
     */
    ClassificationResVo easyDLClassification(String imageUrl) throws IOException;

    /**
     *
     * @param base64OrURL 上传的图片为base64格式的或者网络地址
     * @param choose 1 表示上传的是base64，2 表示上传的是URL
     */
    IdentifyBugResVO identifyBug(String base64OrURL, Integer choose) throws IOException;
}
