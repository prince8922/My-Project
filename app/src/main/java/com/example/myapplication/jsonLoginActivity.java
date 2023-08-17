package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.controls.actions.CommandAction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.myapplication.GetData.GetSignupData;
import com.example.myapplication.Utils.ApiClient;
import com.example.myapplication.Utils.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale; //sign

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class jsonLoginActivity extends AppCompatActivity {  //signup che aa

    EditText name,email,contact,password,dob;
    RadioGroup gender;
    Spinner spinner;
    Button submit;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ArrayList<String> cityArray;
    Calendar calendar;

    String sGender,sCity;
    ProgressDialog pd;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_login);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        name = findViewById(R.id.json_login_name);
        email = findViewById(R.id.json_login_email);
        contact = findViewById(R.id.json_login_contact);
        password = findViewById(R.id.json_login_password);
        dob = findViewById(R.id.json_login_dob);
        gender = findViewById(R.id.json_login_gender);
        spinner = findViewById(R.id.json_login_city);
        submit = findViewById(R.id.json_login_submit);


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i); //R.id.home_male
                sGender = radioButton.getText().toString();
                new CommonMethod(jsonLoginActivity.this,sGender);
            }
        });

        cityArray = new ArrayList<>();
        cityArray.add("Ahmedabad");
        cityArray.add("Vadodara");
        cityArray.add("Rajkot");
        cityArray.add("Surat");
        cityArray.add("Nadiad");
        cityArray.add("Anand");
        cityArray.add("Mahesana");

        ArrayAdapter adapter = new ArrayAdapter(jsonLoginActivity.this, android.R.layout.simple_list_item_checked,cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sCity=cityArray.get(i);
                new CommonMethod(jsonLoginActivity.this,sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(dateFormat.format(calendar.getTime()));

            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dateDialog = new DatePickerDialog(jsonLoginActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //dateDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dateDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equalsIgnoreCase("")){
                    name.setError("Name required");

                }else if(email.getText().toString().trim().equalsIgnoreCase("")){
                    email.setError("Email Required");

                }
                else if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Valid Email id Required");

                }
                else if(contact.getText().toString().trim().equalsIgnoreCase("")){
                    contact.setError("Contact No. Required");

                }
                else if(contact.getText().toString().length()<10) {
                    contact.setError("Valid contact no. required");
                } else if(password.getText().toString().trim().equalsIgnoreCase("")){
                    password.setError("password Required");
                }
                else if(dob.getText().toString().trim().equalsIgnoreCase("")){
                        dob.setError("Date of Birth Required");
                }
                else if (gender.getCheckedRadioButtonId()==-1){
                    new CommonMethod(jsonLoginActivity.this,"please select Gender");
                }
                else{
                    //Async task mate
//                    if (new ConnectionDetector(jsonLoginActivity.this).isConnectingToInternet()) {
//                       // new CommonMethod(jsonLoginActivity.this, "Internet/Wifi Connected");
//                            new doSignup().execute();
//                    }
//                    else{
//                            new ConnectionDetector(jsonLoginActivity.this).connectiondetect();
//                    }
                    if(new ConnectionDetector(jsonLoginActivity.this).isConnectingToInternet()) {
                        pd=new ProgressDialog(jsonLoginActivity.this);
                        pd.setMessage("Please Wait..");
                        pd.setCancelable(false);
                        pd.show();
                        retrofitSignup();
                    }
                    else{
                        new ConnectionDetector(jsonLoginActivity.this).connectiondetect();
                    }
                }


            }
        });



    }

    private void retrofitSignup() {
        Call<GetSignupData> call = apiInterface.getSignupData(
                name.getText().toString(),
                email.getText().toString(),
                contact.getText().toString(),
                password.getText().toString(),
                dob.getText().toString(),
                sGender,
                sCity
        );

        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                    pd.dismiss();
                    if (response.code()==200){
                        if(response.body().status.equals("True")){
                            new CommonMethod(jsonLoginActivity.this,response.body().message);
                        }
                        else {
                            new CommonMethod(jsonLoginActivity.this, response.body().message);
                        }
                    }
                    else{
                            new CommonMethod(jsonLoginActivity.this,"Server Error Code..."+response.code());
                    }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

    private class doSignup extends AsyncTask<String,String,String> {

                ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(jsonLoginActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);  //e alert message jay nay te mate
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("name",name.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("dob",dob.getText().toString());
            hashMap.put("password",password.getText().toString());
            hashMap.put("gender",sGender);
            hashMap.put("city",sCity);
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.SIGNUP_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object= new JSONObject(s);
                if(object.getBoolean("Status")==true){
                    new CommonMethod(jsonLoginActivity.this,object.getString("Message"));
                }
                else{
                    new CommonMethod(jsonLoginActivity.this,object.getString("Message"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
                new CommonMethod(jsonLoginActivity.this,e.getMessage());
            }

        }
    }
}