package com.gdb.main.pojo.entity.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-11 20:55
 * @description: 数据库传感器表映射的Java对象
 **/

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SensorDataDB {
    @Schema(description = "数据库中的主键")
    private Integer id;
    @JsonProperty("hPa")
    @Schema(description = "压强")
    private Double hPa;
    @Schema(description = "光照强度")
    private Double lux;
    @JsonProperty("CO")
    @Schema(description = "一氧化碳浓度")
    private Double CO;
    @JsonProperty("AQI")
    @Schema(description = "空气质量指数")
    private Double AQI;
    @Schema(description = "烟浓度")
    private Double smoke;
    @Schema(description = "湿度")
    private Double humidness;
    @Schema(description = "温度")
    private Double temperature;
    @Schema(description = "数据时间")
    private String date;
}
