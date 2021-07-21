package com.ahmad.myproject.controller;

import com.ahmad.myproject.model.MovieDto;
import com.ahmad.myproject.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.codec.binary.Base64;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    public ResponseEntity<Void> deleteById(@PathVariable("id") long id){
                movieService.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping("/add")
    public ResponseEntity<MovieDto> create (@RequestBody MovieDto movieDto)  {
        System.out.println(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.save(movieDto));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable("id")long id,@RequestBody MovieDto movieDto){
          MovieDto movieDto1= movieService.findById(id);
          movieDto1.setPrice(movieDto.getPrice());
          movieDto1.setTitle(movieDto.getTitle());
          movieDto1.setDescription(movieDto.getDescription());
          movieDto1.setImage(movieDto.getImage());
          movieDto1.setQuantity(movieDto.getQuantity());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(movieService.save(movieDto1));
    }

    @PostMapping("/upload")
    public ResponseEntity<MovieDto> uploadFile(@RequestParam("file") MultipartFile multipartFile,@ModelAttribute MovieDto movieDto){
        fileConvert(multipartFile, movieDto);
        return ResponseEntity.status(HttpStatus.OK).body(movieService.save(movieDto));
    }

    private void fileConvert(@RequestParam("file") MultipartFile multipartFile, @ModelAttribute MovieDto movieDto) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path path = Paths.get("src/main/resources/static/images/"+fileName);
        try{
            Files.copy(multipartFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            movieDto.setImage(multipartFile.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
