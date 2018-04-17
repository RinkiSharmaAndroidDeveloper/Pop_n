package com.popn.PopActivities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SignUpPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener{
    static final int REQUEST_LOCATION = 1;
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERID= "userID";

    EditText firstName, lastName, dateOfBirth;
    String email,passcode,fName=null,lName=null,dateBirth=null,phone,age=null,city,resposne_userId;
    TextView done,phoneNumber;
    Network_url network_url;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    Date year,month,day;
    SessionManager sessionManager;

    Geocoder geocoder;
    LocationManager locationManager;
    Double latitude, longitude;
    List<Address> addresses;
    HashMap<String,String > user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_personal_info);
        checkRuntimePermissions();
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        done = (TextView) findViewById(R.id.done1);
        phoneNumber = (TextView) findViewById(R.id.TVLogin);
        progressDialog=new ProgressDialog(this);
        network_url =new Network_url();
        done.setOnClickListener(this);
 //       Intent intent =getIntent();
    //    phone = intent.getStringExtra("phoneNumber");
    //    email= intent.getStringExtra("email");
 //       passcode= intent.getStringExtra("passcode");
      //  phoneNumber.setText(phone);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        sessionManager= new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();

        phone = user.get("phoneNumber");
        phoneNumber.setText(phone);
        getLocation();

    }
    private void checkRuntimePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

   /* private String getAge(String date1){
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
*/

    private void getLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    if(latitude != null && longitude != null) {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                         city = addresses.get(0).getLocality();


                    }else{
                        //Toast.makeText(getApplicationContext(), "Unable to find location", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Unable to find location", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                    getLocation();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.done1):

                fName =firstName.getText().toString();
                lName =lastName.getText().toString();
                dateBirth =dateOfBirth.getText().toString();
                boolean isYourDateBirth = isDateValid(dateBirth);
                if((fName!=null &&lName!=null&&dateBirth!=null &&isYourDateBirth)){
                    //getAuthenticateLogin();
                    Intent intent = new Intent(SignUpPersonalInfoActivity.this, SignUpEmailActivity.class);
                    intent.putExtra("FirstName",fName);
                    intent.putExtra("LastName",lName);
                    intent.putExtra("DateOfBirth",dateBirth);
                    intent.putExtra("city",city);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUpPersonalInfoActivity.this, "Please enter valid data", Toast.LENGTH_SHORT).show();
                }
                break;
            case (R.id.dateOfBirth):
                datePickerDialog.show();
                break;
        }
    }

    public static boolean isDateValid(String date){
        String strDate = null;
        if(date==null){
            System.out.println("Invalid Date");
            return false;
        }else{
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-DD");
            sdfrmt.setLenient(false);

            try
            {
                strDate = date;
                System.out.println(strDate);
                Date javaDate = sdfrmt.parse(strDate);}

            catch (ParseException e)
            {

                return false;
            }

            return true;
        }

    }
   /* public void getAuthenticateLogin()
    {


            RequestQueue queue = null;
            queue = Volley.newRequestQueue(this);

          *//*  String URL = network_url.Base_Url + network_url.Add_Identity +"identity_name"+"="+FName+LName+"&identity_username="+ IdentityName
                   +"&identity_userage="+DateBirth +"&identity_city="+ "Chandigarh"+"&identity_userid="+ "9215299771"+"&identity_color="+Get_Idenitiy_color
            +"&identity_userimage="+ profilepicencodedString;    *//*

            String URL = network_url.Base_Url + network_url.Signup+"phone_no="+phone+"&email="+email+"&password="+passcode
                    +"&first_name="+fName+"&last_name="+lName+"&dob="+dateBirth;
        progressDialog.show();

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
                                    progressDialog.hide();
                                    Toast.makeText(SignUpPersonalInfoActivity.this,resposne_message,Toast.LENGTH_SHORT).show();
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


    }*/

   /* public void addIdentity(String age){

        RequestQueue queue = null;
        queue = Volley.newRequestQueue(this);
      //  identity_name, identity_username, identity_userage, identity_city, identity_userid, identity_color, identity_userimage
           String URL = network_url.Base_Url + network_url.Add_Identity +"identity_name"+"="+fName+lName+"&identity_username="+ "ID"
                   +"&identity_userage="+age +"&identity_city="+ city+"&identity_userid="+resposne_userId +"&identity_color="+"299,299,299"
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
                                sessionManager.createLoginSession(passcode,KEY_PASSWORD);
                                sessionManager.createLoginSession(email,KEY_EMAIL);
                                sessionManager.createLoginSession(resposne_userId,KEY_USERID);
                                sessionManager.createLoginSession(resposne_User_Qr,"Qr_Image");
                                Intent intent = new Intent(SignUpPersonalInfoActivity.this, IdentiesMainActivity.class);
                                startActivity(intent);
                                progressDialog.hide();
                            }else{
                                Toast.makeText(SignUpPersonalInfoActivity.this,resposne_message,Toast.LENGTH_SHORT).show();
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

    }*/

}
