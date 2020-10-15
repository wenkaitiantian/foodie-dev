package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Category;

import java.util.List;

public interface CategoryMapperCustom {
    public List getSubCatList(Integer rootCatId);
}