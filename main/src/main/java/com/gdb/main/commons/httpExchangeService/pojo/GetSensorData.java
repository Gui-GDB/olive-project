package com.gdb.main.commons.httpExchangeService.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-05 20:29
 * @description: 获取二十四小时内的最新数据时返回的对象
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSensorData {
    private String did;
    @JsonProperty("updated_at")
    private Long updatedAt;
    private SensorData attr;

    /**
     * 封装传感器的数据对象
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SensorData {
        @JsonProperty("hPa")
        private Double hPa;
        private Double lux;
        @JsonProperty("CO")
        private Double CO;
        @JsonProperty("AQI")
        private Double AQI;
        private Double smoke;
        private Double humidness;
        private Double temperature;
    }
}
