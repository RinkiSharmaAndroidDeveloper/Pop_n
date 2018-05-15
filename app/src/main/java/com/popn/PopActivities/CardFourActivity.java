package com.popn.PopActivities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.popn.AsyncResult;
import com.popn.PopFragments.IdentityFragment;
import com.popn.PopModels.InterestModel;
import com.popn.R;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CardFourActivity extends AppCompatActivity implements View.OnClickListener{
    static final int REQUEST_LOCATION = 1;
    LinearLayout linearLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String statusCode;
    TextView back,next;
    ImageView backIcon;
    String keywordModel,purposeId,userId,Identity_Id;
    InterestModel interestModel;
    LocationManager locationManager;
    Double latitude, longitude;
    Geocoder geocoder;
    Network_url network_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_four);
        linearLayout =(LinearLayout)findViewById(R.id.linear_layout_identity);
        back=(TextView)findViewById(R.id.back);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        next=(TextView)findViewById(R.id.next);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        statusCode = user.get("statusCode");
        purposeId = user.get("purpose");
        userId = user.get("userID");
        android.content.Intent i = getIntent();
        interestModel = (InterestModel) i.getParcelableExtra("interection");
        keywordModel = i.getStringExtra("KEYWORDMODEL");
        locationManager = (android.location.LocationManager) getSystemService(android.content.Context.LOCATION_SERVICE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IdentityFragment fragment = new IdentityFragment(statusCode);
        fragment.newInstance(null,"1",asyncResult_selectedIdentityId);
        fragmentTransaction.add(R.id.linear_layout_identity, fragment);
        fragmentTransaction.commit();
        back.setOnClickListener(this);
        backIcon.setOnClickListener(this);
        next.setOnClickListener(this);
    }
 private void getLocation(){
        if (android.support.v4.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            android.support.v4.app.ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                geocoder = new Geocoder(this, java.util.Locale.getDefault());
                if(latitude != null && longitude != null) {
                    createBroadcast(latitude, longitude);
                    // city = addresses.get(0).getLocality();

                }else{
                    //Toast.makeText(getApplicationContext(), "Unable to find location", Toast.LENGTH_SHORT).show();
                }

            } else {
                android.widget.Toast.makeText(getApplicationContext(), "Unable to find location", android.widget.Toast.LENGTH_SHORT).show();
            }
        }
    }
    AsyncResult<String> asyncResult_selectedIdentityId = new AsyncResult<String>() {
        @Override
        public void success(String  click) {
            //asyncResult_selectedCategory.success(model);
            Identity_Id=click;
        }
        @Override
        public void error(String error) {

        }
    };
  public void createBroadcast(Double latitude, Double longitude){

        com.android.volley.RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = com.android.volley.toolbox.Volley.newRequestQueue(this);
        }
        network_url = new Network_url();
        //http://vertosys.com/popnapp/create_broadcast.php?user_id=39&message=testing&purpose_id=1&keyword_id=1&keyword_id2=1&keyword_id3=1&interest_id=1&latitude=30.7046&longitude=76.7179
        //
    if(Identity_Id!=null) {
    String URL = network_url.Base_Url + network_url.Create_Broadcast + "user_id=" + userId + "&message=" + "testing" + "&purpose_id=" + purposeId
            + "&keyword_id=" + keywordModel + "&interest_id=" + interestModel.getInterestId() + "&latitude=" + latitude + "&longitude=" + longitude + "&identity_id=" + Identity_Id;

    //progressDialog.show();
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

                        if (success.contains("true")) {
                            Toast.makeText(CardFourActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CardFourActivity.this, BroadcastActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CardFourActivity.this, message, Toast.LENGTH_SHORT).show();
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
}else{
    Toast.makeText(CardFourActivity.this, "Please select one identity.", Toast.LENGTH_SHORT).show();
}
    }

    @Override
    public void onClick(View v) {
     switch (v.getId())  {
         case (R.id.back):
              finish();
              break;
         case (R.id.backIcon):
              finish();
              break;
         case (R.id.next):
              getLocation();
              break;
         }
     }

}
