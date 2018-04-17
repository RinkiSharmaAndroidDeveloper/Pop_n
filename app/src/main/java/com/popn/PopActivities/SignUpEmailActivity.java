package com.popn.PopActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    EditText email, password, confirmPassword;
    TextView done,phoneNumber,back;
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERID= "userID";
    public static final String Status_Code= "statusCode";
    String pass, confPAssword, emailID, fName, lName, dateBirth, resposne_userId,city;
    private Pattern pattern;
    private Matcher matcher;
    String phone, statuscode = "1";
    SessionManager sessionManager;
    HashMap<String,String > user;
    Network_url network_url;
    ProgressDialog progressDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);

        network_url =new Network_url();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        done = (TextView) findViewById(R.id.done);
        back = (TextView) findViewById(R.id.back);
        phoneNumber = (TextView) findViewById(R.id.TVLogin);
        pattern = Pattern.compile(EMAIL_PATTERN);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();

        phone = user.get("phoneNumber");

        phoneNumber.setText(phone);
        Intent intent =getIntent();
        fName = intent.getStringExtra("FirstName");
        lName = intent.getStringExtra("LastName");
        dateBirth = intent.getStringExtra("DateOfBirth");
        city = intent.getStringExtra("city");
        progressDialog=new ProgressDialog(this);
        done.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    public void getUserDetails() {
        pass = password.getText().toString();
        confPAssword = confirmPassword.getText().toString();
        emailID = email.getText().toString();
        if (emailID.equals("") == true && pass.equals("") == true && confPAssword.equals("") == true) {
            Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
        } else if (pass.equals("") == true) {
            password.setError("Enter your password");
            password.requestFocus();
        } else if (confPAssword.equals("") == true) {
            confirmPassword.setError("Enter your password");
            confirmPassword.requestFocus();
        } else if (emailID.equals("") == true) {
            email.setError("Enter your Email");
            email.requestFocus();
        } else if (validate(emailID) == false) {
            email.setError("This email doesn't exist");
            email.requestFocus();
        } else if (!pass.equals(confPAssword)) {
            Toast.makeText(getApplicationContext(), "Your password and confirm password not match", Toast.LENGTH_SHORT).show();
        } else if (validate(emailID) == true && pass.equals("") == false && confPAssword.equals("") == false && pass.equals(confPAssword)) {
            // here you can do whatever you want after checking
            progressBar.setVisibility(View.VISIBLE);
            getAuthenticateLogin();
          /*Intent intent = new Intent(SignUpEmailActivity.this, SignUpPersonalInfoActivity.class);
            intent.putExtra("phoneNumber",phone);
            intent.putExtra("email",emailID);
            intent.putExtra("passcode",pass);
            startActivity(intent);*/
        }
    }


    public void getAuthenticateLogin()
    {


        RequestQueue queue = null;
        queue = Volley.newRequestQueue(this);

          /*  String URL = network_url.Base_Url + network_url.Add_Identity +"identity_name"+"="+FName+LName+"&identity_username="+ IdentityName
                   +"&identity_userage="+DateBirth +"&identity_city="+ "Chandigarh"+"&identity_userid="+ "9215299771"+"&identity_color="+Get_Idenitiy_color
            +"&identity_userimage="+ profilepicencodedString;    */

        String URL = network_url.Base_Url + network_url.Signup+"phone_no="+phone+"&email="+emailID+"&password="+pass
                +"&first_name="+fName+"&last_name="+lName+"&dob="+dateBirth;
       // progressDialog.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            String resposne_message = response.getString("message");
                            String resposne_sucess = response.getString("success");
                            resposne_userId = response.getString("user_id");
                            if(resposne_sucess.equals("true"))
                            {

                                String PersonAge= getAge(dateBirth);
                                addIdentity(PersonAge);

                            }else{
                               // progressDialog.hide();
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUpEmailActivity.this,resposne_message,Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);


    }

    public void addIdentity(String age){

        RequestQueue queue = null;
        queue = Volley.newRequestQueue(this);
        //  identity_name, identity_username, identity_userage, identity_city, identity_userid, identity_color, identity_userimage
        String URL = network_url.Base_Url + network_url.Add_Identity +"identity_name"+"="+fName+lName+"&identity_username="+ "ID"
                +"&identity_userage="+age +"&identity_city="+city+"&identity_userid="+resposne_userId +"&identity_color="+"299,299,299"
                +"&identity_userimage="+ "test.jpeg";


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            String resposne_sucess = response.getString("success");
                            String resposne_User_Qr = response.getString("qr_image");
                            String resposne_message = response.getString("message");
                            if(resposne_sucess.equals("true"))
                            {
                                progressBar.setVisibility(View.GONE);
                                sessionManager.createLoginSession(pass,KEY_PASSWORD);
                                sessionManager.createLoginSession(emailID,KEY_EMAIL);
                                sessionManager.createLoginSession(resposne_userId,KEY_USERID);
                                sessionManager.createLoginSession(resposne_User_Qr,"Qr_Image");
                                sessionManager.createLoginSession(statuscode,Status_Code);
                                Intent intent = new Intent(SignUpEmailActivity.this, IdentiesMainActivity.class);
                                intent.putExtra("statuscode","1");
                                startActivity(intent);
                               // progressDialog.hide();
                            }else{
                                Toast.makeText(SignUpEmailActivity.this,resposne_message,Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);

    }

    private String getAge(String date1){
        Date yourDate=null;
        SimpleDateFormat yourDateFormat = new SimpleDateFormat("yyyy-MM-DD");

        try {
            yourDate = yourDateFormat.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(yourDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.done):
                getUserDetails();
                break;
                case (R.id.back):
                   finish();
                break;
        }
    }
}