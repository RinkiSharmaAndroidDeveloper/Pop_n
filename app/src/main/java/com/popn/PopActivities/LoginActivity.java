package com.popn.PopActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERID= "userID";
    public static final String Status_Code= "statusCode";

    EditText email,password;
     TextView done,back;
    String Email=null,Password=null;
    ProgressDialog progressDialog;
    Network_url network_url;
    SessionManager sessionManager;
    String statuscode = "2";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressDialog =new ProgressDialog(this);
        network_url =new Network_url();
        sessionManager= new SessionManager(this);
        email= (EditText)findViewById(R.id.email);
        password= (EditText)findViewById(R.id.password);
        back= (TextView)findViewById(R.id.back);
        done= (TextView)findViewById(R.id.done);
        done.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.done):
                Email =email.getText().toString();
                Password =password.getText().toString();
                if(Email!=null && Password!=null){
                    progressBar.setVisibility(View.VISIBLE);
                    Login();
                }else{
                    email.setError("Please enter email.");
                    password.setError("Please enter password.");
                }
                break;
            case (R.id.back):
               finish();
                break;
        }
    }
    public void Login() {
        RequestQueue queue = null;
        queue = Volley.newRequestQueue(this);
        String URL = network_url.Base_Url + network_url.SignIn + "email=" + Email + "&password=" + Password;
       // progressDialog.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, URL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {

                            String resposne_message = response.getString("message");
                            String resposne_sucess = response.getString("success");
                            String resposne_userId = response.getString("user_id");
                            if(resposne_sucess.equals("true"))
                            {
                                progressBar.setVisibility(View.GONE);
                                sessionManager.createLoginSession(resposne_userId,KEY_USERID);
                                sessionManager.createLoginSession(Email,KEY_EMAIL);
                                sessionManager.createLoginSession(Password,KEY_PASSWORD);
                                sessionManager.createLoginSession(statuscode,Status_Code);
                                Intent intent = new Intent(LoginActivity.this, IdentiesMainActivity.class);
                                intent.putExtra("statuscode","2");
                                startActivity(intent);
                               // progressDialog.hide();
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this,resposne_message,Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);
    }

}
