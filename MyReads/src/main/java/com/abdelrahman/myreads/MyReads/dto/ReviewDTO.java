package com.abdelrahman.myreads.MyReads.dto;

import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.model.Star;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.UserProfile;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    Long id;
    String body;
    Long parentId;
    Double rating;
    Book book;
    User user;
    String fullName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    Link userLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getBody() {
        return body;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @JsonIgnore
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Link getUserLink() {
        return userLink;
    }

    public void setUserLink(Link userLink) {
        this.userLink = userLink;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof ReviewDTO))
            return false;
        ReviewDTO other = (ReviewDTO) o;
        return this.getId() == other.getId() &&
                this.getBody() == other.getBody() &&
                this.getParentId() == other.getParentId() &&
                this.getBook() == other.getBook() &&
                this.getUser() == other.getUser() &&
                this.rating == other.rating &&
                this.createdAt == other.createdAt;
    }
    private Object[] getSigFields(){
        Object[] result = {
                id, parentId, body, book, user, rating, createdAt
        };
        return result;
    }
    @Override
    public int hashCode() {
        return Objects.hash(getSigFields());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
