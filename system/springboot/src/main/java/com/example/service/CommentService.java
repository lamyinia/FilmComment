package com.example.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  //注入到spring容器
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    public void add(Comment comment) {
        comment.setTime(DateUtil.now());
        commentMapper.insert(comment);
    }
    public void update(Comment comment) {
        commentMapper.updateById(comment);
    }

    public List<Comment> selectAll(Comment comment){
        return commentMapper.selectAll(comment);
    }
    public void deleteByid(Integer id) {
        commentMapper.deleteById(id);
    }

    /**
        @param pageNum 当前的页码
        @param pageSize 每页的个数
        @return 分页的对象，包含数据和分页参数 total
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }
}
