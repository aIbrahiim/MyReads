package com.abdelrahman.myreads.MyReads.util;

import com.abdelrahman.myreads.MyReads.controller.UserController;
import com.abdelrahman.myreads.MyReads.dto.ReviewDTO;
import com.abdelrahman.myreads.MyReads.exception.BadRequestException;
import com.abdelrahman.myreads.MyReads.model.Gender;
import com.abdelrahman.myreads.MyReads.model.Review;
import com.abdelrahman.myreads.MyReads.model.Star;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.Link;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public  class UtilsMethods {

    public static Gender getEnumGender(String gender){
        switch (gender){
            case "male":
                return Gender.MALE;
            case "female":
                return Gender.FEMALE;
            default:
                return null;
        }
    }

    public static Star getStarValue(String value){
        if(value == null)
            return null;

        switch (value){
            case "one":
                return Star.ONE;
            case "two":
                return Star.TWO;
            case "three":
                return Star.THREE;
            case "four":
                return Star.FOUR;
            case "FIVE":

            default:
                return null;
        }
    }


    public static String consutructJson(Map<ReviewDTO, List<ReviewDTO>> map){
        ObjectMapper mapper = new ObjectMapper();
        String clientFilterJson = "";
        try {
            clientFilterJson = mapper.writeValueAsString(map);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return clientFilterJson;
    }
    public static ReviewDTO toReviewDTO(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setFullName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        reviewDTO.setBody(review.getBody());
        reviewDTO.setBook(review.getBook());
        reviewDTO.setParentId(review.getParentId());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUserProfile(review.getUser().getId())).withSelfRel();
        reviewDTO.setUserLink(selfLink);
        return reviewDTO;
    }
    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(new ApiResponse(Boolean.FALSE, "Page number cannot be less than zero."));
        }

        if (size < 0) {
            throw new BadRequestException(new ApiResponse(Boolean.FALSE, "Size number cannot be less than zero."));
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException(new ApiResponse(Boolean.FALSE, "Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE));

        }
    }
}
