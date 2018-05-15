package com.popn.PopFragments;

import android.app.ProgressDialog;
import android.content.Context;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.popn.Adapters.CustomAdapter;
import com.popn.Adapters.IdentityCustomeAdapter;
import com.popn.AsyncResult;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.IdentityInformationModel;
import com.popn.PopModels.SocialIdModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class IdentityFragment extends Fragment implements View.OnClickListener {
    public static String hexCode = "#868383";
    LinearLayout linearLayout1, linearLayout2, linearLayout3;
    RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3;
    FrameLayout mainLayout;
    private OnFragmentInteractionListener mListener;
    AsyncResult<IdentityInformationModel> asyncResult_selectedCategory;
    Context context;
    TextView textView3, textView1, textView2, network_name, personName, personAge, location;
    TextView network_name1, personName1, personAge1, location1, network_name2, personName2, personAge2, location2;
    ;
    ImageView imageView, qr_image, qr_image1, qr_image2;
    GridView gridView, gridView1, gridView2;
    ProgressDialog progressDialog;
    Network_url network_url;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String userId;
    CircleImageView circleImageView, circleImageView1, circleImageView2;
    IdentityInformationModel identityInformationModel;
    List<IdentityInformationModel> identityInformationModels;
    static String commaSeparatedRGBPattern = "^(\\d{3}),(\\d{3}),(\\d{3})$";
    String qr_code, IdentityUserId;
    List<SocialIdModel> ImagesList = new ArrayList<>();
    SocialIdModel socialIdModel;
    List<SocialIdModel> osImages = new ArrayList<>();
    SocialIdModel socialIdModel1;
    String statusCode;
    ProgressBar progressBar;
    CardView cardView;
    String isBroadcast;
    AsyncResult<String> asyncResult_selectedIdentityId;
    public IdentityFragment() {
        // Required empty public constructor
    }

    public IdentityFragment(String statusCode) {
        this.statusCode = statusCode;
    }

    // TODO: Rename and change types and number of parameters
    public IdentityFragment newInstance(AsyncResult<IdentityInformationModel> asyncResult_selectedCategory,String isBroadcast,AsyncResult<String> asyncResult_selectedIdentityId) {
        IdentityFragment fragment = new IdentityFragment();
        Bundle args = new Bundle();
        this.asyncResult_selectedCategory = asyncResult_selectedCategory;
        this.isBroadcast = isBroadcast;
        this.asyncResult_selectedIdentityId = asyncResult_selectedIdentityId;
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

        View view = inflater.inflate(R.layout.fragment_identity, container, false);//Inflate Layout
        linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) view.findViewById(R.id.linearLayout3);
        relativeLayout1 = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        relativeLayout2 = (RelativeLayout) view.findViewById(R.id.relativeLayout1);
        relativeLayout3 = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
        textView3 = (TextView) view.findViewById(R.id.edit3);
        textView1 = (TextView) view.findViewById(R.id.edit1);
        circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
        textView2 = (TextView) view.findViewById(R.id.edit2);
        network_name = (TextView) view.findViewById(R.id.social);
        personName = (TextView) view.findViewById(R.id.mattie);
        personAge = (TextView) view.findViewById(R.id.person_age);
        location = (TextView) view.findViewById(R.id.discountNewRate);
        gridView = (GridView) view.findViewById(R.id.grid1);
        qr_image = (ImageView) view.findViewById(R.id.qr_image);

        circleImageView1 = (CircleImageView) view.findViewById(R.id.profile_image2);
        network_name1 = (TextView) view.findViewById(R.id.social2);
        personName1 = (TextView) view.findViewById(R.id.mattie2);
        personAge1 = (TextView) view.findViewById(R.id.discountDescription2);
        location1 = (TextView) view.findViewById(R.id.discountNewRate2);
        gridView1 = (GridView) view.findViewById(R.id.grid2);
        qr_image1 = (ImageView) view.findViewById(R.id.qr_image2);

        circleImageView2 = (CircleImageView) view.findViewById(R.id.profile_image3);
        network_name2 = (TextView) view.findViewById(R.id.social3);
        personName2 = (TextView) view.findViewById(R.id.mattie3);
        personAge2 = (TextView) view.findViewById(R.id.discountDescription3);
        location2 = (TextView) view.findViewById(R.id.discountNewRate3);
        gridView2 = (GridView) view.findViewById(R.id.grid3);
        qr_image2 = (ImageView) view.findViewById(R.id.qr_image3);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mainLayout = (FrameLayout) view.findViewById(R.id.activity_identities);
        identityInformationModels = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sessionManager = new SessionManager(getContext().getApplicationContext());
        }
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        userId = user.get("userID");
        qr_code = user.get("Qr_Image");
        if(isBroadcast!=null && isBroadcast.equals("1")){
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
        }
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLayout.removeView(linearLayout1);
                mainLayout.removeView(linearLayout2);
                mainLayout.removeView(linearLayout3);

                FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) linearLayout1.getLayoutParams();
                params1.setMargins(0, 290, 0, 0);
                linearLayout1.setLayoutParams(params1);
                if(asyncResult_selectedIdentityId!=null) {
                    asyncResult_selectedIdentityId.success(identityInformationModels.get(0).getIdentityId());
                }

                FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) linearLayout2.getLayoutParams();
                params2.setMargins(0, 40, 0, 0);
                linearLayout2.setLayoutParams(params2);

                FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) linearLayout3.getLayoutParams();
                params3.setMargins(0, 170, 0, 0);
                linearLayout3.setLayoutParams(params3);

                //frameLayout.addView(linearLayout1);
                mainLayout.addView(linearLayout2, 0);
                mainLayout.addView(linearLayout3, 1);
                mainLayout.addView(linearLayout1, 2);
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLayout.removeView(linearLayout1);
                mainLayout.removeView(linearLayout2);
                mainLayout.removeView(linearLayout3);

                FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) linearLayout1.getLayoutParams();
                params1.setMargins(0, 40, 0, 0);
                linearLayout1.setLayoutParams(params1);
                 if(asyncResult_selectedIdentityId!=null) {
                     asyncResult_selectedIdentityId.success(identityInformationModels.get(1).getIdentityId());
                 }
                FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) linearLayout2.getLayoutParams();
                params2.setMargins(0, 290, 0, 0);
                linearLayout2.setLayoutParams(params2);

                FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) linearLayout3.getLayoutParams();
                params3.setMargins(0, 170, 0, 0);
                linearLayout3.setLayoutParams(params3);

                //frameLayout.addView(linearLayout2);
                mainLayout.addView(linearLayout1, 0);
                mainLayout.addView(linearLayout3, 1);
                mainLayout.addView(linearLayout2, 2);

            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLayout.removeView(linearLayout1);
                mainLayout.removeView(linearLayout2);
                mainLayout.removeView(linearLayout3);

                FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) linearLayout1.getLayoutParams();
                params1.setMargins(0, 40, 0, 0);
                linearLayout1.setLayoutParams(params1);
                if(asyncResult_selectedIdentityId!=null) {
                    asyncResult_selectedIdentityId.success(identityInformationModels.get(2).getIdentityId());
                }
                FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) linearLayout2.getLayoutParams();
                params2.setMargins(0, 170, 0, 0);
                linearLayout2.setLayoutParams(params2);

                FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) linearLayout3.getLayoutParams();
                params3.setMargins(0, 290, 0, 0);
                linearLayout3.setLayoutParams(params3);

                //frameLayout.addView(linearLayout3);

                mainLayout.addView(linearLayout1, 0);
                mainLayout.addView(linearLayout2, 1);
                mainLayout.addView(linearLayout3, 2);

            }
        });
        if (statusCode.contains("1")) {
            progressBar.setVisibility(View.VISIBLE);
            getUserInfo();
        }
        if (statusCode.contains("2")) {
            progressBar.setVisibility(View.VISIBLE);
            getUserLoginInfo();
        }


        return view;//return view
    }

    public void setDataToCard(final List<IdentityInformationModel> identityInformationModels) {
        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.createLoginSession(identityInformationModels.get(0).getIdentityUserId(), "identity_userID");
        LayerDrawable shape = null;
        if (identityInformationModels.size() == 2) {
            linearLayout2.setVisibility(View.VISIBLE);
        } else if (identityInformationModels.size() >= 3) {
            linearLayout2.setVisibility(View.VISIBLE);
            linearLayout3.setVisibility(View.VISIBLE);
        }
        //  String hexcode=convertRGBToHex(identityInformationModel.getIdentityColor());
        //String hexColor = String.format( "#%02x%02x%02x", r, g, b );
        for (int i = 0; i < identityInformationModels.size(); i++) {
            if (i == 0) {
                int hexCode = switchColor(identityInformationModels.get(i).getIdentityColor());
                //  int color1 =  Color.parseColor(hexCode);
                shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                gradientDrawable.setStroke(10, ContextCompat.getColor(getContext().getApplicationContext(), hexCode));

                relativeLayout1.setBackground(shape);
              /*  if(isBroadcast!=null && isBroadcast.equals("1")) {
                    relativeLayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            asyncResult_selectedIdentityId.success(identityInformationModels.get(0).getIdentityId());
                        }
                    });
                }*/
                network_name.setText(identityInformationModels.get(i).getIdentityName());
                network_name.setTextColor(getResources().getColor(hexCode));
                personName.setText(identityInformationModels.get(i).getIdentityUserName());
                personName.setTextColor(getResources().getColor(hexCode));
                personAge.setText(identityInformationModels.get(i).getIdentityUserAge());
                personAge.setTextColor(getResources().getColor(hexCode));
                location.setText(identityInformationModels.get(i).getIdentityCity());
                location.setTextColor(getResources().getColor(hexCode));
                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        asyncResult_selectedCategory.success(identityInformationModels.get(0));
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/code/" + identityInformationModels.get(i).getIdentityId() + ".png").into(qr_image);
                    Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/" + identityInformationModels.get(i).getIdentityUserImage()).into(circleImageView);
                }
            } else if (i == 1) {

                int hexCode = switchColor(identityInformationModels.get(i).getIdentityColor());

                //   int color = Color.parseColor(hexCode);
                network_name1.setText(identityInformationModels.get(i).getIdentityName());
                network_name1.setTextColor(getResources().getColor(hexCode));
                personName1.setText(identityInformationModels.get(i).getIdentityUserName());
                personName1.setTextColor(getResources().getColor(hexCode));
                personAge1.setText(identityInformationModels.get(i).getIdentityUserAge());
                personAge1.setTextColor(getResources().getColor(hexCode));
                location1.setText(identityInformationModels.get(i).getIdentityCity());
                location1.setTextColor(getResources().getColor(hexCode));

                shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                gradientDrawable.setStroke(10, ContextCompat.getColor(getContext().getApplicationContext(), hexCode));

                relativeLayout2.setBackground(shape);
              /*  if(isBroadcast!=null && isBroadcast.equals("1")) {
                    relativeLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            asyncResult_selectedIdentityId.success(identityInformationModels.get(1).getIdentityId());
                        }
                    });
                }*/
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        asyncResult_selectedCategory.success(identityInformationModels.get(1));
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/code/" + identityInformationModels.get(i).getIdentityId() + ".png").into(qr_image1);
                    Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/" + identityInformationModels.get(i).getIdentityUserImage()).into(circleImageView1);
                }
            } else if (i == 2) {
                int hexCode = switchColor(identityInformationModels.get(i).getIdentityColor());

                // int color = Color.parseColor(hexCode);
                network_name2.setText(identityInformationModels.get(i).getIdentityName());
                network_name2.setTextColor(getResources().getColor(hexCode));
                personName2.setText(identityInformationModels.get(i).getIdentityUserName());
                personName2.setTextColor(getResources().getColor(hexCode));
                personAge2.setText(identityInformationModels.get(i).getIdentityUserAge());
                personAge2.setTextColor(getResources().getColor(hexCode));
                location2.setText(identityInformationModels.get(i).getIdentityCity());
                location2.setTextColor(getResources().getColor(hexCode));
                shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                gradientDrawable.setStroke(10, ContextCompat.getColor(getContext().getApplicationContext(), hexCode));
                //shape.setTint(color);
                relativeLayout3.setBackground(shape);
            /*    if(isBroadcast!=null && isBroadcast.equals("1")) {
                    relativeLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            asyncResult_selectedIdentityId.success(identityInformationModels.get(2).getIdentityId());
                        }
                    });
                }*/
                textView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        asyncResult_selectedCategory.success(identityInformationModels.get(2));
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/code/" + identityInformationModels.get(i).getIdentityId() + ".png").into(qr_image2);
                    Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/" + identityInformationModels.get(i).getIdentityUserImage()).into(circleImageView2);
                }
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                gradientDrawable.setStroke(10, ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorTextGray));

                linearLayout1.setBackground(shape);
                linearLayout2.setBackground(shape);
                linearLayout3.setBackground(shape);
            }
        }
    }


    public void getUserInfo() {

        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        network_url = new Network_url();
        String URL = network_url.Base_Url + network_url.GetUserDetail + "identity_id=" + userId;

        //     progressDialog.show();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progressBar.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            //[{"identity_id":"17","identity_name":"ID","identity_username":"Abhishek","identity_userage":"29",
                            // "identity_city":"Cupertino","identity_userid":"7","identity_color":"299,299,299\"","identity_userimage":""}]}
                            Log.e("Data23", response.getString("message"));

                            JSONArray jsonObj = new JSONArray(response.getString("data"));
                            for (int i = 0; i < jsonObj.length(); i++) {
                                JSONObject jsonObject1 = jsonObj.getJSONObject(i);
                                // Json Data set to ServiceJob
                                String IdentityId = jsonObject1.getString("identity_id");
                                String IdentityName = jsonObject1.getString("identity_name");
                                String IdentityUserName = jsonObject1.getString("identity_username");
                                String IdentityUserAge = jsonObject1.getString("identity_userage");
                                String IdentityCity = jsonObject1.getString("identity_city");
                                IdentityUserId = jsonObject1.getString("identity_userid");
                                String IdentityColor = jsonObject1.getString("identity_color");
                                String IdentityUserImage = jsonObject1.getString("identity_userimage");

                                identityInformationModel = new IdentityInformationModel(IdentityId, IdentityName, IdentityUserName, IdentityUserAge, IdentityCity,
                                        IdentityUserId, IdentityColor, IdentityUserImage);
                                identityInformationModels.add(identityInformationModel);
                            }
                            if(identityInformationModels!=null &&identityInformationModels.size()>0){
                            setDataToCard(identityInformationModels);
                            for (int i = 0; i < identityInformationModels.size(); i++) {
                                getSocialId(identityInformationModels.get(i), i);
                            }
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


    public void getUserLoginInfo() {

        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        network_url = new Network_url();

        String URL = network_url.Base_Url + network_url.Get_Identity + "identity_userid=" + userId;

        //     progressDialog.show();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progressBar.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            //[{"identity_id":"17","identity_name":"ID","identity_username":"Abhishek","identity_userage":"29",
                            // "identity_city":"Cupertino","identity_userid":"7","identity_color":"299,299,299\"","identity_userimage":""}]}
                            Log.e("Data23", response.getString("message"));

                            JSONArray jsonObj = new JSONArray(response.getString("data"));

                            for (int i = 0; i < jsonObj.length(); i++) {
                                JSONObject jsonObject1 = jsonObj.getJSONObject(i);


                                // Json Data set to ServiceJob
                                String IdentityId = jsonObject1.getString("identity_id");
                                String IdentityName = jsonObject1.getString("identity_name");
                                String IdentityUserName = jsonObject1.getString("identity_username");
                                String IdentityUserAge = jsonObject1.getString("identity_userage");
                                String IdentityCity = jsonObject1.getString("identity_city");
                                String IdentityUserId = jsonObject1.getString("identity_userid");
                                String IdentityColor = jsonObject1.getString("identity_color");
                                String IdentityUserImage = jsonObject1.getString("identity_userimage");

                                identityInformationModel = new IdentityInformationModel(IdentityId, IdentityName, IdentityUserName, IdentityUserAge, IdentityCity,
                                        IdentityUserId, IdentityColor, IdentityUserImage);
                                identityInformationModels.add(identityInformationModel);
                            }
                            if(identityInformationModels!=null && identityInformationModels.size()>0) {
                                setDataToCard(identityInformationModels);
                                for (int i = 0; i < identityInformationModels.size(); i++) {
                                    getSocialId(identityInformationModels.get(i), i);
                                }
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

    private void getSocialId(IdentityInformationModel identityInformationModel, final int i) {
        if (osImages.size() >= 0) {
            osImages.clear();
        }
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        String URL = network_url.Base_Url + network_url.Get_Identity_Social_By_Id + network_url.IdentityId + "=" + identityInformationModel.getIdentityId();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);
                        if (osImages.size() >= 0) {
                            osImages.clear();
                        }
                        try {
                            String responsemessage = response.getString("success");
                            String message = response.getString("message");
                            JSONArray jsonObj = new JSONArray(response.getString("data"));

                            for (int i = 0; i < jsonObj.length(); i++) {
                                JSONObject data = jsonObj.getJSONObject(i);
                                String socialID = data.getString("social_id");
                                String SocialProfileID = data.getString("social_profileid");

                                socialIdModel = new SocialIdModel(socialID, SocialProfileID);
                                osImages.add(socialIdModel);
                            }
                            getSocialImages(osImages, i);

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

    public void getSocialImages(List<SocialIdModel> socialIdModels, int j) {
        int i;
        if (ImagesList.size() >= 0) {
            ImagesList.clear();
        }

        for (i = 0; i < socialIdModels.size(); i++) {

            switch (socialIdModels.get(i).getSocialId()) {
                case ("1"):
                    socialIdModel1 = new SocialIdModel("phone", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("2"):
                    socialIdModel1 = new SocialIdModel("email_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("3"):
                    socialIdModel1 = new SocialIdModel("canvas_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("4"):
                    socialIdModel1 = new SocialIdModel("facebook_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("5"):
                    socialIdModel1 = new SocialIdModel("insta_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("6"):
                    socialIdModel1 = new SocialIdModel("snapchat_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("7"):
                    socialIdModel1 = new SocialIdModel("whatsapp_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("8"):
                    socialIdModel1 = new SocialIdModel("tiwtter_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("9"):
                    socialIdModel1 = new SocialIdModel("linkedin_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;
                case ("10"):
                    socialIdModel1 = new SocialIdModel("sms_32", socialIdModels.get(i).getSocialUrl());
                    ImagesList.add(socialIdModel1);
                    break;

            }
        }

        if (j == 0) {
            List<SocialIdModel> informationModels = new ArrayList<>();
            informationModels.addAll(ImagesList);
            gridView.setAdapter(new IdentityCustomeAdapter(getContext().getApplicationContext(), informationModels, false, null,null));
        } else if (j == 1) {
            List<SocialIdModel> informationModels = new ArrayList<>();
            informationModels.addAll(ImagesList);
            gridView1.setAdapter(new IdentityCustomeAdapter(getContext().getApplicationContext(), informationModels, false, null,null));
        } else if (j == 2) {
            List<SocialIdModel> informationModels = new ArrayList<>();
            informationModels.addAll(ImagesList);
            gridView2.setAdapter(new IdentityCustomeAdapter(getContext().getApplicationContext(), informationModels, false, null,null));
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
