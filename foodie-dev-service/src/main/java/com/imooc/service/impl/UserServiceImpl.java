package com.imooc.service.impl;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
    @Override
    public boolean queryUsernameIsExist(String username) {
        // Mybatis逆向工程会生成实例及实例对应的example（用于添加条件，相当于where后的部分）
        Example userExample=new Example(Users.class);
        Example.Criteria userCriteria=userExample.createCriteria();
          // 5.添加username字段等于value条件
        userCriteria.andEqualTo("username",username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result==null ? false : true;
    }
}
