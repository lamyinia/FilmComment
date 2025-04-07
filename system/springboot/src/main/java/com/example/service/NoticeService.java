package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  //注入到spring容器
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    public void add(Notice notice) {
        notice.setTime(DateUtil.now());  // 设置notice的时间
        noticeMapper.insert(notice);
    }
    public void update(Notice notice) {
        noticeMapper.updateByid(notice);
    }

    public List<Notice> selectAll(Notice notice){
        return noticeMapper.selectAll(notice);
    }
    public void deleteByid(Integer id) {
        noticeMapper.deleteByid(id);
    }

    /*
        @param pageNum 当前的页码
        @param pageSize 每页的个数
        @return 分页的对象，包含数据和分页参数 total
     */
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }


}
