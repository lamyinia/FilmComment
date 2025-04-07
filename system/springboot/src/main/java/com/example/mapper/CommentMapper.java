package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    List<Comment> selectAll(Comment comment);

    void insert(Comment comment);

    void updateById(Comment comment);

    void deleteById(Integer id);

    @Select("select count(*) from comment where film_id = #{filmId}")
    int selectTotal(Integer filmId);

    @Select("select sum(score) from comment where film_id = #{filmId}")
    Double selectSum(Integer id);
}
