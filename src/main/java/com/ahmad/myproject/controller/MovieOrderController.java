package com.ahmad.myproject.controller;

import com.ahmad.myproject.entity.Movie;
import com.ahmad.myproject.entity.MovieOrder;
import com.ahmad.myproject.entity.Order;
import com.ahmad.myproject.repository.MovieOrderRepository;
import com.ahmad.myproject.repository.MovieRepo;
import com.ahmad.myproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/buy")
@CrossOrigin("*")
public class MovieOrderController {

        private MovieRepo movieRepo;
        private OrderRepository orderRepository;
        private MovieOrderRepository movieOrderRepository;
    @Autowired
    public void setMovieRepo(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Autowired
    public void setMovieOrderRepository(MovieOrderRepository movieOrderRepository) {
        this.movieOrderRepository = movieOrderRepository;
    }

    @PostMapping
    public ResponseEntity<MovieOrder> buy (@RequestBody Movie movie)  {
        if(SignIn_Out.user.getEmail().equals(null) || SignIn_Out.user.getPassword().equals(null)){
            throw new IllegalStateException("You have to sign in first ");
        }
        MovieOrder movieOrder = new MovieOrder();
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPaymentAmount(movie.getPrice()*movie.getQuantity());
        order.setPaymentStatus("Done");
        order.setUser(SignIn_Out.SignedInUser(SignIn_Out.user));
        orderRepository.save(order);
        System.out.println(movie);
        movieRepo.save(movie);
        movieOrder.setMovie(movie);
        movieOrder.setOrder(order);
        movieOrder.setQuantity(movie.getQuantity());
        movieOrderRepository.save(movieOrder);
        return ResponseEntity.status(HttpStatus.OK).body(movieOrder);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<MovieOrder>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(movieOrderRepository.findAll());
    }

}
