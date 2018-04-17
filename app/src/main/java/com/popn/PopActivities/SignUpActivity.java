package com.popn.PopActivities;

import android.content.Context;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.popn.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    TextView textView, code;
    EditText editText;
    Context mContext;
    String Number = null, CountryCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Spinner element

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        textView = (TextView) findViewById(R.id.done);
        code = (TextView) findViewById(R.id.code);
        editText = (EditText) findViewById(R.id.edit_screen1);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        mContext = getApplicationContext();

        List<String> categories = new ArrayList<String>();
        categories.add("United States");
        categories.add("India");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        textView.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        if (item.contains("United States")) {
            code.setText("+1");
            CountryCode = "+1";
        } else {
            code.setText("+91");
            CountryCode = "+91";
        }


    }

    public void onNothingSelected(AdapterView arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.done):
                Number = editText.getText().toString();
                if (Number != null) {
                    String fullNumber = CountryCode + Number;
                    String verifyCode = getRandomNumber();
                    Intent intent = new Intent(this, VerificationCodeActivity.class);
                    intent.putExtra("phoneNumber", fullNumber);
                    // intent.putExtra("verifyCode",verifyCode);
                    startActivity(intent);
                } else {
                    editText.setError("Enter your phone number.");
                }
                break;
        }
    }


    public String getRandomNumber() {
        String val = "" + ((int) (Math.random() * 900000) + 100000);
        return val;
    }
   /* public void verifyPhoneNumber(String verifyCode){
      String ACCOUNT_SID = "AC49e3cd6d3b0eae78e1c1226c8a7e2c0e";
      String AUTH_TOKEN = "13ff657f9a1c7f662c9e83cce2ac68b5";
        httpclient = new DefaultHttpClient();

        httppost = new HttpPost(
                "https://api.twilio.com/2010-04-01/Accounts/AC49e3cd6d3b0eae78e1c1226c8a7e2c0e/Messages");
        String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        httppost.setHeader("Authorization",
                base64EncodedCredentials);
        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("From",
                    "+18312192503"));
            nameValuePairs.add(new BasicNameValuePair("To",
                    "+919996843002"));
            nameValuePairs.add(new BasicNameValuePair("Body",
                    "Hey your verification code is: "+verifyCode));

            httppost.setEntity(new UrlEncodedFormEntity(
                    nameValuePairs));

            // Execute HTTP Post Request
         *//**//*   HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            System.out.println("Entity post is: "
                    + EntityUtils.toString(entity));*//**//*
            new RetrieveFeedTask().execute();

        }*//**//* catch (ClientProtocolException e) {

        }*//**//* catch (IOException e) {

        }
    }*/
/*    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            try {
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                System.out.println("Entity post is: "
                        + EntityUtils.toString(entity));

            } catch (Exception e) {
                this.exception = e;


            } finally {

            }
            return null;
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }*/
}


