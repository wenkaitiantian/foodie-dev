package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import org.springframework.stereotype.Service;


public interface UserService {
    //判断用户是否存在
    public boolean queryUsernameIsExist(String username);
    //判断用户是否存在
    public Users  createUser(UserBO userBO);
    //检索用户名和密码是否匹配
    public Users queryUserForLogin(String username,String password);

}
