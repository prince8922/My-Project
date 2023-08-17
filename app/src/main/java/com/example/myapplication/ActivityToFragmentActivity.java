package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityToFragmentActivity extends AppCompatActivity {

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_fragment);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Activity To Activity
                /*Intent intent = new Intent(ActivityToFragmentActivity.this,TabDemoActivity.class);
                startActivity(intent);*/
                FragmentManager manager=getSupportFragmentManager(); //Activity to Fragment
                manager.beginTransaction().replace(R.id.activity_to_fragment_relative,new DemoFragment()).addToBackStack("").commit();
                //addToBackStack to get back slide

            }
        });
    }
}