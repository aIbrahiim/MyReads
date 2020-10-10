package com.abdelrahman.myreads.MyReads.dto;

import com.abdelrahman.myreads.MyReads.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String isbn;
    private Integer numberOfPages;
    private Genre bookGenre;
    private Integer seriesPosition;
    private Double rating;
    private String coverUUID;
}
