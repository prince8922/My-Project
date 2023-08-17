package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomListViewSecondActivity extends AppCompatActivity {

    String[] technologyArray = {"Android", "Java", "Php", "IOS"};
    String[] priceArray = {"1000", "400", "500", "100"};
    String[] descriptionArray = {
            "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
            "Java is a widely used object-oriented programming language and software platform that runs on billions of devices, including notebook computers, mobile devices, gaming consoles, medical devices and many others. The rules and syntax of Java are based on the C and C++ languages.",
            "PHP (recursive acronym for PHP: Hypertext Preprocessor ) is a widely-used open source general-purpose scripting language that is especially suited for web development and can be embedded into HTML.",
            "Apple (AAPL) iOS is the operating system for iPhone, iPad, and other Apple mobile devices. Based on Mac OS, the operating system which runs Apple's line of Mac desktop and laptop computers, Apple iOS is designed for easy, seamless networking between a range of Apple products."};
    int[] imageArray={R.drawable.prince,R.drawable.img,R.drawable.clock,R.drawable.prince};

    ArrayList<CustomList> arrayList;
    ListView listview ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_second);

        listview =findViewById(R.id.custom_listview_second);

        arrayList=new ArrayList<>();
        for(int i=0;i<technologyArray.length;i++)
        {
            CustomList list=new CustomList(technologyArray[i],priceArray[i],descriptionArray[i],imageArray[i]);
            arrayList.add(list);
              /*CustomList list = new CustomList();
            list.setImage(imageArray[i]);
            list.setName(technologyArray[i]);
            arrayList.add(list);*/
        }

        CustomSecondAdapter adapter=new CustomSecondAdapter(CustomListViewSecondActivity.this,arrayList);
        listview.setAdapter(adapter);




    }
}