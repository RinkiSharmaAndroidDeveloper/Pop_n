package com.popn.PopActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.popn.R;

public class BroadcastRequestActivity extends AppCompatActivity implements View.OnClickListener{
    String name;
    TextView userName, requestIdentity, send;
    ImageView back;
    EditText editComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_request);

        userName = (TextView) findViewById(R.id.userName);
        requestIdentity = (TextView) findViewById(R.id.requestIdentity);
        send = (TextView) findViewById(R.id.send);
        editComment = (EditText) findViewById(R.id.editComment);
        back = (ImageView) findViewById(R.id.back);

        name = getIntent().getStringExtra("Name");
        userName.setText(name);

        back.setOnClickListener(this);
        requestIdentity.setOnClickListener(this);
        send.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.back):
                Intent backIntent = new Intent(BroadcastRequestActivity.this, BroadcastActivity.class);
                startActivity(backIntent);
                break;
            case (R.id.requestIdentity):
                break;
            case (R.id.send):
                break;
        }
    }

}
