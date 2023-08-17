package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonProfileActivity extends AppCompatActivity {

    EditText name,email,contact,password,dob;
    RadioButton male,female,transgender;
    RadioGroup gender;
    Spinner spinner;
    Button editProfile,submit, logout;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ArrayList<String> cityArray;
    Calendar calendar;

    String sGender, sCity;
    ApiInterface apiInterface;
    ProgressDialog pd;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_profile);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        sp = getSharedPreferences(ConstantUrl.PREF, MODE_PRIVATE);
        name = findViewById(R.id.json_profile_name);
        email = findViewById(R.id.json_profile_email);
        contact = findViewById(R.id.json_profile_contact);
        password = findViewById(R.id.json_profile_password);
        dob = findViewById(R.id.json_profile_dob);

        male = findViewById(R.id.json_profile_male);
        female = findViewById(R.id.json_profile_female);
        transgender = findViewById(R.id.json_profile_transgender);


        name.setText(sp.getString(ConstantUrl.NAME, ""));
        email.setText(sp.getString(ConstantUrl.EMAIL, ""));
        contact.setText(sp.getString(ConstantUrl.CONTACT, ""));
        dob.setText(sp.getString(ConstantUrl.DOB, ""));

        gender = findViewById(R.id.json_profile_gender);
        spinner = findViewById(R.id.json_profile_city);
        editProfile = findViewById(R.id.json_profile_edit);
        submit = findViewById(R.id.json_profile_submit);



        logout=findViewById(R.id.json_profile_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sp.edit().remove(ConstantUrl.ID).commit();
                sp.edit().clear().commit();  //login karelu hoy direct profile khule
                new CommonMethod(JsonProfileActivity.this,JsonSignupActivity.class);
            }
        });

        sGender=sp.getString(ConstantUrl.GENDER,"");
        if(sGender.equalsIgnoreCase("Male")){
            male.setChecked(true);
        }else if(sGender.equalsIgnoreCase("Female")){
            female.setChecked(true);
        }
        else if(sGender.equalsIgnoreCase("Transgender")){
            transgender.setChecked(true);
        }

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i); //R.id.home_male
                sGender = radioButton.getText().toString();
                new CommonMethod(JsonProfileActivity.this, sGender);
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

        ArrayAdapter adapter = new ArrayAdapter(JsonProfileActivity.this, android.R.layout.simple_list_item_checked, cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner.setAdapter(adapter);

        sCity =sp.getString(ConstantUrl.CITY,"");

        int selectedPosition= 0;
        for(int i=0;i<cityArray.size();i++){
                if(cityArray.get(i).equalsIgnoreCase(sCity)){
                    selectedPosition=i;
                    break;
                }
        }
        spinner.setSelection(selectedPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sCity = cityArray.get(i);
                new CommonMethod(JsonProfileActivity.this, sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(dateFormat.format(calendar.getTime()));

            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dateDialog = new DatePickerDialog(JsonProfileActivity.this, dateClick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //dateDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dateDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equalsIgnoreCase("")) {
                    name.setError("Name Required");
                } else if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().matches(emailPattern)) {
                    email.setError("Valid Email Id Required");
                } else if (contact.getText().toString().trim().equalsIgnoreCase("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("Password Required");
                } else if (dob.getText().toString().trim().equalsIgnoreCase("")) {
                    dob.setError("Date Of Birth Required");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    new CommonMethod(JsonProfileActivity.this, "Please Select Gender");
                } else {
//                       setEnableData(false);
//                        editProfile.setVisibility(View.VISIBLE);
//                        submit.setVisibility(View.GONE);
                            if(new ConnectionDetector(JsonProfileActivity.this).isConnectingToInternet()){
                                    //new updateData().execute();
                                    pd=new ProgressDialog(JsonProfileActivity.this);
                                pd.setMessage("Please Wait...");
                                    pd.setCancelable(false);
                                    retrofitUpdate();
                                pd.show();
                            }
                            else
                            {
                                new ConnectionDetector(JsonProfileActivity.this).connectiondetect();
                            }
                }
            }
        });

        setEnableData(false);
        editProfile.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableData(true);
                editProfile.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });
    }

    private void retrofitUpdate() {

        Call<GetSignupData> call = apiInterface.updateProfileData(
                sp.getString(ConstantUrl.ID,""),
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
                if (response.code() == 200) {
                    if (response.body().status.equals("True")) {
                        new CommonMethod(JsonProfileActivity.this, response.body().message);
                        setEnableData(false);
                        editProfile.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.GONE);

                        sp.edit().putString(ConstantUrl.NAME, name.getText().toString()).commit();
                        sp.edit().putString(ConstantUrl.EMAIL, email.getText().toString()).commit();
                        sp.edit().putString(ConstantUrl.CONTACT, contact.getText().toString()).commit();
                        sp.edit().putString(ConstantUrl.DOB, dob.getText().toString()).commit();
                        sp.edit().putString(ConstantUrl.GENDER, sGender).commit();
                        sp.edit().putString(ConstantUrl.CITY, sCity).commit();
                    } else {
                        new CommonMethod(JsonProfileActivity.this, response.body().message);
                    }
                } else {
                    new CommonMethod(JsonProfileActivity.this, "Server Error Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                Log.d("RESPONSE_ERROR",t.getMessage());
            }
        });
    }

    private void setEnableData(boolean b) {
        name.setEnabled(b);
        email.setEnabled(b);
        contact.setEnabled(b);
        password.setEnabled(b);
        male.setEnabled(b);
        female.setEnabled(b);
        transgender.setEnabled(b);

        spinner.setEnabled(b);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }

    private class updateData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(JsonProfileActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("id",sp.getString(ConstantUrl.ID,""));
            hashMap.put("name",name.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("gender",sGender);
            hashMap.put("city",sCity);
            hashMap.put("password",password.getText().toString());
            hashMap.put("dob",dob.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.UPDATE_PROFILE_URL,MakeServiceCall.POST,hashMap);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object=new JSONObject(s);
                if(object.getBoolean("Status")==true){
                    new CommonMethod(JsonProfileActivity.this,object.getString("Message"));
                    setEnableData(false);
                    editProfile.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);

                    sp.edit().putString(ConstantUrl.NAME,name.getText().toString()).commit();
                    sp.edit().putString(ConstantUrl.EMAIL,email.getText().toString()).commit();
                    sp.edit().putString(ConstantUrl.CONTACT,contact.getText().toString()).commit();
                    sp.edit().putString(ConstantUrl.GENDER,sGender).commit();
                    sp.edit().putString(ConstantUrl.CITY,sCity).commit();
                }
                else{
                        new CommonMethod(JsonProfileActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

