package com.gdb.main.service;

import com.gdb.main.pojo.vo.easyDLVO.IdentifyBugResVO;

import java.io.IOException;

/**
 * @author Mr.GDB
 */
public interface AiService {
    /**
     * 通过调用文心一言接口获取信息
     * @param messages 聊天内容
     * @return 返回文心一言最新回答的数据
     */
    String getMessage(String messages) throws IOException;
}
