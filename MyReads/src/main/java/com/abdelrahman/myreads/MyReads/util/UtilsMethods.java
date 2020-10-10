package com.abdelrahman.myreads.MyReads.util;

import com.abdelrahman.myreads.MyReads.model.Gender;

public  class UtilsMethods {
    public static Gender getEnumGender(String gender){
        switch (gender){
            case "male":
                return Gender.MALE;
            case "female":
                return Gender.FEMALE;
            default:
                return null;
        }
    }
}
