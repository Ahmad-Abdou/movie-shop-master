package com.ahmad.myproject.repository;

import com.ahmad.myproject.entity.MovieOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieOrderRepository extends JpaRepository<MovieOrder,Long> {
}
