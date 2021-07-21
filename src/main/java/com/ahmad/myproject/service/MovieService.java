package com.ahmad.myproject.service;

import com.ahmad.myproject.entity.Movie;
import com.ahmad.myproject.model.MovieDto;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


public interface MovieService {

    MovieDto save (MovieDto bookdto);

    MovieDto findById(Long id);

    List<MovieDto> findAll();

    MovieDto findByTitle(String title);

    void deleteById(Long id);




}
