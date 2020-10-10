package com.abdelrahman.myreads.MyReads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookShelfId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @ManyToOne
    @JoinColumn(name = "shelf_id", referencedColumnName = "id")
    private Shelf shelf;


    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;




}
