package com.example.usersapp.user;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    public Login login;
    @SerializedName("name")
    public Name name;
    @SerializedName("email")
    public String email;
    @SerializedName("dob")
    public Dob dob;
    @SerializedName("location")
    public Location location;
    @SerializedName("picture")
    public Picture picture;

    public static class Login {
        @SerializedName("uuid")
        public String uuid;
    }

    public static class Name {
        @SerializedName("first")
        public String first;
        @SerializedName("last")
        public String last;
    }

    public static class Dob {
        @SerializedName("age")
        public int age;
    }

    public static class Location {
        @SerializedName("city")
        public String city;
        @SerializedName("country")
        public String country;
    }

    public static class Picture {
        @SerializedName("large")
        public String large;
    }
}
