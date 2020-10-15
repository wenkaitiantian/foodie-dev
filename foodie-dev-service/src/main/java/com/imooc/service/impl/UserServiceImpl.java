package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.MD5Utils;
import com.imooc.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * Mybatis逆向工程会生成实例及实例对应的example（用于添加条件，相当于where后的部分）
 * xxxExample example = new xxxExample();
 * Criteria criteria = example.createCriteria();
 * 方法说明：
 * // 1.添加升序排列条件，DESC为降序
 * example.setOrderByClause("字段名ASC")
 * // 2.去除重复，boolean类型，true为选择不重复的记录
 * example.setDistinct(false)
 * // 3.添加字段xxx为null的条件
 * criteria.andXxxIsNull
 * // 4.添加字段xxx不为null的条件
 * criteria.andXxxIsNotNull
 * // 5.添加xxx字段等于value条件
 * criteria.andXxxEqualTo(value)
 * // 6.添加xxx字段不等于value条件
 * criteria.andXxxNotEqualTo(value)
 * // 7.添加xxx字段大于value条件
 * criteria.andXxxGreaterThan(value)
 * // 8.添加xxx字段大于等于value条件
 * criteria.andXxxGreaterThanOrEqualTo(value)
 * // 9.添加xxx字段小于value条件
 * criteria.andXxxLessThan(value)
 * // 10.添加xxx字段小于等于value条件
 * criteria.andXxxLessThanOrEqualTo(value)
 * // 11.添加xxx字段值在List
 * criteria.andXxxIn(List)
 * // 12.不添加xxx字段值在List
 * criteria.andXxxNotIn(List)
 * // 13.添加xxx字段值在之间
 * criteria.andXxxBetween(value1,value2)
 * // 14.添加xxx字段值不在之间
 * criteria.andXxxNotBetween(value1,value2)
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    public static  final String user_face = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        // Mybatis逆向工程会生成实例及实例对应的example（用于添加条件，相当于where后的部分）
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        // 5.添加username字段等于value条件
        userCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        String userId = sid.nextShort();
        Users users=new Users();

        users.setId(userId);
        users.setUsername(userBO.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //昵称
        users.setNickname(userBO.getUsername());
        //头像
        users.setFace(user_face);
        users.setBirthday(DateUtil.stringToDate("1992-08-26"));
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);
        return users;
    }

    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample=new Example(Users.class);
        Example.Criteria userCriteria=userExample.createCriteria();
        userCriteria.andEqualTo("username",username);
        userCriteria.andEqualTo("password",password);
        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }
}
