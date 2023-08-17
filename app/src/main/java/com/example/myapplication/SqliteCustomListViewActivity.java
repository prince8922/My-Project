package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SqliteCustomListViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<SqliteCustomList> arrayList;
    SQLiteDatabase db;

    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_custom_list_view);

        getSupportActionBar().setTitle("My Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = openOrCreateDatabase("MYAPP", MODE_PRIVATE, null);
        String createTable = "create table if not exists record(STUDENTNAME VARCHAR(50),EMAILID VARCHAR(100),CONTACTNO INT(10))";
        db.execSQL(createTable);

        add = findViewById(R.id.sqlite_custom_list_view_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new CommonMethod(SqliteCustomListViewActivity.this,SqliteDatabaseActivity.class);
                Intent intent=new Intent(SqliteCustomListViewActivity.this,SqliteDatabaseActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type","add");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

            recyclerView=findViewById(R.id.sqlite_custom_recyclerview);
            //RecyclerView as a List
            recyclerView.setLayoutManager(new LinearLayoutManager(SqliteCustomListViewActivity.this));

            //recyclerView as a Grid//3 row
           // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));

        //Recyclerview As Grid //3 Column
     //   recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        //Recyclerview As Horizontal Scroll
       // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));


            recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
            if (id==android.R.id.home){
                onBackPressed();
            }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {

        String selectQuery = "select * from record";
        Cursor cursor = db.rawQuery(selectQuery,null);
        arrayList = new ArrayList<>();
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                while (cursor.moveToNext()){
                    SqliteCustomList list = new SqliteCustomList();
                    list.setName(cursor.getString(0));
                    list.setEmail(cursor.getString(1));
                    list.setContact(cursor.getString(2));
                    arrayList.add(list);
                }
                SqliteCustomAdapter adapter = new SqliteCustomAdapter(SqliteCustomListViewActivity.this,arrayList);
                recyclerView.setAdapter(adapter);
            }
        }
        else{
            new CommonMethod(SqliteCustomListViewActivity.this,"Data Not Found");
        }
    }
}
