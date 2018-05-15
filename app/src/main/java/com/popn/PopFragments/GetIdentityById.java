package com.popn.PopFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
import com.bumptech.glide.Glide;
import com.popn.Adapters.CustomAdapter;
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.SocialIdModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Android-Dev2 on 3/28/2018.
 */

public class GetIdentityById extends Fragment implements View.OnClickListener {
    private TextView name, age, city, add,PersonName,identityName1;
    String URL, responsemessage, message;
    String identityId, hexcode,identityID;
    String identityUserId;
    ImageView qrCode,back;
    static String commaSeparatedRGBPattern = "^(\\d{3}),(\\d{3}),(\\d{3})$";
    static final int HEXLENGTH = 8;
    Network_url network_url;
    LinearLayout linearLayout;
    List<SocialIdModel> ImagesList =new ArrayList<>();
    SocialIdModel socialIdModel,socialIdModel1;
    List<SocialIdModel> osImages =new ArrayList<>();

    GridView gridView;
    CircleImageView circleImageView;
    public GetIdentityById(){
    }


    public GetIdentityById(String value){
        this.identityId = value;
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
        View view = inflater.inflate(R.layout.get_identity_by_id, container, false);

        name = (TextView) view.findViewById(R.id.name);
        age = (TextView) view.findViewById(R.id.age);
        city = (TextView) view.findViewById(R.id.city);
        add = (TextView) view.findViewById(R.id.add);
        PersonName = (TextView) view.findViewById(R.id.userName);
        identityName1 = (TextView) view.findViewById(R.id.ID);
        gridView = (GridView) view.findViewById(R.id.customgrid);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout1);
        circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
        qrCode = (ImageView) view.findViewById(R.id.qrCode);
        back = (ImageView) view.findViewById(R.id.back_job);
        network_url =new Network_url();
        add.setOnClickListener(this);
        back.setOnClickListener(this);
        hitApi();

        return view;
    }

    private void hitApi() {
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        URL = "http://vertosys.com/popnapp/getIdentityById.php" + "?identity_id=" +identityId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                      //  progressBar.setVisibility(View.GONE);

                        try {
                            responsemessage = response.getString("success");
                            message = response.getString("message");
                            JSONArray jsonObj = new JSONArray(response.getString("data"));
                            JSONObject data = jsonObj.getJSONObject(0);
                            identityID = data.getString("identity_id");
                            String identityName = data.getString("identity_name");
                            String userName = data.getString("identity_username");
                            String userAge = data.getString("identity_userage");
                            String userCity = data.getString("identity_city");
                            identityUserId = data.getString("identity_userid");
                            String identityColor = data.getString("identity_color");
                            String userImage = data.getString("identity_userimage");
                            if (responsemessage.equals("true")) {
                                LayerDrawable shape = null;
                                int hexCode = switchColor(identityColor);
                                shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                                GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);
                                gradientDrawable.setStroke(10, ContextCompat.getColor(getContext().getApplicationContext(), hexCode));
                                linearLayout.setBackground(shape);
                                name.setText(userName);
                                name.setTextColor(getResources().getColor(hexCode));
                                identityName1.setText(identityName);
                                identityName1.setTextColor(getResources().getColor(hexCode));
                                age.setText(userAge + " years age");
                                age.setTextColor(getResources().getColor(hexCode));
                                city.setText(userCity);
                                city.setTextColor(getResources().getColor(hexCode));
                                PersonName.setText(userName);
                                PersonName.setTextColor(getResources().getColor(hexCode));

                                Glide.with(getContext()).load("http://vertosys.com/popnapp/code/" + identityId + ".png").into(qrCode);
                                Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/" + userImage).into(circleImageView);


                            }
                            getSocialId();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        queue.add(jsonObjectRequest);
    }
    public int switchColor(String IdentityColor) {
        int setColor = R.color.colorTextGray;

        switch (IdentityColor) {
            case "229,229,229":
                // hexCode = "#868383";
                setColor = R.color.colorTextGray;
                break;
            case "90,200,250":
                // hexCode = "#5CC8FA";
                setColor = R.color.colorTextBlue;
                break;
            case "255,149,0":
                //  hexCode = "#FF9501";
                setColor = R.color.colorTextYellow;
                break;
            case "88,86,214":
                // hexCode = "#3F51B5";
                setColor = R.color.colorPrimary;
                break;
            case "255,45,85":
                // hexCode = "#ff2d55";
                setColor = R.color.color_preloader_end;
                break;

        }
        return setColor;

    }
    private void getSocialId() {
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        URL = network_url.Base_Url + network_url.Get_Identity_Social_By_Id+ network_url.IdentityId+ "=" + identityID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);

                        try {
                            responsemessage = response.getString("success");
                            message = response.getString("message");
                            JSONArray jsonObj = new JSONArray(response.getString("data"));

                            for(int i=0;i<jsonObj.length();i++) {
                                JSONObject data = jsonObj.getJSONObject(i);
                                String socialID = data.getString("social_id");
                                String SocialProfileID = data.getString("social_profileid");

                                socialIdModel=new SocialIdModel(socialID,SocialProfileID);
                                osImages.add(socialIdModel);
                            }
                            getSocialImages(osImages);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        queue.add(jsonObjectRequest);
    }
    public void getSocialImages(List<SocialIdModel> socialIdModels){
        int i;

        for(i=0;i<socialIdModels.size();i++){

            switch (socialIdModels.get(i).getSocialId()){
                case ("1"):
                    socialIdModel1=new SocialIdModel("phone",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("2"):
                    socialIdModel1=new SocialIdModel("email_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("3"):
                    socialIdModel1=new SocialIdModel("canvas_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("4"):
                    socialIdModel1=new SocialIdModel("facebook_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("5"):
                    socialIdModel1=new SocialIdModel("insta_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("6"):
                    socialIdModel1=new SocialIdModel("snapchat_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("7"):
                    socialIdModel1=new SocialIdModel("whatsapp_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("8"):
                    socialIdModel1=new SocialIdModel("tiwtter_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("9"):
                    socialIdModel1=new SocialIdModel("linkedin_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("10"):
                    socialIdModel1=new SocialIdModel("sms_32",socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;

            }
        }
        gridView.setAdapter(new CustomAdapter(getContext().getApplicationContext(), ImagesList,null));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void addFriends(){
        SessionManager sessionManager =new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        String keyuserId = user.get("userID");
        RequestQueue queue = Volley.newRequestQueue(getContext());
        URL = "http://vertosys.com/popnapp/addfriend.php?"+"user_id=" +keyuserId + "&friend_id=" + identityUserId+ "&identity_id="+identityID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);

                        try {
                            responsemessage = response.getString("success");
                        //    message = response.getString("message");
                            if (responsemessage.equals("true")) {
                                Intent intent =new Intent(getContext(),IdentiesMainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getContext(), "Already friend", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getContext(),IdentiesMainActivity.class);
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.add):
                addFriends();
                break;
            case (R.id.back_job):
                Intent intent =new Intent(getContext().getApplicationContext(), IdentiesMainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
