package com.popn.PopActivities;

import android.content.Intent;
import android.os.Parcelable;
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
import com.popn.Adapters.BroadcastLocationAdapter;
import com.popn.Adapters.CardOneAdapter;
import com.popn.AsyncResult;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.InterestModel;
import com.popn.PopModels.KeywordModel;
import com.popn.PopModels.PurposeModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardOne extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private CardOneAdapter cardOneAdapter;
    private List<PurposeModel> broadcastLocationModelList;
    ImageView backIcon;
    TextView back;
    private List<PurposeModel> purposeModelList;
    private List<InterestModel> interestModelList;
    private List<KeywordModel> keywordModelList;

    PurposeModel purposeModel;
    InterestModel interestModel;
    KeywordModel keywordModel;
    Network_url network_url;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_one);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        back = (TextView) findViewById(R.id.back);
        broadcastLocationModelList = new ArrayList<>();
        purposeModelList = new ArrayList<>();
        interestModelList = new ArrayList<>();
        keywordModelList = new ArrayList<>();
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        userID = user.get("userID");
        getPurposeList();
        setAdapter();
        back.setOnClickListener(this);
        backIcon.setOnClickListener(this);
    }


    public void setAdapter() {
        cardOneAdapter = new CardOneAdapter(getApplicationContext(), purposeModelList, asyncResult);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(cardOneAdapter);
    }

    AsyncResult<PurposeModel> asyncResult = new AsyncResult<PurposeModel>() {
        @Override
        public void success(PurposeModel purposeModel) {
            Intent intent = new Intent(CardOne.this, CardTwoActivity.class);
            intent.putParcelableArrayListExtra("interection", (ArrayList<? extends Parcelable>) interestModelList);
            sessionManager.createLoginSession(purposeModel.getId(),"purpose");
            startActivity(intent);
        }

        @Override
        public void error(String error) {

        }
    };

    public void getPurposeList() {

        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(this);
        }
        network_url = new Network_url();

        String URL = network_url.Base_Url + network_url.GetPKI+"user_id="+userID;

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

                                JSONObject jsonObj = new JSONObject(response.getString("data"));
                                JSONArray jsonArrayPurpose = jsonObj.getJSONArray("purpose_list");
                                JSONArray jsonArrayKeywords = jsonObj.getJSONArray("keywords_list");
                                JSONArray jsonArrayInterest = jsonObj.getJSONArray("interest_list");

                                for(int i = 0; i < jsonArrayPurpose.length(); i++){
                                    JSONObject purposeData = jsonArrayPurpose.getJSONObject(i);

                                    String purposeId = purposeData.getString("id");
                                    String purposeName = purposeData.getString("name");

                                    purposeModel = new PurposeModel(purposeId, purposeName);
                                    purposeModelList.add(purposeModel);

                                }
                                cardOneAdapter.notifyDataSetChanged();

                                for(int i = 0; i < jsonArrayKeywords.length(); i++){
                                    JSONObject keywordData = jsonArrayKeywords.getJSONObject(i);

                                    String keywordId = keywordData.getString("id");
                                    String keywordName = keywordData.getString("name");
                                    String keywordColor = keywordData.getString("keyword_color");

                                    keywordModel = new KeywordModel(keywordId, keywordName, keywordColor);
                                    keywordModelList.add(keywordModel);

                                }
                                // cardTwoAdapter.notifyDataSetChanged();

                                for(int i = 0; i < jsonArrayInterest.length(); i++){
                                    JSONObject interestData = jsonArrayInterest.getJSONObject(i);

                                    String interestId = interestData.getString("id");
                                    String interestName = interestData.getString("name");

                                    interestModel = new InterestModel(interestId, interestName);
                                    interestModelList.add(interestModel);

                                }
                                cardOneAdapter.notifyDataSetChanged();
                            } else{
                                Toast.makeText(CardOne.this, message, Toast.LENGTH_SHORT).show();
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
        }
    }
}
