package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "select count from rating r where r.book_id = :book_id and r.star_value = :star_value",  nativeQuery = true)
    Long getRatesOfBookByStarValueAndBook(@Param("book_id") Long bookId, @Param("star_value") String starValue);

    @Transactional
    @Query(value = "update rating set count = (count+1) where book_id = :book_id and star_value = :star_value", nativeQuery = true)
    @Modifying
    void updateCount(@Param("book_id") Long bookId, @Param("star_value") String starValue);

    Boolean existsByBook(Book book);

    @Transactional
    @Query(value = "update rating set count = (count-1) where book_id = :book_id and star_value = :star_value", nativeQuery = true)
    @Modifying
    void updateCountByStar(@Param("book_id") Long bookId, @Param("star_value") String starValue);
}
