package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {  // Activity ma jay defultasctivity ma jay ne


    TextView Email,Password;

    RadioGroup gender;

    CheckBox androidCheck ,java,ios,flutter;
    Button show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // ahiya sudhi defult aavse
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // false to (<-) (home) gayb thay jse
        getSupportActionBar().setTitle("Home");  // action bar title

        Email= findViewById(R.id.home_email);
        Password= findViewById(R.id.home_password);

        Bundle bundle=getIntent().getExtras();
        Email.setText(bundle.getString("EMAIL"));
        Password.setText(bundle.getString("PASSWORD"));

        //male = findViewById(R.id.home_male);

        /*male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(HomeActivity.this,male.getText().toString());
            }
        });*/
            gender=findViewById(R.id.home_gender);
                    gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                             /*int id = gender.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);  or*/

                            RadioButton radioButton=findViewById(i); //dharoke R.id.home_male;
                            new CommonMethod(HomeActivity.this,radioButton.getText().toString());

                        }
                    });

                androidCheck=findViewById(R.id.home_android);
                java=findViewById(R.id.home_java);
               ios=findViewById(R.id.home_ios);
               flutter=findViewById(R.id.home_flutter);
               show=findViewById(R.id.home_show);

               show.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       StringBuilder sb=new StringBuilder();
                       if(androidCheck.isChecked())
                       {
                           /*Log.d("RESPONSE_TECH",androidCheck.getText().toString());
                    new CommonMethod(HomeActivity.this,androidCheck.getText().toString());*/
                        sb.append(androidCheck.getText().toString()+"\n");
                       }
                       if(java.isChecked())
                       {
                           /*Log.d("RESPONSE_TECH",androidCheck.getText().toString());
                    new CommonMethod(HomeActivity.this,java.getText().toString());*/
                           sb.append(java.getText().toString()+"\n");
                       }
                       if(ios.isChecked())
                       {
                           /*Log.d("RESPONSE_TECH",androidCheck.getText().toString());
                    new CommonMethod(HomeActivity.this,ios.getText().toString());*/
                           sb.append(ios.getText().toString()+"\n");
                       }
                       if(flutter.isChecked())
                       {
                           /*Log.d("RESPONSE_TECH",androidCheck.getText().toString());
                    new CommonMethod(HomeActivity.this,flutter.getText().toString());*/
                           sb.append(flutter.getText().toString()+"\n");
                       }
                            new CommonMethod(HomeActivity.this,sb.toString());
                   }
               });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id==android.R.id.home)
        {
                onBackPressed();  // pachu main page per java mate (<-)clik karvan
           //  alertmathod();
        }
        return super.onOptionsItemSelected(item);  //defult lakheklu
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        alertmathod();
    }

    private void alertmathod() {
        AlertDialog.Builder builder =new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Exit alert");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Are you sure you want to exit");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //System.exit(0);
                //finish();
                finishAffinity();
            }


        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

            }
        });

        builder.setNeutralButton("Rate us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new CommonMethod(HomeActivity.this,"Rate us");
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();


    }
}