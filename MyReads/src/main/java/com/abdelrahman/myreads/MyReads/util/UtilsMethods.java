package com.abdelrahman.myreads.MyReads.util;

import com.abdelrahman.myreads.MyReads.exception.BadRequestException;
import com.abdelrahman.myreads.MyReads.model.Gender;
import com.abdelrahman.myreads.MyReads.model.Star;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import org.springframework.http.HttpStatus;

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
