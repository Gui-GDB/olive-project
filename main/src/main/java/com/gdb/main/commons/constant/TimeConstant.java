package com.gdb.main.commons.constant;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-14 22:21
 * @description: 用来定义整个程序中需要使用时间的常量
 **/

public class TimeConstant {
    /**
     * 每次获取一次完整的传感器数据的间隔时间
     */
    public static final Long SENSOR_SCHEDULE_TIME = 1800000L;
    /**
     * 同一次获取完整传感器数据的时间间隔
     */
    public static final Long GET_SENSOR_DATA_INTERVAL = 3*1000L;
}
