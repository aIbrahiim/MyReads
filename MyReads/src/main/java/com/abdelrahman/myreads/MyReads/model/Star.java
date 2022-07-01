package com.abdelrahman.myreads.MyReads.model;


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

    public static Star from(String value) {
        for(Star item : values()) {
            if(item.value.equals(value)) {
                return item;
            }
        }
        return null;

    }
}



