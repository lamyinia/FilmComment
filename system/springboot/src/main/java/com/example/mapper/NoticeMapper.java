package com.example.mapper;

import com.example.entity.Notice;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NoticeMapper {
    List<Notice> selectAll(Notice notice);

    void insert(Notice notice);

    void updateByid(Notice notice);

    void deleteByid(Integer id);
}
