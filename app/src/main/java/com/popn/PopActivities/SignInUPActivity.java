package com.popn.PopActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.popn.R;

public class SignInUPActivity extends AppCompatActivity implements View.OnClickListener {
   Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        login =(Button)findViewById(R.id.login);
        signup =(Button)findViewById(R.id.signup);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.signup):
                Intent intent=new Intent(SignInUPActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
            case (R.id.login):
                Intent intent1=new Intent(SignInUPActivity.this,LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
