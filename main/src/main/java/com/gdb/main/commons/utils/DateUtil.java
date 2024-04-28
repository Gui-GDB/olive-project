package com.gdb.main.commons.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-11 21:00
 * @description: 对Date类型数据进行处理的工具类
 **/
public class DateUtil {
    /**
     * 工具类，构造方法私有化
     */
    private DateUtil(){}
    /**
     * 对指定的date对象进行格式化
     * @param date 传入一个日期 Date 对象
     * @return 返回指定格式的日期字符串
     */
    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 将日期转化为指定的格式
     * @param date 传入一个日期 date 对象
     * @param pattern 要求的日期格式
     * @return 返回指定格式的日期字符串
     */
    public static String formatDateTime(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
