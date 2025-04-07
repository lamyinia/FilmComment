package com.example.service;

import com.example.entity.Film;
import com.example.mapper.CommentMapper;
import com.example.mapper.FilmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service  //注入到spring容器
public class FilmService {

    @Resource
    private FilmMapper filmMapper;
    @Resource
    private CommentMapper commentMapper;

    public void add(Film film) {
        filmMapper.insert(film);
    }
    public void update(Film film) {
        filmMapper.updateById(film);
    }

    public List<Film> selectAll(Film film){
        return filmMapper.selectAll(film);
    }
    public void deleteById(Integer id) {
        filmMapper.deleteById(id);
    }

    /*
        @param pageNum 当前的页码
        @param pageSize 每页的个数
        @return 分页的对象，包含数据和分页参数 total
     */
    public PageInfo<Film> selectPage(Film film, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Film> list = filmMapper.selectAll(film);
        for (Film f : list) {
            this.setScore(f);
        }
        return PageInfo.of(list);
    }

    public Film selectById(Integer id) {
        Film f = filmMapper.selectById(id);
        this.setScore(f);
        return f;
    }
    // 随机推荐电影
    public List<Film> selectRecommend(Integer id) {
        List <Film> films = this.selectAll(null);
        films = films.stream().filter(film -> !film.getId().equals(id)).collect(Collectors.toList());
        Collections.shuffle(films);
        List <Film> tar = films.stream().limit(3).collect(Collectors.toList());
        for (Film f : tar) {
            this.setScore(f);
        }
        return tar;
    }

    private void setScore(Film f) {
        int total = commentMapper.selectTotal(f.getId());
        f.setCommentCount(total);
        if (total == 0){
            f.setScore(0D);
        } else {
            Double sum = commentMapper.selectSum(f.getId());
            BigDecimal score = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(total), 1, RoundingMode.HALF_UP);
            f.setScore(score.doubleValue());
        }
    }

    public List<Film> selectRaking() {
        List<Film> films = filmMapper.selectAll(null);
        for (Film f : films) {
            this.setScore(f);
        }
        return films.stream().sorted((f1, f2) -> f2.getScore().compareTo(f1.getScore())).limit(5).collect(Collectors.toList());
    }
}
