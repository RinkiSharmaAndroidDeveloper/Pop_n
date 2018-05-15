package com.popn.PopFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.popn.Adapters.BroadcastLocationAdapter;
import com.popn.AsyncResult;
import com.popn.PopActivities.BroadcastDetailActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.PersonalBroadcastModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BroadcastPersonalFragment extends Fragment {
      RecyclerView recyclerView;
      TextView textView;
      ProgressBar progressBar;
      Network_url network_url;
      List<PersonalBroadcastModel> personalBroadcastModelList = new ArrayList<>();
      PersonalBroadcastModel personalBroadcastModel;
      BroadcastLocationAdapter broadcastLocationAdapter;
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.broadcast_personal_fragment, container, false);
         recyclerView =(RecyclerView)view.findViewById(R.id.recycler_view);
         textView = (TextView) view.findViewById(R.id.text);
         progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
         network_url =new Network_url();
         getPersonalBroadcastList();
        return view;
    }
    public void setAdapter(List<PersonalBroadcastModel> personalBroadcastModelList) {
        broadcastLocationAdapter = new BroadcastLocationAdapter(getContext(), personalBroadcastModelList,asyncResult_clickBtn);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(broadcastLocationAdapter);
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
    public void isListShow(List<PersonalBroadcastModel> personalBroadcastModelList){
         if(personalBroadcastModelList.size()>0)
         {
             textView.setVisibility(View.GONE);
             recyclerView.setVisibility(View.VISIBLE);
             setAdapter(personalBroadcastModelList);
         }else{
             textView.setVisibility(View.VISIBLE);
             recyclerView.setVisibility(View.GONE);

         }
    }
    public void getPersonalBroadcastList()
    {
        if(personalBroadcastModelList.size()>0){
            personalBroadcastModelList.clear();
        }
        SessionManager sessionManager =new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        String keyuserId = user.get("userID");
        RequestQueue queue = null;
        progressBar.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(getContext());

        String URL = network_url.Base_Url + network_url.PersonalBroadcast +"user_id="+keyuserId;
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
                                   /* String Latitude = jsonObject1.getString("latitude");
                                    String Longitude = jsonObject1.getString("longitude");*/
                                    String Purposel = jsonObject1.getString("purpose");
                                    String Keyword1 = jsonObject1.getString("keyword");
                                    String identity = jsonObject1.getString("identity_id");
                                  /*  String Keyword2 = jsonObject1.getString("keyword2");
                                    String Keyword3 = jsonObject1.getString("keyword3");*/
                                    String Interest = jsonObject1.getString("interest");
                                    personalBroadcastModel = new PersonalBroadcastModel(ID,UserName,null,null,Purposel,Keyword1,identity,null,Interest);
                                    personalBroadcastModelList.add(personalBroadcastModel);
                                }
                                isListShow(personalBroadcastModelList);
                                progressBar.setVisibility(View.GONE);
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
