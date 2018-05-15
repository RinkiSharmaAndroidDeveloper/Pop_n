package com.popn.PopActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.google.android.flexbox.FlexboxLayout;
import com.popn.Adapters.CustomAdapter;
import com.popn.PopModels.KeywordModel;
import com.popn.PopModels.PersonalBroadcastModel;
import com.popn.PopModels.SocialIdModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BroadcastDetailActivity extends AppCompatActivity {
     CircleImageView circleImageView;
     TextView network_name,personName,personAge,location,back;
    GridView gridView;
    ImageView qr_image;
    Network_url network_url;
    String identityID,identityUserId;
    PersonalBroadcastModel personalBroadcastModel;
    SocialIdModel socialIdModel,socialIdModel1;
    List<SocialIdModel> ImagesList =new ArrayList<>();
    List<SocialIdModel> osImages =new ArrayList<>();
    LinearLayout linearLayout;
    List<String> keyList1 =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_detail);

        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        network_name = (TextView) findViewById(R.id.social);
        personName = (TextView) findViewById(R.id.mattie);
        personAge = (TextView) findViewById(R.id.person_age);
        location = (TextView) findViewById(R.id.discountNewRate);
        back = (TextView) findViewById(R.id.back);
        gridView = (GridView) findViewById(R.id.grid1);
        qr_image = (ImageView) findViewById(R.id.qr_image);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        Intent i = getIntent();
        personalBroadcastModel = (PersonalBroadcastModel) i.getParcelableExtra("broadcastModal");
        network_url =new Network_url();
        getIdentityById();

        List<String> keyList2= splitKeys(personalBroadcastModel.getKeyword1());
        inflatelayout(keyList2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public List<String> splitKeys(String keyList){
        String[] arrSplit=null;
        if(keyList1.size()>0){
            keyList1.clear();
        }
        arrSplit = keyList.split(",");
        for (int i=0; i < arrSplit.length; i++)
        {
            keyList1.add(arrSplit[i]);
        }
        return keyList1;
    }
    private void inflatelayout(List<String> keywordModelList1) {


        FlexboxLayout container = (FlexboxLayout) findViewById(R.id.v_container);

        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(10, 10, 10, 10);


        for (int i = 0; i < keywordModelList1.size(); i++) {
            final TextView tv = new TextView(getApplicationContext());
            tv.setText(keywordModelList1.get(i));
            tv.setHeight(100);
            tv.setTextSize(18.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#868383"));

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


    private void getIdentityById() {
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(this);
        }
      String URL = "http://vertosys.com/popnapp/getIdentityById.php" + "?identity_id=" +personalBroadcastModel.getKeyword2();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);

                        try {
                        String  responsemessage = response.getString("success");
                           String message = response.getString("message");
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
                                shape = (LayerDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_shadow);
                                GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);
                                gradientDrawable.setStroke(10, ContextCompat.getColor(getApplicationContext(), hexCode));
                                linearLayout.setBackground(shape);
                                personName.setText(userName);
                                personName.setTextColor(getResources().getColor(hexCode));
                                network_name.setText(identityName);
                                network_name.setTextColor(getResources().getColor(hexCode));
                                personAge.setText(userAge + " years age");
                                personAge.setTextColor(getResources().getColor(hexCode));
                                location.setText(userCity);
                                location.setTextColor(getResources().getColor(hexCode));

                                Glide.with(getApplicationContext()).load("http://vertosys.com/popnapp/code/" + identityID + ".png").into(qr_image);
                                Glide.with(getApplicationContext().getApplicationContext()).load("http://vertosys.com/popnapp/" + userImage).into(circleImageView);


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
    private void getSocialId() {
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(this);
        }
       String URL = network_url.Base_Url + network_url.Get_Identity_Social_By_Id+ network_url.IdentityId+ "=" + identityID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);

                        try {
                          String  responsemessage = response.getString("success");
                           String  message = response.getString("message");
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
        gridView.setAdapter(new CustomAdapter(this, ImagesList,null));
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
}
