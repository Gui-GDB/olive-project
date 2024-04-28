package com.gdb.main.controller;

import com.gdb.main.commons.responseResult.ResponseResult;
import com.gdb.main.pojo.entity.sensor.SensorDataDB;
import com.gdb.main.service.SensorDataDBService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-12 16:06
 * @description: 和传感器相关的控制器方法
 **/
@Tag(name = "传感器相关的 API", description = "获取传感器的数据")
@RequestMapping("/sensor/data")
@RestController
public class SensorDataController {
    @Resource
    private SensorDataDBService sensorDataDBService;

    /**
     * 获取指定传感器的最新数据
     * @param name 传感器的编号
     * @return 最新的数据
     */
    @Operation(
            summary = "获取指定传感器的最新数据",
            parameters = {
            @Parameter(name = "name", description = "传感器的编号"),
    })
    @GetMapping("/latest")
    public ResponseResult<SensorDataDB> getSensorDataLatest(String name) {
        return ResponseResult.success(sensorDataDBService.queryLatestData(name));
    }

    /**
     * 获取指定传感器的所有历史数据
     * @param name 传感器的编号
     * @return 所有数据
     */
    @Operation(
            summary = "获取指定时间范围内的传感器的数据",
            parameters = {
                    @Parameter(name = "name", description = "传感器的编号"),
                    @Parameter(name = "limit", description = "限制的时间范围，例如展示一天的数据就输入1，半天就输入0.5")
    })
    @GetMapping("/limit/")
    public ResponseResult<List<Map<String, SensorDataDB>>> getAllSensorDataForOne(@RequestParam("name") String name, @RequestParam("limit") Double limit) {
        return ResponseResult.success(sensorDataDBService.queryAllDataForOneSensor(name, limit));
    }
}
