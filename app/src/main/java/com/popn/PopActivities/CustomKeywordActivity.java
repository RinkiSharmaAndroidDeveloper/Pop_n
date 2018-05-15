package com.popn.PopActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.popn.AsyncResult;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.InterestModel;
import com.popn.PopModels.KeywordModel;
import com.popn.PopModels.PurposeModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CustomKeywordActivity extends AppCompatActivity implements View.OnClickListener{
    static final int GETDATA = 1;
    ImageView backIcon;
    TextView addCustomKeyword, done, back;
    EditText getCustomKeyword;
    String getKeyword;
    BroadcastLocationModel broadcastLocationModel;
    public AsyncResult<BroadcastLocationModel> asyncResult_addNewConnectionData1;
    Network_url network_url;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_keyword);

        backIcon = (ImageView) findViewById(R.id.backIcon);
        back = (TextView) findViewById(R.id.back);
        done = (TextView) findViewById(R.id.done);
        addCustomKeyword = (TextView) findViewById(R.id.addCustomKeyword);
        getCustomKeyword = (EditText) findViewById(R.id.getCustomKeyword);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        userID = user.get("userID");
        back.setOnClickListener(this);
        backIcon.setOnClickListener(this);
        done.setOnClickListener(this);
        broadcastLocationModel = new BroadcastLocationModel();

    }


    public CustomKeywordActivity(){

    }

    public void getInterface1(AsyncResult<BroadcastLocationModel> asyncResult_addNewConnectionData) {
        asyncResult_addNewConnectionData1 = asyncResult_addNewConnectionData;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.back):
                finish();
                break;
            case (R.id.backIcon):
                finish();
                break;
            case (R.id.done):
                getKeyword=getCustomKeyword.getText().toString().replaceAll(" ", "%20");
                if(getKeyword!=null && !(getKeyword.equals("")))
                {
                    addCustomKeyword.setText(getKeyword);
                    getPurposeList();
                }else {
                    getCustomKeyword.setError("Please enter keyword");
                }


                break;
        }
    }

    public void getPurposeList() {

        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(this);
        }
        network_url = new Network_url();

        String URL = network_url.Base_Url + network_url.AddKeywords+"user_id="+userID+"&keyword_name="+getKeyword+"&keyword_color="+"255%20255%20255";

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
                                //  broadcastLocationModel.setName(getKeyword);
                                Intent intent = new Intent(CustomKeywordActivity.this,CardTwoActivity.class);
                                //intent.putExtra("GetKeyword", getKeyword);
                                startActivity(intent);
                                /*setResult(GETDATA, intent);
                                finish();*/
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
}
