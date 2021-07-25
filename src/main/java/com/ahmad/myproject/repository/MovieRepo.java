package com.ahmad.myproject.repository;

import com.ahmad.myproject.entity.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepo extends CrudRepository<Movie,Long> {

    Movie findByTitle (String title);

    List<Movie> findAll ();

}
