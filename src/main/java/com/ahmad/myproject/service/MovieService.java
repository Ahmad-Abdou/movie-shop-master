package com.ahmad.myproject.service;

import com.ahmad.myproject.model.MovieDto;


import java.util.List;


public interface MovieService {

    MovieDto save (MovieDto bookdto);

    MovieDto findById(Long id);

    List<MovieDto> findAll();

    MovieDto findByTitle(String title);

    MovieDto update(MovieDto movieDto);

    void deleteById(Long id);


}
