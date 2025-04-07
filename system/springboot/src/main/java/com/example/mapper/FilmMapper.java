package com.example.mapper;

import com.example.entity.Film;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FilmMapper {
    List<Film> selectAll(Film film);

    void insert(Film film);

    void updateById(Film film);

    void deleteById(Integer id);

    Film selectById(Integer id);
}
