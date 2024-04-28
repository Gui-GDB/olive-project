package com.gdb.main.controller;

import com.gdb.main.commons.exception.IdentifyBugChooseTypeException;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.commons.responseResult.ResponseResult;
import com.gdb.main.commons.utils.FileUtil;
import com.gdb.main.commons.utils.ImageUtil;
import com.gdb.main.pojo.dto.IdentifyBugDTO;
import com.gdb.main.pojo.vo.easyDLVO.ClassificationResVo;
import com.gdb.main.pojo.vo.easyDLVO.IdentifyBugResVO;
import com.gdb.main.service.EasyDLAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-24 14:44
 * @description: 百度智能云的 EasyDL 大模型相关的接口
 **/

@Slf4j
@Tag(name="百度智能云EasyDL相关API")
@RequestMapping("/easyDL")
@RestController
public class EasyDLAIController {
    @Resource
    private Environment environment;
    @Resource
    private EasyDLAIService easyDLAIService;

    @Operation(
            summary = "病虫害分类"
    )
    @PostMapping("/classification")
    public ResponseResult<ClassificationResVo> identifyBug2(MultipartFile file) throws IOException {
        //首先判断上传的文件是否为空
        if (file.isEmpty()) {
            return ResponseResult.error(ResponseEnum.UPLOAD_FAIL.getResultMsg());
        }
        //获取文件保存的路径
        String newFilePath = environment.getProperty("linux.avatar") + "classification" + FileUtil.getNewFileName(FileUtil.getFileSuffix(file));
        //将文件保存到服务器上
        FileUtil.saveFile(file, newFilePath);
        //将服务器上的图片大小进行压缩
        ImageUtil.imageCompress(file.getSize(), newFilePath, 1024 * 1024 * 4);
        //获取服务器上图片的URL地址
        String imageUrl = environment.getProperty("linux.server") + newFilePath;
        //调用服务接口获取识别结果
        log.info(newFilePath);
        ClassificationResVo classificationResVo = easyDLAIService.easyDLClassification(imageUrl);
        return ResponseResult.success(classificationResVo);
    }

    @Operation(
            summary = "病虫害识别"
    )
    @PostMapping("/identifyBug")
    public ResponseResult<IdentifyBugResVO> identifyBug(@RequestBody IdentifyBugDTO identifyBugDTO) throws IOException {
        //判断传递的参数是否争取
        Integer choose = identifyBugDTO.getChoose();
        if (choose != 1 && choose != 2)
            throw new IdentifyBugChooseTypeException(ResponseEnum.PARAM_CHOOSE_ERROR.getResultMsg());
        //调用Service层的方法进行处理,返回标记好的图片的base64编码结果
        IdentifyBugResVO identifyBugRes = easyDLAIService.identifyBug(identifyBugDTO.getBase64OrURL(), choose);
        //返回结果
        log.info("虫害识别成功");
        return ResponseResult.success(identifyBugRes);
    }
}
