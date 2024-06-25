package com.example.usersapp.api;

import com.example.usersapp.user.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("results")
    private User[] results;

    public User[] getResults() {
        return results;
    }

    public void setResults(User[] results) {
        this.results = results;
    }
}
