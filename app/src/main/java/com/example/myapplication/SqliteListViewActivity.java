package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SqliteListViewActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    ArrayList<String> arraylist;
    ArrayList<String> contactArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_list_view);

        db=openOrCreateDatabase("MYAPP",MODE_PRIVATE, null);
        String createTable="Create table if not exists record(STUDENTNAME VARCHAR(50),EMAILID VARCHAR(100),CONTACTNO INT(10))";
        db.execSQL(createTable);

        listView=findViewById(R.id.sqlite_listview);

        String selectQuery = "select * from record";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                arraylist = new ArrayList<>();
                contactArrayList=new ArrayList<>();
                while (cursor.moveToNext()){
                    String sStudentName = "Student Name : "+cursor.getString(0);
                    String sStudentEmail = "Email Id : "+cursor.getString(1);
                    String sStudentContact = "Contact No. : "+cursor.getString(2);

                    contactArrayList.add(cursor.getString(2));
                    arraylist.add(sStudentName+"\n"+sStudentEmail+"\n"+sStudentContact);
                }
                ArrayAdapter adapter = new ArrayAdapter(SqliteListViewActivity.this, android.R.layout.simple_list_item_1,arraylist);
                listView.setAdapter(adapter);

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String deleteQuery = "delete from record WHERE CONTACTNO='"+contactArrayList.get(i)+"'";
                        db.execSQL(deleteQuery);
                        new CommonMethod(SqliteListViewActivity.this,"Student Record "+contactArrayList.get(i)+" Deleted Successfully");
                        arraylist.remove(i);
                        contactArrayList.remove(i);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });

            }
        }
        else{
            new CommonMethod(SqliteListViewActivity.this,"Data Not Found");
        }
    }
}

