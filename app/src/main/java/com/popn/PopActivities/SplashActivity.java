package com.popn.PopActivities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.popn.R;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String email=null,password=null,keyuserId=null,phoneNumber=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                email = user.get("email");
                password = user.get("password");
                keyuserId = user.get("userID");
                phoneNumber = user.get("phoneNumber");
                if(email!=null && password!=null&&keyuserId!=null) {
                    Intent i = new Intent(SplashActivity.this, IdentiesMainActivity.class);
                    startActivity(i);
                }else if(phoneNumber!=null && email==null && password==null && keyuserId==null)
                {
                    Intent i = new Intent(SplashActivity.this, SignUpPersonalInfoActivity.class);
                    startActivity(i);
                }else
                {
                    Intent i = new Intent(SplashActivity.this, SignInUPActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    }

