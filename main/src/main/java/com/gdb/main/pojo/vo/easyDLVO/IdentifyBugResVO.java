package com.gdb.main.pojo.vo.easyDLVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-08 01:13
 * @description: 返回给前端病虫的识别结果
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "虫害识别结果")
public class IdentifyBugResVO {
    @Schema(description = "base64编码的图片")
    private String base64;

    @Schema(description = "虫子的名字和可信度")
    private List<IdentifyBug> bugs;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdentifyBug {
        @Schema(description = "可信度")
        private Double score;

        @Schema(description = "虫名字")
        private String name;
    }
}


