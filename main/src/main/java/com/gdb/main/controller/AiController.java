package com.gdb.main.controller;

import com.gdb.main.commons.exception.IdentifyBugChooseTypeException;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.commons.responseResult.ResponseResult;
import com.gdb.main.pojo.dto.IdentifyBugDTO;
import com.gdb.main.pojo.vo.easyDLVO.IdentifyBugResVO;
import com.gdb.main.service.AiService;
import com.gdb.main.service.EasyDLAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-28 12:17
 * @description: 该controller主要用于文心一言
 **/

@Tag(name = "文心一言的 PAI")
@Slf4j
@RestController
@RequestMapping("/chat")
public class AiController {
    @Resource
    private AiService aiService;
    @Operation(
            summary = "获取文心一言回复信息",
            parameters = {
                    @Parameter(name = "messages", description = "所有的问答记录（这个是放在请求体中的参数，不能在knife4j文档中测试，请找其他的测试工具进行测试，例如postman）"),
            })
    @PostMapping("/yiyan")
    public ResponseResult<String> chat(@RequestBody String messages) throws IOException {
        log.info(messages);
        String message = aiService.getMessage(messages);
        return ResponseResult.success(message);
    }
}