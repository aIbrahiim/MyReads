package com.abdelrahman.myreads.MyReads.dto;

import com.abdelrahman.myreads.MyReads.model.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;

@Data
public class SubReviewDTO {
    Long id;
    String body;
    ReviewDTO parentId;
    Double rating;
    @JsonIgnore
    Book book;
    String fullName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    Link userLink;
}
