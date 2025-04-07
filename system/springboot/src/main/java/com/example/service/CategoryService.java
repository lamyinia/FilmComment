package com.example.service;

import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  //注入到spring容器
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public void add(Category category) {
        categoryMapper.insert(category);
    }
    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    public List<Category> selectAll(Category category){
        return categoryMapper.selectAll(category);
    }
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    /*
        @param pageNum 当前的页码
        @param pageSize 每页的个数
        @return 分页的对象，包含数据和分页参数 total
     */
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
}
