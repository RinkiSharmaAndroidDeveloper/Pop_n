package com.popn.PopFragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.popn.Adapters.AlphaAdapter;
import com.popn.Adapters.ConnectionAdapter;
import com.popn.Adapters.ItemHeader;
import com.popn.Adapters.ItemHeaderViewBinder;
import com.popn.Adapters.UserItemViewBinder;
import com.popn.AsyncResult;
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.User;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class Identies_Network_ScreenFragment extends Fragment {
    StickyHeaderViewAdapter adapter;
    RecyclerView rv, alphaRecycler;
    User data;
    AlphaAdapter alphaAdapter;
    List<String> alphabetItem = new ArrayList<>();
    List<User> listData = new ArrayList<>();
    List<DataBean> userListBak = new ArrayList<>();
    String currentPrefix;
    List<String> perfix  =new ArrayList<>();
    Network_url network_url;
    AsyncResult<String> asyncResult_selectedCategory;
    EditText networkSearch;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_identies__network__screen, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recyclerview);
        alphaRecycler = (RecyclerView) view.findViewById(R.id.alpharecycler);
        networkSearch =(EditText)view.findViewById(R.id.network_search);
        progressBar =(ProgressBar) view.findViewById(R.id.progressBar);
        getFriendsList();
        setData();
        setRecyclerView();
        initView();
        networkSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });



        return view;
    }
    void filter(String text){
        List<User> temp = new ArrayList();
        if(listData!=null && listData.size()>0) {
            for (User d : listData) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if (d.getUserFName().contains(text)||d.getUserLName().contains(text)) {
                    temp.add(d);
                }
            }
            setAdapterData(temp);
        }
        //update recyclerview

    }
    public  void setRecylerView(){
        adapter = new StickyHeaderViewAdapter(userListBak)
                .RegisterItemType(new UserItemViewBinder(asyncResult_addNewConnection))
                .RegisterItemType(new ItemHeaderViewBinder());
        rv.setAdapter(adapter);
    }

    public Identies_Network_ScreenFragment newInstance(AsyncResult<String> asyncResult_selectedCategory) {
        Identies_Network_ScreenFragment fragment = new Identies_Network_ScreenFragment();
        Bundle args = new Bundle();
        this.asyncResult_selectedCategory=asyncResult_selectedCategory;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void setAdapterData(List<User> listData){
        if(userListBak!=null && userListBak.size()>0)
        {
            userListBak.clear();
            perfix.clear();

        }
        for (User s :listData){
               String firstWord= String.valueOf(s.getUserFName().charAt(0));
                perfix.add(firstWord);
        }
        if(perfix.size()>0&& perfix!=null) {
            currentPrefix = perfix.get(0).substring(0, 1).toUpperCase();
            userListBak.add(new ItemHeader(currentPrefix));
            int i = 0;
            for (User user : listData) {
                if (currentPrefix.compareToIgnoreCase(perfix.get(i).substring(0, 1)) == 0) {
                    userListBak.add(user);
                    i++;
                } else {
                    currentPrefix = perfix.get(i).substring(0, 1).toUpperCase();
                    userListBak.add(new ItemHeader(currentPrefix));
                    userListBak.add(user);
                    i++;
                }
            }
        }
        setRecylerView();
        adapter.notifyDataSetChanged();

    }
    AsyncResult<String > asyncResult_addNewConnection = new AsyncResult<String >() {
        @Override
        public void success(String  click) {

            asyncResult_selectedCategory.success(click);
        }
        @Override
        public void error(String error) {

        }
    };
    public void getFriendsList()
    {
        SessionManager sessionManager =new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        String keyuserId = user.get("userID");
        RequestQueue queue = null;
        progressBar.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(getContext());
//social_id, identity_id, social_profileid
        String URL = network_url.Base_Url + network_url.Get_IDENTITES_Friend +"user_id="+keyuserId;
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

                                    String IdentityID = jsonObject1.getString("identity_id");
                                    String IdentityUserFName = jsonObject1.getString("identity_username");
                                    String IdentityUserLName = jsonObject1.getString("identity_username");

                                    data = new User(IdentityID,IdentityUserFName,IdentityUserLName);
                                    listData.add(data);
                                }
                                progressBar.setVisibility(View.GONE);
                                setAdapterData(listData);

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
    public void setData() {
        alphabetItem.add("A");
        alphabetItem.add("B");
        alphabetItem.add("C");
        alphabetItem.add("D");
        alphabetItem.add("E");
        alphabetItem.add("F");
        alphabetItem.add("G");
        alphabetItem.add("H");
        alphabetItem.add("I");
        alphabetItem.add("J");
        alphabetItem.add("K");
        alphabetItem.add("L");
        alphabetItem.add("M");
        alphabetItem.add("N");
        alphabetItem.add("O");
        alphabetItem.add("P");
        alphabetItem.add("Q");
        alphabetItem.add("R");
        alphabetItem.add("S");
        alphabetItem.add("T");
        alphabetItem.add("U");
        alphabetItem.add("V");
        alphabetItem.add("W");
        alphabetItem.add("X");
        alphabetItem.add("Y");
        alphabetItem.add("Z");
        alphabetItem.add("#");
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        }
        rv.setLayoutManager(linearLayoutManager);
    }

    public void setRecyclerView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alphaAdapter = new AlphaAdapter(alphabetItem);
        }
        RecyclerView.LayoutManager mLayoutManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        }
        alphaRecycler.setLayoutManager(mLayoutManager);
        alphaRecycler.setAdapter(alphaAdapter);

    }
}
