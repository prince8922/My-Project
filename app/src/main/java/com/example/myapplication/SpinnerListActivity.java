package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SpinnerListActivity extends AppCompatActivity {

    Spinner spinner;
  //  String[] cityArray = {"Ahmedabad","Rajkot","Surat","Vadodara","Ahmedabad","Rajkot","Surat","Vadodara","Nadiad","Anand","Ahmedabad","Rajkot","Surat"};
        ArrayList<String> cityArray;

        GridView gridView;  // and in  listview same
        AutoCompleteTextView autoCompleteTextView;
        MultiAutoCompleteTextView multiAutoCompleteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_list);
        spinner = findViewById(R.id.spinner);

        cityArray=new ArrayList<>();
        cityArray.add("Ahmedabad"); //0
        cityArray.add("Surat");
        cityArray.add("Vadodara");  //2
        cityArray.add("Rajkot");
        cityArray.add("Nadiad");
        cityArray.add("Anand");
        cityArray.add("Mahesava");  //6
        cityArray.add("test");  //7

        cityArray.remove(7);
        cityArray.set(6,"Mahesana");


        ArrayAdapter adapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_checked,cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //cityArray[2];
                //cityArray[3];
                new CommonMethod(SpinnerListActivity.this,cityArray.get(i));
               // String s = adapterView.getItemAtPosition(i).toString();
                //new CommonMethod(SpinnerListActivity.this,s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gridView = findViewById(R.id.spinner_listview);
        ArrayAdapter listAdapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,cityArray);
        gridView.setAdapter(listAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //cityArray[2];
                //cityArray[3];
                //new CommonMethod(SpinnerListActivity.this,cityArray[i]);
                String s = adapterView.getItemAtPosition(i).toString();
                new CommonMethod(SpinnerListActivity.this, s);


            }
        });

        autoCompleteTextView=findViewById(R.id.spinner_autocomplete);
        ArrayAdapter autoAdapter=new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,cityArray);
        autoCompleteTextView.setThreshold(1); // char. have start index to 1;
        autoCompleteTextView.setAdapter(autoAdapter);

        multiAutoCompleteTextView =findViewById(R.id.spinner_multiautocomplete);
        ArrayAdapter multiAdapter=new ArrayAdapter(SpinnerListActivity.this,android.R.layout.simple_list_item_1,cityArray);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer()); //to give (, and space) after select city
        multiAutoCompleteTextView.setThreshold(1); // direct option aave city charcter
        multiAutoCompleteTextView.setAdapter(multiAdapter);
    }
}