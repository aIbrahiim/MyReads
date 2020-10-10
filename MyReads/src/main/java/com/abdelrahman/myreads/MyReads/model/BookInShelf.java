package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookInShelf {

    @EmbeddedId
    BookShelfId id;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Setter
    @Getter
    private LocalDateTime addedAt;


    public Book getBook(){
        return id.getBook();
    }

    public Shelf getShelf(){
        return id.getShelf();
    }

    public User getUser(){
        return id.getUser();
    }

}
