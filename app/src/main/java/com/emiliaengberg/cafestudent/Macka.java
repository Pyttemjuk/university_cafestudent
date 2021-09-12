package com.emiliaengberg.cafestudent;

import androidx.annotation.NonNull;

public class Macka {
    private String mName;
    private String mDescription;

    //Default constructor
    public Macka() {
        mName = "";
        mDescription = "";
    }

    //Constructor that takes two parameters
    public Macka(String name, String description) {
        mName = name;
        mDescription = description;
    }

    //Method for returning field as string
    public String getName() {
        return mName;
    }

    //Method for setting field
    public void setName(String name) {
        mName = name;
    }

    //Method for returning field as string
    public String getDescription() {
        return mDescription;
    }

    //Method for setting field
    public void setDescription(String description) {
        mDescription = description;
    }

    //Override to string that returns name of the object
    @NonNull
    @Override
    public String toString() {
        return mName;
    }
}
