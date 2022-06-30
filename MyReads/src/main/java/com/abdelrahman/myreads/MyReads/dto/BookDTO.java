package com.abdelrahman.myreads.MyReads.dto;

import com.abdelrahman.myreads.MyReads.model.Genre;
import com.abdelrahman.myreads.MyReads.payload.PagedResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String isbn;
    private Integer numberOfPages;
    private Genre bookGenre;
    private Integer seriesPosition;
    private Double rating;
    private String coverUUID;
    private PagedResponse<ThreadDTO> reviews;
}
