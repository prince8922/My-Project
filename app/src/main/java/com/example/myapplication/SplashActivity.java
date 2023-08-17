package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

        ImageView imageView;
        SharedPreferences sp;
        //Signup inter chnge Login accsept constant url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        imageView=findViewById(R.id.splash_imageview);
        sp = getSharedPreferences(ConstantUrl.PREF,MODE_PRIVATE);

        AlphaAnimation animation=new AlphaAnimation(0,1);
        animation.setDuration(2000);
       // animation.setRepeatCount(1);
        imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                   // new CommonMethod(SplashActivity.this, TabDemoActivity.class);// ek activity to biji activity ma java
                if(sp.getString(ConstantUrl.ID,"").equalsIgnoreCase("")){
                    new CommonMethod(SplashActivity.this, JsonSignupActivity.class);
                }
                else {
                    new  CommonMethod(SplashActivity.this,JsonProfileActivity.class);
                    finish(); // not return this page when we goes to back in mobail.
                }
            }
        },2500);


    }
}