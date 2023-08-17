package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class CustomListViewActivity extends AppCompatActivity {

    ListView listView;
    String[] technologyArray={"Android","IOS","PHP","Python"};
    int[] imageArray ={R.drawable.prince,R.drawable.clock,R.drawable.prince,R.drawable.img};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view);

        listView=findViewById(R.id.custom_listview);

        CustomListAdapter adapter =new CustomListAdapter(CustomListViewActivity.this,technologyArray,imageArray);
        listView.setAdapter(adapter);

//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
            //new CommonMethod(CustomListViewActivity.this,technologyArray[i]);
//            }
//        });
//
    }
}