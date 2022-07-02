package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByBookId(Long bookId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "delete from reviews r where r.parent_id =:parent_id", nativeQuery = true)
    void deleteReviews(@Param("parent_id") Long parenId);

}
