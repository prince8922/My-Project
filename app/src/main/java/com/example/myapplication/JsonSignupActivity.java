package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.SharedElementCallback;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.GetData.GetLoginData;
import com.example.myapplication.Utils.ApiClient;
import com.example.myapplication.Utils.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonSignupActivity extends AppCompatActivity { //login page che aa

    EditText email,password;
    Button submit;
    TextView createAccount;
    ApiInterface apiInterface;
    ProgressDialog pd;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_signup);

        sp=getSharedPreferences(ConstantUrl.PREF,MODE_PRIVATE);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        email = findViewById(R.id.json_signup_email);
        password = findViewById(R.id.json_signup_password);
        submit = findViewById(R.id.json_signup_submit);
        createAccount = findViewById(R.id.json_signup_create);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(JsonSignupActivity.this,jsonLoginActivity.class);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equalsIgnoreCase("")){
                    email.setError("Email or Contact Reqired");
                }
                else if(password.getText().toString().trim().equalsIgnoreCase("")){
                    password.setError("password Required");
                }
                else{
                        if(new ConnectionDetector(JsonSignupActivity.this).isConnectingToInternet())
                        {
                              //  new dologin().execute();
                            pd = new ProgressDialog(JsonSignupActivity.this);
                            pd.setMessage("Please Wait...");
                            pd.setCancelable(false);
                            pd.show();
                            retrofitLogin();
                        }
                        else{
                            new ConnectionDetector(JsonSignupActivity.this).connectiondetect();
                        }
                }
            }
        });

    }

    private void retrofitLogin() {
         Call<GetLoginData> call=apiInterface.getLoginData(email.getText().toString(),password.getText().toString());
         call.enqueue(new Callback<GetLoginData>() {
             @Override
             public void onResponse(Call<GetLoginData> call, Response<GetLoginData> response) {
                        pd.dismiss();
                        if (response.code()==200){
                            GetLoginData data=response.body();
                            if(data.status=true)
                            {
                                for(int i=0;i<data.userData.size();i++){
                                    String sId=data.userData.get(i).userid;
                                    String sType=data.userData.get(i).type;
                                    String sName=data.userData.get(i).name;
                                    String sEmail=data.userData.get(i).email;
                                    String sContact=data.userData.get(i).contact;
                                    String sGender=data.userData.get(i).gender;
                                    String sCity = data.userData.get(i).city;
                                    String sDob = data.userData.get(i).dob;

                                    sp.edit().putString(ConstantUrl.ID,sId).commit();
                                    sp.edit().putString(ConstantUrl.TYPE,sType).commit();
                                    sp.edit().putString(ConstantUrl.NAME,sName).commit();
                                    sp.edit().putString(ConstantUrl.EMAIL,sEmail).commit();
                                    sp.edit().putString(ConstantUrl.CONTACT,sContact).commit();
                                    sp.edit().putString(ConstantUrl.GENDER,sGender).commit();
                                    sp.edit().putString(ConstantUrl.CITY,sCity).commit();
                                    sp.edit().putString(ConstantUrl.DOB,sDob).commit();
                                }
                                new CommonMethod(JsonSignupActivity.this,data.message);
                                new CommonMethod(JsonSignupActivity.this,JsonProfileActivity.class);
                            }
                            else{
                                new CommonMethod(JsonSignupActivity.this,data.message);
                            }
                        }
                        else{
                            new CommonMethod(JsonSignupActivity.this,"Server Error Code : "+response.code());
                        }
             }

             @Override
             public void onFailure(Call<GetLoginData> call, Throwable t) {
                pd.dismiss();
                 new CommonMethod(JsonSignupActivity.this,t.getMessage());
             }
         });

    }

    private class dologin extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd= new ProgressDialog(JsonSignupActivity.this);
            pd.setMessage("Loding....");
            pd.setCancelable(false);        //no back whwn touch
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantUrl.LOGIN_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(s);
                if (jsonObject.getBoolean("Status")==true){
                    JSONArray array = jsonObject.getJSONArray("UserData");
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        String sId=object.getString("id");
                        String sType=object.getString("type");
                        String sName=object.getString("name");
                        String sEmail=object.getString("email");
                        String sContact=object.getString("contact");
                        String sGender=object.getString("gender");
                        String sCity=object.getString("city");
                        String sDob=object.getString("dob");


                        sp.edit().putString(ConstantUrl.ID,sId).commit();
                        sp.edit().putString(ConstantUrl.TYPE,sType).commit();
                        sp.edit().putString(ConstantUrl.NAME,sName).commit();
                        sp.edit().putString(ConstantUrl.EMAIL,sEmail).commit();
                        sp.edit().putString(ConstantUrl.CONTACT,sContact).commit();
                        sp.edit().putString(ConstantUrl.GENDER,sGender).commit();
                        sp.edit().putString(ConstantUrl.CITY,sCity).commit();
                        sp.edit().putString(ConstantUrl.DOB,sDob).commit();
                    }
                    new CommonMethod(JsonSignupActivity.this,jsonObject.getString("Message"));
                    new CommonMethod(JsonSignupActivity.this,JsonProfileActivity.class);
                }
                else{
                    new CommonMethod(JsonSignupActivity.this,jsonObject.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new CommonMethod(JsonSignupActivity.this,e.getMessage());
            }
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }

}