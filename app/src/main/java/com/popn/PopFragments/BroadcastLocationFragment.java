package com.popn.PopFragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.popn.Adapters.BroadcastLocationAdapter;
import com.popn.AsyncResult;
import com.popn.PopActivities.BroadcastDetailActivity;
import com.popn.PopActivities.BroadcastRequestActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.BroadcastDetailsModel;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.IdentityInformationModel;
import com.popn.PopModels.PersonalBroadcastModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Android-Dev2 on 4/6/2018.
 */

public class BroadcastLocationFragment extends Fragment implements View.OnClickListener {
    public static final int PERMISSION_ACCESS_COARSE_LOCATION = 99;
    static final int REQUEST_LOCATION = 1;
    private RecyclerView recyclerView;
    private BroadcastLocationAdapter broadcastLocationAdapter;
    private List<PersonalBroadcastModel> broadcastLocationModelList;
    ProgressBar progressBar;
    Network_url network_url;
    Double latitude,longitude;
    String LAT,LNG;
    List<PersonalBroadcastModel> personalBroadcastModelList = new ArrayList<>();
    PersonalBroadcastModel personalBroadcastModel;
   Geocoder geocoder;
   LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null ) {

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.broadcast_location_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        network_url =new Network_url();

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        getPersonalBroadcastList();
        return view;
    }

    AsyncResult<BroadcastLocationModel> asyncResult = new AsyncResult<BroadcastLocationModel>() {
        @Override
        public void success(BroadcastLocationModel broadcastLocationModel) {
            Intent intent = new Intent(getActivity(), BroadcastRequestActivity.class);
            intent.putExtra("Name", broadcastLocationModel.getName());
            startActivity(intent);
        }

        @Override
        public void error(String error) {

        }
    };

    public void setAdapter(List<PersonalBroadcastModel> personalBroadcastModelList) {
        broadcastLocationAdapter = new BroadcastLocationAdapter(getContext(), personalBroadcastModelList,asyncResult_clickBtn);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(broadcastLocationAdapter);
    }

    public void isListShow(List<PersonalBroadcastModel> personalBroadcastModelList){
        if(personalBroadcastModelList.size() > 0)
        {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            setAdapter(personalBroadcastModelList);
        }else{

            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
  private void getLocation(){

            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_ACCESS_COARSE_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                if(latitude != null && longitude != null) {

                 LAT = String.valueOf(latitude);
                 LNG = String.valueOf(longitude);
                }else{
                    Toast.makeText(getContext(), "Unable to find location", Toast.LENGTH_SHORT).show();
                }

            } else {

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
                  //  getLocation();
                } else {
                    Toast.makeText(getContext(), "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    AsyncResult<PersonalBroadcastModel> asyncResult_clickBtn = new AsyncResult<PersonalBroadcastModel>() {
        @Override
        public void success(PersonalBroadcastModel  click) {
            //asyncResult_selectedCategory.success(model);
            Intent intent =new Intent(getContext(), BroadcastDetailActivity.class);
            intent.putExtra("broadcastModal",click);
            startActivity(intent);
        }
        @Override
        public void error(String error) {

        }
    };
    public void getPersonalBroadcastList()
    {
        if(personalBroadcastModelList.size()>0){
            personalBroadcastModelList.clear();
        }

        RequestQueue queue = null;
        progressBar.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(getContext());
     //   latitude=17.4272&longitude=78.5067
        String URL = network_url.Base_Url + network_url.LocationBroadcast +"latitude="+LAT +"&longitude="+LNG;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            //  Log.e("Data23", response.getString("message"));
                            String resposne_message = response.getString("success");
                            JSONArray jsonObj = new JSONArray(response.getString("data"));
                            if(resposne_message.equals("true")) {
                                for (int i = 0; i < jsonObj.length(); i++) {
                                    JSONObject jsonObject1 = jsonObj.getJSONObject(i);
                                    String ID = jsonObject1.getString("id");
                                    String UserName = jsonObject1.getString("user_name");
                                    String Purposel = jsonObject1.getString("purpose");
                                    String Keyword1 = jsonObject1.getString("keyword");
                                    String identity = jsonObject1.getString("identity_id");
                                   // String Keyword3 = jsonObject1.getString("keyword3");*/
                                    String Interest = jsonObject1.getString("interest");

                                    personalBroadcastModel = new PersonalBroadcastModel(ID,UserName,null,null,Purposel,Keyword1,identity,null,Interest);
                                    personalBroadcastModelList.add(personalBroadcastModel);
                                }
                                isListShow(personalBroadcastModelList);

                                //
                            }
                            else{

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
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
