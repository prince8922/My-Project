package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText Email,Password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      login=  findViewById(R.id.main_login);
       Email= findViewById(R.id.main_email);
       Password =findViewById(R.id.main_password);

        login.setBackgroundColor(getResources().getColor(R.color.teal_200));

       Email.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(charSequence.toString().trim().equals("") || Password.getText().toString().trim().equals(""))
            {
                login.setClickable(false);
                login.setBackgroundColor(getResources().getColor(R.color.teal_200));   // when no enter the email
            } else   {

           login.setClickable(true);
                login.setBackground(getResources().getDrawable(R.drawable.custom_button));
            }
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().equals("") || Email.getText().toString().trim().equals(""))
                {
                    login.setClickable(false);
                    login.setBackgroundColor(getResources().getColor(R.color.teal_200));  //when no enter the password
                } else   {

                    login.setClickable(true);
                    login.setBackground(getResources().getDrawable(R.drawable.custom_button));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

      login.setOnClickListener(new View.OnClickListener() {           // button ma thse
          @Override
          public void onClick(View view)
          {
              if(Email.getText().toString().trim().equals("")){
                    Email.setError("pls enter the email id");

              }
              else if(!Email.getText().toString().matches(emailPattern)){ // if no mathch(!)
                  Email.setError("set Valid Email Id Required");}
              else if(Password.getText().toString().trim().equals("")){

                  Password.setError("pls enter the password");

              }

              else{
                if(Email.getText().toString().trim().equals("princepatel123@gmail.com") && Password.getText().toString().trim().equals("me@123")) {
                    System.out.println("Login Successfully");  //info
                    Log.d("RESPONSE", "Login Successfully"); //debug
                    Log.e("RESPONSE", "Login Successfully"); //Error (red)
                    //Toast.makeText(MainActivity.this,"login successfully",Toast.LENGTH_SHORT).show(); //both
                    new CommonMethod(MainActivity.this, "Login Successfullly");
                    //Snackbar.make(view,"LOGIN SUCCESSFULLY",Snackbar.LENGTH_LONG).show();  // use print in screen easy as Toast
                    new CommonMethod(view, "login Successfully");

                    Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("EMAIL",Email.getText().toString());
                    bundle.putString("PASSWORD",Password.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);

                  //new   CommonMethod(MainActivity.this,HomeActivity.class);


                }
                else{
                    new CommonMethod(MainActivity.this, "Login UnSuccessfullly");
                    new CommonMethod(view, "login UnSuccessfully");

                }


              }

          }
      });

    }
}