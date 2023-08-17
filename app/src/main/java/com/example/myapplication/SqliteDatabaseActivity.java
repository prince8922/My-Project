package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SqliteDatabaseActivity extends AppCompatActivity {

    EditText name,email,contact;
    Button insert,show,customShow;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    SQLiteDatabase db;   //record tablename and MYAPP db che
    String sType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);

        db=openOrCreateDatabase("MYAPP",MODE_PRIVATE,null);
        String createTable ="Create table if not exists record(STUDENTNAME VARCHAR(50),EMAILID VARCHAR(100),CONTACTNO INT(10))";
        db.execSQL(createTable);

        name=findViewById(R.id.sqlite_database_name);
        email=findViewById(R.id.sqlite_database_email);
        contact=findViewById(R.id.sqlite_database_contact);

        insert=findViewById(R.id.sqlite_database_insert);

        Bundle bundle = getIntent().getExtras();
        sType = bundle.getString("type");
        if(sType.equalsIgnoreCase("add")){
            getSupportActionBar().setTitle("Add Data");
            insert.setText("Insert");
            contact.setEnabled(true);
        }
        else{
            getSupportActionBar().setTitle("Update Data");
            insert.setText("Update");
            name.setText(bundle.getString("NAME"));
            email.setText(bundle.getString("EMAIL"));
            contact.setText(bundle.getString("CONTACT"));

            contact.setEnabled(false);

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customShow=findViewById(R.id.sqlite_database_custom_list_show);
        customShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new CommonMethod(SqliteDatabaseActivity.this,SqliteCustomListViewActivity.class);
            }
        });


        show=findViewById(R.id.sqlite_database_show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SqliteDatabaseActivity.this,SqliteListViewActivity.class);
            }
        });




        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equalsIgnoreCase("")){  //space na gane (kadhva)-> trim
                    name.setError("Name Required");
                }
                else if(contact.getText().toString().trim().equalsIgnoreCase("")){
                    contact.setError("contact Required");
                }
                else if(contact.getText().toString().length()<10){
                    contact.setError("Valid Contact no.");
                }
                else if(email.getText().toString().trim().equalsIgnoreCase("")){
                    email.setError("Email Required");
                }
                else if (!email.getText().toString().matches(emailPattern)){
                    email.setError("Valid Email Required");
                }
                else {
                    if(sType.equalsIgnoreCase("Add")){
                        String selectQuery = "select * from record WHERE EMAILID='" + email.getText().toString() + "' OR CONTACTNO='" + contact.getText().toString() + "'";
                        Cursor cursor = db.rawQuery(selectQuery, null);

                        if (cursor.getCount() > 0) {
                            new CommonMethod(SqliteDatabaseActivity.this, "EmailId/Contact No. Already Registered");
                        } else {
                            //String insertQuery1 = "insert into record (`CONTACTNO`,`STUDENTNAME`,`EMAILID`) VALUES('"++"')";
                            String insertQuery = "insert into record VALUES('" + name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "')";
                            db.execSQL(insertQuery);
                            new CommonMethod(SqliteDatabaseActivity.this, "Insert Successfully");
                            clearData();

                        }
                    }
                    else{
                            String updateQuery="UPDATE record SET STUDENTNAME='"+name.getText().toString()+"',EMAILID='"+email.getText().toString()+"' WHERE CONTACTNO='"+contact.getText().toString()+"'";
                        db.execSQL(updateQuery);
                        new CommonMethod(SqliteDatabaseActivity.this, "Update Successfully");
                        clearData();
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearData(){
        name.setText("");
        email.setText("");
        contact.setText("");
        name.requestFocus();
        onBackPressed();
    }
}



