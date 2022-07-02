package com.abdelrahman.myreads.MyReads.payload;

import com.abdelrahman.myreads.MyReads.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    Long parentId;
    @NotBlank
    @Size(min = 5, message = "Review body must be minimum 5 characters")
    String body;
    Double rating;
}
