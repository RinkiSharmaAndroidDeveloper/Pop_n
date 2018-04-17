package com.popn.PopFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.popn.Adapters.ConnectionAdapter;
import com.popn.AsyncResult;
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.ConnectionModel;
import com.popn.PopModels.IdentityInformationModel;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vs1 on 2/13/2018.
 */

public class ConnectionsFragment extends Fragment {
    List<ConnectionModel> connectionModelList;
    ConnectionModel connectionModel;
    RecyclerView recyclerView;
    ConnectionAdapter mAdapter;
    TextView imageView;
    AsyncResult<ConnectionModel> asyncResult_selectedCategory;
    Network_url network_url;
    String identityId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    public ConnectionsFragment newInstance(AsyncResult<ConnectionModel> asyncResult_selectedCategory,String identityId) {
        ConnectionsFragment fragment = new ConnectionsFragment();
        Bundle args = new Bundle();
        this.asyncResult_selectedCategory=asyncResult_selectedCategory;
        this.identityId=identityId;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_connection, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        imageView = (TextView) view.findViewById(R.id.back_text);
        connectionModelList=new ArrayList<>();
        network_url = new Network_url();
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getActivity(), IdentiesMainActivity.class);
                startActivity(i);
            }
        });

        getData();
        setRecyclerView();
        return view;
    }

   public void setRecyclerView(){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           mAdapter = new ConnectionAdapter(connectionModelList,getContext().getApplicationContext(),asyncResult_clickConnection);
       }
       RecyclerView.LayoutManager mLayoutManager = null;
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
       }
       recyclerView.setLayoutManager(mLayoutManager);
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.setAdapter(mAdapter);

   }
    //Upload second Edit Information fragment
    AsyncResult<ConnectionModel > asyncResult_clickConnection = new AsyncResult<ConnectionModel>()  {
        @Override
        public void success(ConnectionModel connectionModel) {

            asyncResult_selectedCategory.success(connectionModel);
        }
        @Override
        public void error(String error) {

        }
    };

    public void getData(){
        connectionModel =new ConnectionModel("Phone Number","phone","1",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("Email","email_32","2",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("Personal URL","canvas_32","3",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("Facebook","facebook_32","4",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("Instagram","insta_32","5",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("Snapchat","snapchat_32","6",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("Whatsapp","whatsapp_32","7",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("Twitter","tiwtter_32","8",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("LinkedIn","linkedin_32","9",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("WeChat","sms_32","10",identityId);
        connectionModelList.add(connectionModel);
        connectionModel =new ConnectionModel("WeChat","sms_32","11",identityId);
        connectionModelList.add(connectionModel);


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
