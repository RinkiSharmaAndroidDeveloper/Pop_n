package com.popn.PopFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.ConnectionModel;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddConnectionFragment extends Fragment implements View.OnClickListener{
    ImageView imageView;
    AsyncResult<ConnectionModel> asyncResult_selectedCategory;
    ConnectionModel connectionModel;
    EditText userInfo;
    TextView back,done,nameConn;
    String userInformation=null;
    Network_url network_url;

    public AddConnectionFragment newInstance(ConnectionModel connectionModel) {
        AddConnectionFragment fragment = new AddConnectionFragment();
        Bundle args = new Bundle();
        this.connectionModel=connectionModel;
        return fragment;
    }
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
        View view = inflater.inflate(R.layout.fragment_add_connection, container, false);

        userInfo =(EditText)view.findViewById(R.id.userInformation);
        back =(TextView) view.findViewById(R.id.back);
        done =(TextView) view.findViewById(R.id.done);
        nameConn =(TextView) view.findViewById(R.id.TVLogin);
        imageView =(ImageView) view.findViewById(R.id.imageView);
        network_url= new Network_url();
        String uri = "@drawable/"+connectionModel.getImageUrl();  // where myresource (without the extension) is the file
        int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());
        Drawable res = getContext().getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);
        nameConn.setText(connectionModel.getTitle());
        back.setOnClickListener(this);
        done.setOnClickListener(this);
        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.done):
                userInformation= userInfo.getText().toString();
                if(userInformation!=null){
                    getAuthenticateLogin();
                }else{
                    userInfo.setError("Please enter valid information");
                }
                break;
            case (R.id.back):
                Intent intent =new Intent(getContext(), IdentiesMainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void getAuthenticateLogin()
    {
        SessionManager sessionManager =new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        String keyuserId = user.get("userID");
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(getContext());
//social_id, identity_id, social_profileid
            String URL = network_url.Base_Url + network_url.Social_Id +"social_id="+connectionModel.getSocialID()+"&identity_id="+ connectionModel.getIdentityId()
                    +"&social_profileid="+userInformation;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                            Log.e("Response", response.toString());
                            String responsemessage = null;
                            try {
                                String resposne_message = response.getString("success");
                                String res = response.getString("message");

                                if(resposne_message.equals("true")) {
                                    Intent i = new Intent(getActivity(), IdentiesMainActivity.class);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
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
}
