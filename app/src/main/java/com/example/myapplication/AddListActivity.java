package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddListActivity extends AppCompatActivity {

    Button set;
    EditText editText;

    ListView listView;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        set=findViewById(R.id.add_list_set);
        editText=findViewById(R.id.add_text);
        listView=findViewById(R.id.add_listview);

        arrayList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(AddListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().trim().equalsIgnoreCase("")){
                    editText.setError("name required");
                }
                else{
                    arrayList.add(editText.getText().toString());
                   // notifyAll();
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                     /*ArrayAdapter adapter = new ArrayAdapter(AddListActivity.this, android.R.layout.simple_list_item_1,arrayList);
                    listView.setAdapter(adapter);*/



                }
            }
        });



    }
}