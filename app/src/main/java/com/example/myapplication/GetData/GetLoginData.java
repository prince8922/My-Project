package com.example.myapplication.GetData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLoginData {

    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("UserData")
    @Expose
    public List<GetUserData> userData = null;

    public class GetUserData {
        @SerializedName("id")
        @Expose
        public String userid;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("profile")
        @Expose
        public String profile;
    }
}



