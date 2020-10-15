package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.function.Supplier;

@Service
public class CarouselServiceImpl implements CarouselService {
    final static Logger logger= LoggerFactory.getLogger(CarouselServiceImpl.class);
    @Autowired
    private CarouselMapper carouselMapper;
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example=new Example(Carousel.class);
        example.orderBy("sort").desc();    //排序
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        List<Carousel> result = carouselMapper.selectByExample(example);
        logger.info(result.toString());
        return result;
    }
}
