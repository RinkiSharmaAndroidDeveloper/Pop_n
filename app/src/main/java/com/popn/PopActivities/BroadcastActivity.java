package com.popn.PopActivities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popn.R;
import com.popn.PopFragments.BroadcastLocationFragment;
import com.popn.PopFragments.BroadcastPersonalFragment;
import com.popn.PopFragments.IdentityFragment;


/**
 * Created by Android-Dev2 on 4/6/2018.
 */

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linearLayout1;
    Button location, personal;
    ImageView plus,broadcastImage,idenityImage;
    TextView broadcastTxt,identityTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        linearLayout1 = (LinearLayout) findViewById(R.id.fragment_idenity1);
        location = (Button) findViewById(R.id.location);
        personal = (Button) findViewById(R.id.personal);
        plus = (ImageView) findViewById(R.id.plus);
        broadcastImage = (ImageView) findViewById(R.id.interaction_imageView1);
        idenityImage = (ImageView) findViewById(R.id.identity_imageView);
        broadcastTxt = (TextView) findViewById(R.id.broadcast_text);
        identityTxt = (TextView) findViewById(R.id.identity_text);
        loadBroadcastLocationFragment();
        location.setOnClickListener(this);
        personal.setOnClickListener(this);
        plus.setOnClickListener(this);
        broadcastImage.setOnClickListener(this);
        idenityImage.setOnClickListener(this);
    }


    public void loadBroadcastPersonalFragment(){
        BroadcastPersonalFragment broadcastPersonalFragment = new BroadcastPersonalFragment();
        Bundle broadcastPersonalBundle = new Bundle();
        broadcastPersonalFragment.setArguments(broadcastPersonalBundle);
        //broadcastLocationFragment.newInstance(asyncResult_clickEditBtn);
        FragmentManager broadcastLocationManager = getFragmentManager();
        FragmentTransaction broadcastLocationTransaction = broadcastLocationManager.beginTransaction();
        broadcastLocationTransaction.replace(R.id.fragment_idenity1, broadcastPersonalFragment, null);
        broadcastLocationTransaction.addToBackStack(null);
        broadcastLocationTransaction.commit();

    }


    public void loadBroadcastLocationFragment(){
        BroadcastLocationFragment broadcastLocationFragment = new BroadcastLocationFragment();
        Bundle broadcastLocationBundle = new Bundle();
        broadcastLocationFragment.setArguments(broadcastLocationBundle);
        //broadcastLocationFragment.newInstance(asyncResult_clickEditBtn);
        FragmentManager broadcastLocationManager = getFragmentManager();
        FragmentTransaction broadcastLocationTransaction = broadcastLocationManager.beginTransaction();
        broadcastLocationTransaction.replace(R.id.fragment_idenity1, broadcastLocationFragment, null);
        broadcastLocationTransaction.addToBackStack(null);
        broadcastLocationTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.location):
                personal.setBackgroundResource(R.drawable.border_pink_right_blank);
                location.setBackgroundResource(R.drawable.border_pink_left_fill);
                personal.setTextColor(Color.parseColor("#ff2d55"));
                location.setTextColor(Color.parseColor("#ffffff"));
                loadBroadcastLocationFragment();
                break;
            case (R.id.personal):
                personal.setBackgroundResource(R.drawable.border_pink_right_fill);
                location.setBackgroundResource(R.drawable.border_pink_left_blank);
                location.setTextColor(Color.parseColor("#ff2d55"));
                personal.setTextColor(Color.parseColor("#ffffff"));
                loadBroadcastPersonalFragment();
                break;
            case (R.id.plus):
                Intent intent = new Intent(BroadcastActivity.this, CardOne.class);
                startActivity(intent);
                break;
            case (R.id.interaction_imageView1):
                broadcastImage.setImageResource(R.drawable.broadcasticonpink);
                idenityImage.setImageResource(R.drawable.identitiesgrayicon);
                identityTxt.setTextColor(Color.parseColor("#868383"));
                broadcastTxt.setTextColor(Color.parseColor("#ff2d55"));
                break;

            case (R.id.identity_imageView):
                idenityImage.setImageResource(R.drawable.identitiespinkicon);
                broadcastImage.setImageResource(R.drawable.broadcasticon);
                identityTxt.setTextColor(Color.parseColor("#ff2d55"));
                broadcastTxt.setTextColor(Color.parseColor("#868383"));
                Intent intent1=new Intent(BroadcastActivity.this,IdentiesMainActivity.class);
                startActivity(intent1);
                break;
        }
    }


}
