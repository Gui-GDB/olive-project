package com.gdb.main.mapper;

import com.gdb.main.pojo.entity.user.User;
import org.apache.ibatis.annotations.Select;

/**
 * 用户相关的DB接口
 * @author Mr.Gui
 */
public interface UserMapper {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户的全部信息
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    /**
     * 根据 id 修改用户信息
     * @param user 需要修改的用户信息
     */
    void update(User user);

    /**
     * 根据主键id查询用户的真实姓名
     * @param id 用户主键
     * @return 用户的真实姓名
     */
    @Select("select name from user where id = #{id}")
    String getName(Integer id);
}
