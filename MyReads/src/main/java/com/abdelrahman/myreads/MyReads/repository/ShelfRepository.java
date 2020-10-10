package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.Shelf;
import com.abdelrahman.myreads.MyReads.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

    List<Shelf> findAllByUser(User user);

    @Transactional
    @Modifying
    @Query("update Shelf s set s.count = :cnt where s.id = :id")
    void updateCount(@Param("id") Long id, @Param("cnt") Integer cnt);

    Boolean existsByName(String name);

    Optional<Shelf> findByName(String name);
}
