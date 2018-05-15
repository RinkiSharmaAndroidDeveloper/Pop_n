package com.popn.PopActivities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.popn.Adapters.CardOneAdapter;
import com.popn.Adapters.CardThreeAdapter;
import com.popn.AsyncResult;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.InterestModel;
import com.popn.PopModels.KeywordModel;
import com.popn.PopModels.PurposeModel;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CardThreeActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_LOCATION = 1;
    private RecyclerView recyclerView;
    private CardThreeAdapter cardThreeAdapter;
    private List<BroadcastLocationModel> broadcastLocationModelList;
    ImageView backIcon;
    private List<InterestModel> interestModelList;
    InterestModel interestModel;
    PurposeModel purposeModel;
    String  keywordModel;
    SessionManager sessionManager;
    TextView done,back;
    HashMap<String, String> user;
    String userId;
    Geocoder geocoder;
    LocationManager locationManager;
    Double latitude, longitude;
    List<Address> addresses;
    ArrayList<String> getNoOfKeywords;
    Network_url network_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_three);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        back = (TextView) findViewById(R.id.back);
        broadcastLocationModelList = new ArrayList<>();
        Intent i = getIntent();
        keywordModel = i.getStringExtra("KEYWORDMODEL");
        interestModelList = i.getParcelableArrayListExtra("interection");
        // Arraylist for number of keywords
      //  getNoOfKeywords = (ArrayList<String>) i.getSerializableExtra("GetNoOfKeywords");
        done = (TextView) findViewById(R.id.done);

        sessionManager = new SessionManager(getApplicationContext());
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        userId = user.get("userID");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        setAdapter();
        //prepareBroadcastLocationModel();
       // getInterestList();
        done.setOnClickListener(this);
        setAdapter();

        back.setOnClickListener(this);
        backIcon.setOnClickListener(this);

    }
    public void setAdapter() {
        cardThreeAdapter = new CardThreeAdapter(getApplicationContext(), interestModelList,asyncResult);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(cardThreeAdapter);
    }
    private void getInterestList(){
        interestModelList.add(interestModel);
        cardThreeAdapter.notifyDataSetChanged();
    }
    AsyncResult<InterestModel> asyncResult = new AsyncResult<InterestModel>() {
        @Override
        public void success(InterestModel interestModel) {
            Intent intent = new Intent(CardThreeActivity.this, CardFourActivity.class);
            intent.putExtra("KEYWORDMODEL",keywordModel);
            intent.putExtra("interection",interestModel);
            startActivity(intent);
        }

        @Override
        public void error(String error) {

        }
    };

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
                if(latitude != null && longitude != null) {
                    createBroadcast(latitude, longitude);
                    // city = addresses.get(0).getLocality();

                }else{
                    //Toast.makeText(getApplicationContext(), "Unable to find location", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Unable to find location", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void createBroadcast(Double latitude, Double longitude){

        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(this);
        }
        network_url = new Network_url();
        //http://vertosys.com/popnapp/create_broadcast.php?user_id=39&message=testing&purpose_id=1&keyword_id=1&keyword_id2=1&keyword_id3=1&interest_id=1&latitude=30.7046&longitude=76.7179
        //
        if(getNoOfKeywords.size()==1){
            getNoOfKeywords.add("0");
            getNoOfKeywords.add("0");

        }else if(getNoOfKeywords.size()==2){
            getNoOfKeywords.add("0");
            }
        String URL = network_url.Base_Url + network_url.Create_Broadcast + "user_id=" + userId + "&message=" + "testing" + "&purpose_id=" + purposeModel.getId()
                + "&keyword_id=" + getNoOfKeywords.get(0) + "&keyword_id2=" + getNoOfKeywords.get(1) + "&keyword_id3=" + getNoOfKeywords.get(2)
                + "&interest_id=" + interestModel.getInterestId() + "&latitude=" + latitude + "&longitude=" + longitude;

        //     progressDialog.show();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        // progressBar.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            //[{"identity_id":"17","identity_name":"ID","identity_username":"Abhishek","identity_userage":"29",
                            // "identity_city":"Cupertino","identity_userid":"7","identity_color":"299,299,299\"","identity_userimage":""}]}
                            Log.e("Data23", response.getString("message"));
                            String message = response.getString("message");
                            String success = response.getString("success");

                            if(success.contains("true")){
                                Toast.makeText(CardThreeActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CardThreeActivity.this, BroadcastActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(CardThreeActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            //  progressDialog.hide();
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.back):
                finish();
                break;
            case (R.id.backIcon):
                finish();
                break;
            case (R.id.done):
                Intent intent=new Intent(CardThreeActivity.this,CardFourActivity.class);
                startActivity(intent);
              //  getLocation();
                break;
        }

    }
}
