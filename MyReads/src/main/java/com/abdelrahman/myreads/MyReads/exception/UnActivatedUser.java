package com.abdelrahman.myreads.MyReads.exception;

import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnActivatedUser extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ApiResponse apiResponse;

    private String message;

    public UnActivatedUser(ApiResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public UnActivatedUser(String message){
        super(message);
        this.message = message;
    }

    public UnActivatedUser(String message, Throwable cause){
        super(message, cause);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
