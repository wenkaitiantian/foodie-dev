package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuMapper stuMapper;
    Stu stu=new Stu();
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void saveStu() {
//        Stu stu=new Stu();
        stu.setAge(19);
        stu.setName("wen");
        stuMapper.insert(stu);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void updateStu(int id) {
        stu.setId(id);
        stu.setAge(20);
        stu.setName("wen");
        stuMapper.updateByPrimaryKey(stu);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);

    }
}
