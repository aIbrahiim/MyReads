package com.abdelrahman.myreads.MyReads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewDto extends RepresentationModel<UserReviewDto> {

    String firstName;
    String lastName;
}
