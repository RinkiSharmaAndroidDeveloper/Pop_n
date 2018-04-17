package com.popn.PopActivities;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.popn.AsyncResult;
import com.popn.PopFragments.AddConnectionFragment;
import com.popn.PopFragments.AddIdentityFragment;
import com.popn.PopFragments.ConnectionsFragment;
import com.popn.PopFragments.EditInfoFragment;
import com.popn.PopFragments.GetIdentityById;
import com.popn.PopFragments.Identies_Network_ScreenFragment;
import com.popn.PopFragments.IdentityFragment;
import com.popn.PopFragments.IdentityUserFragment;
import com.popn.PopFragments.Network_detail_fragment;
import com.popn.PopFragments.RegistrationFragment;
import com.popn.PopModels.ConnectionModel;
import com.popn.PopModels.IdentityInformationModel;
import com.popn.PopModels.SignupModel;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tellh.com.stickyheaderview_rv.adapter.DataBean;

public class IdentiesMainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout personalLayout, identityLayout, linearFragment1, linearFragment2,
            mainLinear, linearFragment3, linearFragment4, linearFragment5, linearFragmentGetIdentityId,addNewFragment;
    Button networkButton, personalButton;;

    SignupModel signupModel;
    String location;
    ImageView plus;
    ImageView interactionImageView, broadcastImageView, identityImageView;
    TextView interaction, broadcast, identities;
    LinearLayout identitiesMainLayout,broadcastMainLayout,interactionMainLayout;
    //qr code scanner object
    private IntentIntegrator qrScan;
    String scannerResult;
    String statusCode;
    SessionManager sessionManager;
    HashMap<String,String > user;
    boolean onClick = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identies_main);
        personalLayout = (LinearLayout) findViewById(R.id.linear_personal);
        identityLayout = (LinearLayout) findViewById(R.id.identit_person);
        linearFragment1 = (LinearLayout) findViewById(R.id.fragment_idenity1);
        linearFragment2 = (LinearLayout) findViewById(R.id.fragment_idenity2);
        mainLinear = (LinearLayout) findViewById(R.id.main_linear_layout);
        linearFragment3 = (LinearLayout) findViewById(R.id.fragment_idenity3);
        linearFragment4 = (LinearLayout) findViewById(R.id.fragment_idenity4);
        linearFragment5 = (LinearLayout) findViewById(R.id.fragment_idenity5);
        linearFragmentGetIdentityId = (LinearLayout) findViewById(R.id.fragment_getidentitybyid);
        addNewFragment = (LinearLayout) findViewById(R.id.fragment_add_new_connection);
        networkButton = (Button) findViewById(R.id.networkBtn);
        personalButton = (Button) findViewById(R.id.personalBtn);
        identitiesMainLayout = (LinearLayout) findViewById(R.id.identitiesMainLayout);
        broadcastMainLayout = (LinearLayout) findViewById(R.id.broadcastMainLayout);
        interactionMainLayout = (LinearLayout) findViewById(R.id.interactionMainLayout);
        interactionImageView = (ImageView) findViewById(R.id.interaction_imageView);
        broadcastImageView = (ImageView) findViewById(R.id.interaction_imageView1);
        identityImageView = (ImageView) findViewById(R.id.interaction_imageView2);
        interaction = (TextView) findViewById(R.id.textView);
        broadcast = (TextView) findViewById(R.id.textView1);
        identities = (TextView) findViewById(R.id.textView2);

        plus =  (ImageView) findViewById(R.id.plus);

        qrScan = new IntentIntegrator(this);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        statusCode = user.get("statusCode");
        linearFragment2.setVisibility(View.GONE);
        linearFragment3.setVisibility(View.GONE);
        linearFragment4.setVisibility(View.GONE);
        linearFragment5.setVisibility(View.GONE);
        addNewFragment.setVisibility(View.GONE);
        networkButton.setOnClickListener(this);
        plus.setOnClickListener(this);
        identitiesMainLayout.setOnClickListener(this);
        broadcastMainLayout.setOnClickListener(this);
        interactionMainLayout.setOnClickListener(this);
        personalButton.setOnClickListener(this);
        getDisplayInfoFragment();

    }


    //Upload second DisplayInfo Information fragment
   /* public void getDisplayInfoFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IdentityFragment fragment = new IdentityFragment();
        fragment.newInstance(asyncResult_clickEditBtn);
        fragmentTransaction.add(R.id.fragment_idenity1, fragment);
        fragmentTransaction.commit();
    }*/
    public void getDisplayInfoFragment(){
        onClick =true;
        IdentityFragment fragmentIdentity = new IdentityFragment(statusCode);
        Bundle bundleIdentity = new Bundle();
        fragmentIdentity.setArguments(bundleIdentity);
        fragmentIdentity.newInstance(asyncResult_clickEditBtn);
        FragmentManager managerIdentity = getFragmentManager();
        FragmentTransaction transactionIdentity = managerIdentity.beginTransaction();
        transactionIdentity.replace(R.id.fragment_idenity1, fragmentIdentity, null);
        transactionIdentity.addToBackStack(null);
        transactionIdentity.commit();
       /* FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IdentityFragment fragment = new IdentityFragment();
        fragment.newInstance(asyncResult_clickEditBtn);
        fragmentTransaction.add(R.id.fragment_idenity1, fragment);
        fragmentTransaction.commit();*/
    }
    //Upload second Edit Information fragment
    AsyncResult<IdentityInformationModel > asyncResult_clickEditBtn = new AsyncResult<IdentityInformationModel>()  {
        @Override
        public void success(IdentityInformationModel identityInformationModel) {
            //asyncResult_selectedCategory.success(model);
            linearFragment2.setVisibility(View.VISIBLE);
            identityLayout.setVisibility(View.GONE);
            personalLayout.setVisibility(View.GONE);
            linearFragment1.setVisibility(View.GONE);
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            EditInfoFragment fragment1 = new EditInfoFragment();
            fragment1.newInstance(asyncResult_addNewConnection,identityInformationModel);
            fragmentTransaction1.add(R.id.fragment_idenity2, fragment1);
            fragmentTransaction1.commit();
        }
        @Override
        public void error(String error) {

        }
    };
    AsyncResult<String > asyncResult_clickRegisterBtn = new AsyncResult<String >() {
        @Override
        public void success(String  click) {
            //asyncResult_selectedCategory.success(model);

        }
        @Override
        public void error(String error) {

        }
    };


    AsyncResult<String > asyncResult_identityClick = new AsyncResult<String >() {
        @Override
        public void success(String click) {
            //asyncResult_selectedCategory.success(model);
           /* linearFragment5.setVisibility(View.VISIBLE);
            mainLinear.setVisibility(View.GONE);
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            IdentityUserFragment fragment1 = new IdentityUserFragment();
            fragment1.newInstance(asyncResult_addNewConnection);
            fragmentTransaction1.add(R.id.fragment_idenity5, fragment1);
            fragmentTransaction1.commit();*/
            linearFragment1.setVisibility(View.GONE);
            mainLinear.setVisibility(View.GONE);
            linearFragment4.setVisibility(View.GONE);
            linearFragment5.setVisibility(View.VISIBLE);
            Network_detail_fragment fragmentBroadcast = new Network_detail_fragment();
            Bundle bundleBroad = new Bundle();
            fragmentBroadcast.setArguments(bundleBroad);
            FragmentManager managerBroad = getFragmentManager();
            FragmentTransaction transactionBroad = managerBroad.beginTransaction();
            transactionBroad.replace(R.id.fragment_idenity5, fragmentBroadcast, null);
            fragmentBroadcast.newInstance(click);
            transactionBroad.addToBackStack(null);
            transactionBroad.commit();
        }
        @Override
        public void error(String error) {

        }
    };
    public void LoadAddIdentityFragment(){
        identityLayout.setVisibility(View.GONE);
        personalLayout.setVisibility(View.GONE);
        AddIdentityFragment addIdentityFragment = new AddIdentityFragment();
        Bundle bundleaddIdentityFragment = new Bundle();
        addIdentityFragment.setArguments(bundleaddIdentityFragment);
        FragmentManager manageraddIdentityFragment = getFragmentManager();
        FragmentTransaction transactionaddIdentityFragment = manageraddIdentityFragment.beginTransaction();
        transactionaddIdentityFragment.replace(R.id.fragment_idenity1, addIdentityFragment, null);
        transactionaddIdentityFragment.addToBackStack(null);
        transactionaddIdentityFragment.commit();

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
    //Upload second Connection fragment
    AsyncResult<String > asyncResult_addNewConnection = new AsyncResult<String >() {
        @Override
        public void success(String click) {

            linearFragment3.setVisibility(View.VISIBLE);
            mainLinear.setVisibility(View.GONE);
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            ConnectionsFragment fragment1 = new ConnectionsFragment();
            fragment1.newInstance(asyncResult_addNewConnectionData,click);
            fragmentTransaction1.add(R.id.fragment_idenity3, fragment1);
            fragmentTransaction1.commit();
        }
        @Override
        public void error(String error) {

        }
    };

    AsyncResult<ConnectionModel> asyncResult_addNewConnectionData = new AsyncResult<ConnectionModel >() {
        @Override
        public void success(ConnectionModel connectionModel) {

            addNewFragment.setVisibility(View.VISIBLE);
            linearFragment3.setVisibility(View.GONE);
            mainLinear.setVisibility(View.GONE);
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            AddConnectionFragment fragment1 = new AddConnectionFragment();
            fragment1.newInstance(connectionModel);
            fragmentTransaction1.add(R.id.fragment_add_new_connection, fragment1);
            fragmentTransaction1.commit();
        }
        @Override
        public void error(String error) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.plus):
                if(onClick == true){
                    LoadAddIdentityFragment();
                } else if(onClick == false) {
                    qrScan.setCameraId(0);
                    // Use a specific camera of the device
                    qrScan.setOrientationLocked(true);
                    qrScan.setBeepEnabled(true);
                    qrScan.setCaptureActivity(CaptureActivityPortrait.class);
                    qrScan.initiateScan();
                } else{

                }
                break;
            case (R.id.personalBtn):
                networkButton.setBackgroundResource(R.drawable.border_pink_right_blank);
                personalButton.setBackgroundResource(R.drawable.border_pink_left_fill);
                personalButton.setTextColor(Color.parseColor("#ffffff"));
                networkButton.setTextColor(Color.parseColor("#ff2d55"));

                linearFragment4.setVisibility(View.GONE);
                linearFragment1.setVisibility(View.VISIBLE);
             //   linearFragment1.setVisibility(View.VISIBLE);
                identityLayout.setVisibility(View.VISIBLE);
                personalLayout.setVisibility(View.VISIBLE);
                getDisplayInfoFragment();
                break;

            case (R.id.networkBtn):
                onClick=false;
                networkButton.setBackgroundResource(R.drawable.border_pink_right_fill);
                personalButton.setBackgroundResource(R.drawable.border_pink_left_blank);
                personalButton.setTextColor(Color.parseColor("#ff2d55"));
                networkButton.setTextColor(Color.parseColor("#ffffff"));


                linearFragment1.setVisibility(View.GONE);
                linearFragment4.setVisibility(View.VISIBLE);

                FragmentManager fragmentManagerNetwork = getFragmentManager();
                FragmentTransaction fragmentTransactionNetwork = fragmentManagerNetwork.beginTransaction();
                Identies_Network_ScreenFragment fragmentNetwork = new Identies_Network_ScreenFragment();
                fragmentNetwork.newInstance(asyncResult_identityClick);
                fragmentTransactionNetwork.add(R.id.fragment_idenity4, fragmentNetwork);
                fragmentTransactionNetwork.commit();
                break;
            case (R.id.identitiesMainLayout):
                identityImageView.setImageResource(R.drawable.identitiespinkicon);
                interactionImageView.setImageResource(R.drawable.interacticon);
                broadcastImageView.setImageResource(R.drawable.broadcasticon);
                identities.setTextColor(Color.parseColor("#ff2d55"));
                broadcast.setTextColor(Color.parseColor("#868383"));
                interaction.setTextColor(Color.parseColor("#868383"));
                break;
            case (R.id.broadcastMainLayout):
                broadcastImageView.setImageResource(R.drawable.broadcasticonpink);
                interactionImageView.setImageResource(R.drawable.interacticon);
                identityImageView.setImageResource(R.drawable.identitiesgrayicon);
                identities.setTextColor(Color.parseColor("#868383"));
                broadcast.setTextColor(Color.parseColor("#ff2d55"));
                interaction.setTextColor(Color.parseColor("#868383"));
                Intent intent =new Intent(IdentiesMainActivity.this,BroadcastActivity.class);
                startActivity(intent);
                break;
            case (R.id.interactionMainLayout):
                interactionImageView.setImageResource(R.drawable.interacticonpink);
                broadcastImageView.setImageResource(R.drawable.broadcasticon);
                identityImageView.setImageResource(R.drawable.identitiesgrayicon);
                identities.setTextColor(Color.parseColor("#868383"));
                broadcast.setTextColor(Color.parseColor("#868383"));
                interaction.setTextColor(Color.parseColor("#ff2d55"));
                break;
        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                scannerResult = String.valueOf(result.getContents());
                linearFragment1.setVisibility(View.GONE);
                linearFragment4.setVisibility(View.GONE);
                identityLayout.setVisibility(View.GONE);
                personalLayout.setVisibility(View.GONE);
                linearFragmentGetIdentityId.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                GetIdentityById fragment = new GetIdentityById(scannerResult);
                //fragment.newInstance(asyncResult_identityClick);
                fragmentTransaction.add(R.id.fragment_getidentitybyid, fragment);
                fragmentTransaction.commit();
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
