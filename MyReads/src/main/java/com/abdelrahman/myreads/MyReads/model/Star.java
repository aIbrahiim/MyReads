package com.abdelrahman.myreads.MyReads.model;

public enum Star {

    ONE("one"),
    TWO("two"),
    THREE("three"),
    FOUR("four"),
    FIVE("five");



    private final String star;


    Star(String star) {
        this.star = star;
    }

    public String getStar() {
        return star;
    }

}
