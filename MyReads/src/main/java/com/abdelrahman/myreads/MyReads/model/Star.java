package com.abdelrahman.myreads.MyReads.model;

import java.util.stream.Stream;

public enum Star {

    ONE("one"),
    TWO("two"),
    THREE("three"),
    FOUR("four"),
    FIVE("five");



    private String value;

    Star(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

 }


