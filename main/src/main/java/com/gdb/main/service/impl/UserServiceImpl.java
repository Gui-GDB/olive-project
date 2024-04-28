package com.gdb.main.service.impl;

import com.gdb.main.commons.constant.StatusCodeConstant;
import com.gdb.main.commons.exception.AccountLockedException;
import com.gdb.main.commons.exception.AccountNotFoundException;
import com.gdb.main.commons.exception.AccountPasswordErrorException;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.mapper.UserMapper;
import com.gdb.main.pojo.dto.UserDTO;
import com.gdb.main.pojo.entity.user.User;
import com.gdb.main.pojo.vo.uservo.UserMessage;
import com.gdb.main.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author GDB
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User login(UserDTO userDTO) {
        //获取用户名和密码
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(ResponseEnum.ACCOUNT_NOT_FOUND.getResultMsg());
        }

        //密码比对
        //将前端传过来的密码使用MD5进行加密，然后与数据库中的数据进行对比
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new AccountPasswordErrorException(ResponseEnum.PASSWORD_ERROR.getResultMsg());
        }
        if (user.getStatus().equals(StatusCodeConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(ResponseEnum.ACCOUNT_LOCKED.getResultMsg());
        }
        //3、返回实体对象
        return user;
    }

    @Override
    public void startOrStop(Integer id, Byte status) {
        User user = User.builder()
                    .id(id)
                    .status(status)
                    .build();
        userMapper.update(user);
    }

    @Override
    public UserMessage getUserDetail(String username) {
        User user = userMapper.getByUsername(username);
        UserMessage userMessage = new UserMessage();
        BeanUtils.copyProperties(user, userMessage);
        //将用户关联的主键转化为真实姓名
        userMessage.setCreateUser(userMapper.getName(user.getCreateUser()));
        userMessage.setUpdateUser(userMapper.getName(user.getUpdateUser()));
        return userMessage;
    }

    @Override
    public void uploadAvatar(Integer id, String newFileName) {
        User user = User.builder().id(id).avatar(newFileName).build();
        userMapper.update(user);
    }
}
