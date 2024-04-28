package com.gdb.main.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-08 14:16
 * @description:
 **/

@Data
@Schema(name = "虫害识别传递的参数模型", description = "虫害识别传递的参数模型")
public class IdentifyBugDTO {
    @Schema(description = "这个参数有两个取值，取值1，表示传递的参数是URL地址；取值2，表示传递的参数是图片进行base64编码的字符串", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer choose;

    @Schema(description = "这个参数根据choose的不同传递不同的参数", requiredMode = Schema.RequiredMode.REQUIRED)
    private String base64OrURL;
}
