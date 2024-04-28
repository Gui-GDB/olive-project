package com.gdb.main.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.gdb.main.commons.constant.JWTPayloadConstant;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.commons.responseResult.ResponseResult;
import com.gdb.main.commons.utils.DateUtil;
import com.gdb.main.commons.utils.FileUtil;
import com.gdb.main.commons.utils.JWTUtil;
import com.gdb.main.pojo.dto.UserDTO;
import com.gdb.main.pojo.entity.user.User;
import com.gdb.main.pojo.properties.JWTProperties;
import com.gdb.main.pojo.vo.uservo.UserLoginVO;
import com.gdb.main.pojo.vo.uservo.UserMessage;
import com.gdb.main.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-18 17:28
 * @description:
 **/

@Tag(name = "用户相关的 API")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JWTProperties jwtProperties;
    @Resource
    private Environment environment;

    /**
     * 登录
     */
    @Operation(
            summary = "用户登录"
    )
    @PostMapping("/login")
    public ResponseResult<UserLoginVO> login(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO);

        //登录成功后，生成jwt令牌
        Map<String, String> payload = new HashMap<>(10);
        //将用户的身份证号码写入token中
        payload.put(JWTPayloadConstant.ID_NUMBER, String.valueOf(user.getIdNumber()));
        //将用户名写入token中
        payload.put(JWTPayloadConstant.USERNAME, user.getUsername());
        //将名字写入token中
        payload.put(JWTPayloadConstant.NAME, user.getName());

        //生成 token
        String token = JWTUtil.getToken(jwtProperties.getTtlMillis(), jwtProperties.getSecretKey(), payload);

        //解析token
        DecodedJWT decodedJWT = JWTUtil.decodeToken(token, jwtProperties.getSecretKey());
        String date = DateUtil.formatDateTime(decodedJWT.getExpiresAt());

        //设置需要返回的数据
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .name(user.getName())
                .token(token)
                .tokenOverdueDate(date)
                .build();
        return ResponseResult.success(userLoginVO);
    }

    /**
     * 退出登录
     * todo 这里退出登录需要销毁该用户登录生产的token  ====>  这个暂时不用考虑，如果引入redis可以考虑使用
     * todo 需要考虑如何同时只能让一个账户在一个地方登录，也就是后生成的token覆盖掉之前生成的token从而达到一个用户同时只能在一处登录。 ====>  这个暂时不用考虑，如果引入redis可以考虑使用
     */
    @Operation(
            summary = "用户退出登录",
            parameters = {
                    @Parameter(name = "username", description = "用户退出登录")
            }
    )
    @GetMapping("/logout")
    public ResponseResult<Object> logout(@RequestParam("username") String username) {
        return ResponseResult.success();
    }

    /**
     * 根据 id 启用禁用用户账号
     * @param id 用户id
     * @param status 用户状态
     */
    @Operation(
            summary = "启用禁用用户账号",
            parameters = {
                    @Parameter(name = "id", description = "用户id"),
                    @Parameter(name = "status", description = "用户状态，1为启用，0为禁用")
            }
    )
    @GetMapping("/status/{status}")
    public ResponseResult<Object> status(@RequestParam("id") Integer id, @PathVariable Byte status) {
        userService.startOrStop(id, status);
        return ResponseResult.success();
    }

    /**
     * 根据用户名获取用户的所有信息
     * @param username 用户名
     * @return 用户的所有信息
     */
    @Operation(
            summary = "获取用户信息",
            parameters = {
                    @Parameter(name = "username", description = "用户名")
            }
    )
    @GetMapping("/userDetail")
    public ResponseResult<UserMessage> userDetail(@RequestParam("username") String username) {
        UserMessage userDetail = userService.getUserDetail(username);
        return ResponseResult.success(userDetail);
    }

    /**
     * 用户上传头像
     * @param id 用户主键 id
     * @param multipartFile 头像文件的文件对象
     * @return 返回是否上传成功
     */
    @Operation(
            summary = "上传用户头像",
            parameters = {
                    @Parameter(name = "id", description = "用户主键id"),
            }
    )
    @PostMapping("/uploadAvatar")
    public ResponseResult<String> uploadAvatar(@RequestParam("id") Integer id, @RequestParam("uploadAvatar") MultipartFile multipartFile, HttpServletRequest request)  {
        //首先判断上传的文件是否为空
        if (multipartFile.isEmpty()) {
            return ResponseResult.error(ResponseEnum.UPLOAD_FAIL.getResultMsg());
        }
        //获取文件的后缀
        String imageSuffix = FileUtil.getFileSuffix(multipartFile);
        //生成保存的文件名
        String newFileName = FileUtil.getNewFileName(imageSuffix);
        //获取文件保存的路径
        String newFilePath = environment.getProperty("linux.avatar") + newFileName;
        log.info(newFilePath);
        //将文件保存到服务器上
        FileUtil.saveFile(multipartFile, newFilePath);

        String url;
        //将文件名保存到数据库中
        if ("local".equals(environment.getProperty("spring.profiles.active"))) {
            url = environment.getProperty("linux.avatar") + newFileName;
        } else {
            // todo 这里暂时无法动态获取服务器的IP，因为使用的是docker容器部署，获取的是容器内部的IP ===> 暂时不用处理，但是如果部署的服务器发生变化需要修改配置文件
            url = environment.getProperty("server.ip") + ":" + request.getServerPort() + request.getContextPath() + "/" + environment.getProperty("linux.avatarPattern") + "/" + newFileName;
        }
        userService.uploadAvatar(id, url);
        return ResponseResult.success(url);
    }
}
