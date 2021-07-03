package com.ahmad.myproject.controller;

import com.ahmad.myproject.model.MovieDto;
import com.ahmad.myproject.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin("*")
public class MovieController {


    MovieService movieService;

    @Autowired
    public void setBookService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll (){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findById (@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findById(id));
    }

    @GetMapping("/title")
    public ResponseEntity<MovieDto> findByTitle(@RequestParam("title") String title){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findByTitle(title));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
                movieService.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping
    public ResponseEntity<MovieDto> create (@RequestBody MovieDto movieDto)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.save(movieDto));
    }
    @PutMapping
    public ResponseEntity<MovieDto> update(@RequestBody MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(movieService.update(movieDto));
    }
    @PostMapping("/upload")
    public ResponseEntity<MovieDto> uploadFile( @RequestParam("file") MultipartFile multipartFile,@ModelAttribute MovieDto movieDto){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path path = Paths.get("src/main/resources/static/images/"+fileName);
        try{
            Files.copy(multipartFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

           movieDto.setImage(multipartFile.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.save(movieDto));
    }



}
