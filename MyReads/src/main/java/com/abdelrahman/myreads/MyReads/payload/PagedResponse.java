package com.abdelrahman.myreads.MyReads.payload;

import com.abdelrahman.myreads.MyReads.dto.ReviewDTO;
import com.abdelrahman.myreads.MyReads.dto.SubReviewDTO;
import com.abdelrahman.myreads.MyReads.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
public class PagedResponse{
    private Map<ReviewDTO, List<ReviewDTO>> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    public PagedResponse( Map<ReviewDTO, List<ReviewDTO>> content, int page, int size, long totalElements, int totalPages, boolean last) {
        setContent(content);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
    public  Map<ReviewDTO, List<ReviewDTO>> getContent() {
        return content == null ? null : content;
    }

    public final void setContent( Map<ReviewDTO, List<ReviewDTO>> content) {
        if (content == null) {
            this.content = null;
        } else {
            this.content = content;
        }
    }
}
