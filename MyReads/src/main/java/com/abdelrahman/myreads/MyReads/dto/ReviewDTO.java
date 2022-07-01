package com.abdelrahman.myreads.MyReads.dto;

import com.abdelrahman.myreads.MyReads.model.Star;
import com.abdelrahman.myreads.MyReads.payload.UserProfile;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    Long id;
    String body;
    Long parentId;
    Star starValue;
    String fullName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    Link selfLink;
}
