package com.example.myapplication.GetData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSignupData {

    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("Message")
    @Expose
    public String message;
}
