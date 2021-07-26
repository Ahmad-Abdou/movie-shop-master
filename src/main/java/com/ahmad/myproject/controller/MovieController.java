package com.ahmad.myproject.controller;

import com.ahmad.myproject.entity.Movie;
import com.ahmad.myproject.model.MovieDto;
import com.ahmad.myproject.repository.MovieRepo;
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
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/movie")
@CrossOrigin("*")
public class MovieController {

    MovieService movieService;
    MovieRepo movieRepo;

    @Autowired
    public void setMovieRepo(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Autowired
    public void setBookService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll (){
        return ResponseEntity.status(HttpStatus.OK).body( movieRepo.findAll().stream().filter(movie1 -> movie1.getImage() !=null).collect(Collectors.toList()));
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
    public ResponseEntity<Movie> uploadFile(@RequestParam("file") MultipartFile multipartFile,@ModelAttribute Movie movie){
        if(SignIn_Out.user == null){
            throw new IllegalStateException("You have to sign in first ");
        }
        else
        fileConvert(multipartFile, movie);
        return ResponseEntity.status(HttpStatus.OK).body(movieRepo.save(movie));
    }

    public static void fileConvert(@RequestParam("file") MultipartFile multipartFile, @ModelAttribute Movie movie) {
        if(SignIn_Out.user==null){
            throw new IllegalStateException("You have to sign in first ");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path path = Paths.get("src/main/resources/static/images/"+fileName);
        try{
            Files.copy(multipartFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            movie.setImage(multipartFile.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
