package com.gdb.main.pojo.vo.imageInfoVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-18 20:19
 * @description: 传递GPS信息的对象
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "图片的经纬度信息")
public class GPSInfoVO {
    @Schema(description = "图片的经度")
    private Double longitude;
    @Schema(description = "图片的维度")
    private Double latitude;
}
