package com.gdb.main.pojo.vo.easyDLVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-08 02:22
 * @description: 返回给前端的分类结果
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationResVo implements Serializable {
    @Schema(description = "可信度")
    private Double score;

    @Schema(description = "分类类别名")
    private String name;
}
