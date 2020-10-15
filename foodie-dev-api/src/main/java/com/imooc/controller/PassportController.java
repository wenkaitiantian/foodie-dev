package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登陆", tags = {"用户注册登陆的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {
        //1、判断用户不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户不能为空");
        }
        //2、查找注册的用户名是否存在
        boolean IsExist = userService.queryUsernameIsExist(username);
        if (IsExist) {
            return IMOOCJSONResult.errorMsg("用户存在");
        }
        //请求成功，用户没有重复
//        return 200;
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户名注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult result(@RequestBody UserBO userBO  , HttpServletRequest request
            , HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();
        //0、判断用户密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //1、查询用户是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(username);
        if (usernameIsExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //2、密码长度不能少于6为
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码小于6位");
        }
        //3、判断密码是否一致
        if (!password.equals(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("判断密码是否一致");
        }

        //4、实现注册
        Users userResult = userService.createUser(userBO);
        userResult=setNullProperty(userResult);
        CookieUtils.setCookie(request,response,"user"
                , JsonUtils.objectToJson(userResult),true);
        return IMOOCJSONResult.ok();
    }
    @ApiOperation(value = "用户登陆", notes = "用户登陆", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO
            , HttpServletRequest request
            , HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //0、判断用户密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        //4、实现注册
        Users userResult = null;
        try {
            userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userResult==null){
            return IMOOCJSONResult.errorMsg("用户名或密码错误");
        }
        userResult=setNullProperty(userResult);
        CookieUtils.setCookie(request,response,"user"
                , JsonUtils.objectToJson(userResult),true);
        return IMOOCJSONResult.ok(userResult);
    }
    private Users setNullProperty(Users userResult ){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
