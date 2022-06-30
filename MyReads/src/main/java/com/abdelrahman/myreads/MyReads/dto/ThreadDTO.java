package com.abdelrahman.myreads.MyReads.dto;

import com.abdelrahman.myreads.MyReads.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadDTO {

    ReviewDTO review;
    List<ReviewDTO> subReviews;
}
