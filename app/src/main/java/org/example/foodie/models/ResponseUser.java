package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class ResponseUser {

    @SerializedName("token")
    String token;

    @SerializedName("user")
    User user;

    public ResponseUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
