package com.ahmad.myproject.service;

import com.ahmad.myproject.entity.Movie;
import com.ahmad.myproject.model.MovieDto;
import com.ahmad.myproject.repository.MovieRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    MovieRepo movieRepo;
    ModelMapper modelMapper;

    @Autowired
    public void setBookRepo(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public MovieDto save(MovieDto bookdto) {
        Movie movie = movieRepo.save(modelMapper.map(bookdto, Movie.class));
        MovieDto dto = modelMapper.map(movie, MovieDto.class);
        return dto;
    }

    @Override
    public MovieDto findById(String id) {
        Optional<Movie> book = movieRepo.findById(id);
        if(book.isPresent()){
            MovieDto movieDto = modelMapper.map(book.get(), MovieDto.class);
            return movieDto;
        }
        throw new IllegalArgumentException("Id Not Found");
    }

    @Override
    public List<MovieDto> findAll() {
        List<Movie> movie = new ArrayList<>();
        movieRepo.findAll().iterator().forEachRemaining(movie::add);
        List<MovieDto> movieDtoList = movie.stream().map(movie1 -> modelMapper.map(movie1, MovieDto.class)).collect(Collectors.toList());
        return movieDtoList;
    }

    @Override
    public MovieDto findByTitle(String title) {
        Movie movie = movieRepo.findByTitle(title);
        MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
        return movieDto;
    }

    @Override
    public MovieDto update(MovieDto movieDto) {
        Movie movie = movieRepo.save(modelMapper.map(movieDto, Movie.class));
        MovieDto dto = modelMapper.map(movie, MovieDto.class);
        return dto;
    }

    @Override
    public void deleteById(String id) {
        movieRepo.deleteById(id);
    }


}
