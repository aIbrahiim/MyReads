package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
