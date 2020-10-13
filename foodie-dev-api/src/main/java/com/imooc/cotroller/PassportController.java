package com.imooc.cotroller;

import com.imooc.service.UserService;
import com.imooc.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {
        //1、判断用户不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户不能为空");
        }
        //2、查找注册的用户名是否存在
        boolean IsExist = userService.queryUsernameIsExist(username);
        if (IsExist){
            return IMOOCJSONResult.errorMsg("用户存在");
        }
        //请求成功，用户没有重复
//        return 200;
        return IMOOCJSONResult.ok();
    }
}
