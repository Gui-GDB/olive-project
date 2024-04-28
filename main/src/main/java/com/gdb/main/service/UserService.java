package com.gdb.main.service;

import com.gdb.main.pojo.dto.UserDTO;
import com.gdb.main.pojo.entity.user.User;
import com.gdb.main.pojo.vo.uservo.UserMessage;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-18 18:27
 * @description: 对用户相关的业务
 **/
public interface UserService {
     /**
      * 用户登录
      * @param userDTO 用户名、密码
      * @return 用户所有信息
      */
     User login(UserDTO userDTO);

     /**
      * 开启或关闭用户账号状态
      * @param id 用户id
      * @param status 用户账号状态
      */
     void startOrStop(Integer id, Byte status);

     /**
      * 根据用户名获取所有的用户信息
      * @param name 用户名
      * @return 返回前端展示的用户信息
      */
     UserMessage getUserDetail(String name);


     /**
      * 上传用户的头像
      * @param id 用户id
      */
     void uploadAvatar(Integer id, String newFileName);
}
