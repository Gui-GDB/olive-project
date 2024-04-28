package com.gdb.main.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-07 20:12
 * @description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyDLDTO {
    private IdentifyLocation location;
    private String name;
    private Double score;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdentifyLocation {
        private Integer height;
        private Integer left;
        private Integer top;
        private Integer width;
    }
}