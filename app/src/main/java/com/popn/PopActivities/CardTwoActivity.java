package com.popn.PopActivities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.popn.Adapters.CardOneAdapter;
import com.popn.Adapters.CardTwoAdapter;
import com.popn.AsyncResult;
import com.popn.PopFragments.AddConnectionFragment;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.ConnectionModel;
import com.popn.PopModels.InterestModel;
import com.popn.PopModels.KeywordModel;
import com.popn.PopModels.PurposeModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardTwoActivity extends AppCompatActivity implements View.OnClickListener {
    private List<BroadcastLocationModel> broadcastLocationModelList;

    static int count = 0;
    private CardTwoAdapter cardTwoAdapter;
    private List<KeywordModel> keywordModelList;
    private List<KeywordModel> keywordModelList1;
    ImageView backIcon;
    public List<String> getNoOfKeywords;
    static final int GETDATA = 1;
    TextView custom, add, back;
    KeywordModel keywordModel;
    List<InterestModel> interestModel;
    PurposeModel purposeModel;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String userID;
    Network_url network_url;
    String keyword = "", purposeName, purposeId;
    BroadcastLocationModel broadcastLocationModel;
    CustomKeywordActivity customKeywordActivity;
    LinearLayout.LayoutParams buttonLayoutParams;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_two);
       // Intent i = getIntent();
      //  keywordModel = (KeywordModel) i.getParcelableExtra("KEYWORDMODEL");
      //  interestModel = (InterestModel) i.getParcelableExtra("INTERESTMODEL");
       // purposeModel = (PurposeModel) i.getParcelableExtra("PURPOSEMODEL");

        custom = (TextView) findViewById(R.id.custom);
        add = (TextView) findViewById(R.id.add);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        back = (TextView) findViewById(R.id.back);
        // keywordModelList = new ArrayList<>();
        getNoOfKeywords = new ArrayList<>();
        network_url = new Network_url();
        keywordModelList = new ArrayList<>();
        interestModel = this.getIntent().getParcelableArrayListExtra("interection");

        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        userID = user.get("userID");

        // broadcastLocationModelList = new ArrayList<>();
        customKeywordActivity = new CustomKeywordActivity();
        custom.setOnClickListener(this);
        back.setOnClickListener(this);
        backIcon.setOnClickListener(this);
        getPurposeList();
        // AddCustomKeyword(null);
        //  setAdapter();
        //getKeywordList();

        add.setOnClickListener(this);
        //inflatelayout(keywordModelList);
    }



    private void onAddButtonClick() {
        Intent cardTwoIntent = new Intent(CardTwoActivity.this, CardThreeActivity.class);
        cardTwoIntent.putExtra("KEYWORDMODEL", keyword);
        cardTwoIntent.putParcelableArrayListExtra("interection", (ArrayList<? extends Parcelable>) interestModel);
        startActivity(cardTwoIntent);
    }


    private void inflatelayout(List<KeywordModel> keywordModelList1) {

        String ID = null;

        FlexboxLayout container = (FlexboxLayout) findViewById(R.id.v_container);


        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(10, 10, 10, 10);

        for (int i = 0; i < keywordModelList1.size(); i++) {
            final TextView tv = new TextView(getApplicationContext());
            tv.setText(keywordModelList1.get(i).getKeywordName());
            ID = keywordModelList1.get(i).getKeywordId();
            tv.setHeight(100);
            tv.setTextSize(20.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#868383"));

            final String finalID = ID;

            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                private boolean stateChanged;
                @Override
                public void onClick(View v) {

                    if(count > 9){
                        Toast.makeText(getApplicationContext(), "You can select maximum 10 keywords", Toast.LENGTH_SHORT).show();
                    }else{
                        if(stateChanged) {
                            count--;
                           // keyword = keyword + finalID +" ";
                            if(keyword.contains(finalID + ",")){
                                keyword = keyword.replace(finalID + ",", "");
                            }
                            //reset background to default;
                            if (finalI % 2 == 0) {
                                tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_pink));
                                tv.setTextColor(Color.parseColor("#868383"));
                            } else {
                                tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_blue));
                                tv.setTextColor(Color.parseColor("#868383"));
                            }

                        } else {
                            count++;
                            keyword = keyword + finalID + ",";

                            tv.setBackground(getResources().getDrawable(R.drawable.keywords_filled));
                            tv.setTextColor(Color.parseColor("#ffffff"));
                        }
                        stateChanged = !stateChanged;

                    }

                }

            });
            if (i % 2 == 0) {
                tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_pink));
            } else {
                tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_blue));
            }

            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(i);
            tv.setPadding(20, 10, 20, 10);

            container.addView(tv);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.add):
                if (keyword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please select at least 1 keyword", Toast.LENGTH_SHORT).show();
                } else  {
                    onAddButtonClick();
                    }
                break;
            case (R.id.custom):
                Intent intent = new Intent(CardTwoActivity.this, CustomKeywordActivity.class);
                startActivity(intent);
               // startActivityForResult(intent, GETDATA);
                break;
            case (R.id.back):
                finish();
                break;
            case (R.id.backIcon):
                finish();
                break;
        }
    }

    public void getPurposeList() {
        if (keywordModelList.size() > 0) {
            keywordModelList.clear();
        }
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(this);
        }
        network_url = new Network_url();

        String URL = network_url.Base_Url + network_url.GetPKI + "user_id=" + userID;

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

                            if (success.contains("true")) {

                                JSONObject jsonObj = new JSONObject(response.getString("data"));
                                JSONArray jsonArrayKeywords = jsonObj.getJSONArray("keywords_list");

                                for (int i = 0; i < jsonArrayKeywords.length(); i++) {
                                    JSONObject keywordData = jsonArrayKeywords.getJSONObject(i);

                                    String keywordId = keywordData.getString("id");
                                    String keywordName = keywordData.getString("name");
                                    String keywordColor = keywordData.getString("keyword_color");
                                    keywordModel = new KeywordModel(keywordId, keywordName, keywordColor);
                                    keywordModelList.add(keywordModel);

                                }
                                inflatelayout(keywordModelList);
                                // cardTwoAdapter.notifyDataSetChanged();

                            } else {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        KeywordModel keywordModel;
        List<KeywordModel> keywordModelList2 = new ArrayList<>();
        if (requestCode == GETDATA) {
            // Make sure the request was successful

            if (data != null) {

                //getPurposeList();
            }


        }
    }

}
