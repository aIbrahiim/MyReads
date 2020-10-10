package com.abdelrahman.myreads.MyReads.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookToShelfRequest {

    Long bookId;
    Long shelfId;

}
