package com.popn.PopFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.popn.AsyncResult;
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.ConnectionModel;
import com.popn.PopModels.SocialIdModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vs1 on 4/2/2018.
 */

public class Network_detail_fragment extends Fragment implements View.OnClickListener {
    private static final int PICK_CONTACT = 85500;
    private TextView name, age, city, delete, PersonName,back,IdentitName;
    String URL, responsemessage, message;
    String identityId, hexcode;
    ImageView qrCode;
    CircleImageView circleImageView;
    LinearLayout linearLayout;
    static String commaSeparatedRGBPattern = "^(\\d{3}),(\\d{3}),(\\d{3})$";
    static final int HEXLENGTH = 8;
    GridView gridView;
    String userId;
    String identityUserId;
    Network_url network_url;
    List<SocialIdModel> osImages =new ArrayList<>();
    List<SocialIdModel> ImagesList =new ArrayList<>();
    SocialIdModel socialIdModel;
    String identityID;
    SocialIdModel socialIdModel1;

    public Network_detail_fragment() {

    }

    public Network_detail_fragment newInstance(String userId) {
        Network_detail_fragment fragment = new Network_detail_fragment();
        Bundle args = new Bundle();
        this.userId=userId;
        return fragment;
    }
    public Network_detail_fragment(String value) {
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
        View view = inflater.inflate(R.layout.fragment_identities_details, container, false);

        PersonName = (TextView) view.findViewById(R.id.name1);
        age = (TextView) view.findViewById(R.id.age1);
        city = (TextView) view.findViewById(R.id.city1);
        delete = (TextView) view.findViewById(R.id.delete);
        IdentitName = (TextView) view.findViewById(R.id.identity);
       // PersonName = (TextView) view.findViewById(R.id.userName);
        circleImageView =(CircleImageView)view.findViewById(R.id.profile_image);
       // qrCode = (ImageView) view.findViewById(R.id.qrCode);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        gridView = (GridView) view.findViewById(R.id.customgrid);
        back = (TextView) view.findViewById(R.id.back);
        network_url=new Network_url();
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
         getUserDetails();


        return view;
    }

    AsyncResult<String> asyncResultSocialId = new AsyncResult<String>() {
        @Override
        public void success(String s) {
            switch (s){
                case ("phone"):
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                    phoneIntent.setData(Uri.parse("tel:1122334455"));
                    startActivity(phoneIntent);
                    break;
                case ("email_32"):
                    //Toast.makeText(getContext(), "you clicked email", Toast.LENGTH_SHORT).show();
                    String recepientEmail = ""; // either set to destination email or leave empty
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:" + recepientEmail));
                    startActivity(emailIntent);
                    break;
                case ("canvas_32"):
                    //Toast.makeText(getContext(), "you clicked canvas", Toast.LENGTH_SHORT).show();
                    String url = "https://www.google.co.in/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    break;
                case ("facebook_32"):
                    String Fb_id = "fb://profile";
                    String uri = "https://www.facebook.com/" + Fb_id;
                    //Toast.makeText(getContext(), "you clicked facebook", Toast.LENGTH_SHORT).show();
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(facebookIntent);
                    break;
                case ("insta_32"):
                    Intent instaintent;
                    //Toast.makeText(getContext(), "you clicked instagram", Toast.LENGTH_SHORT).show();
                    String scheme = "http://instagram.com/_p/PICTURE";
                    String path = "https://instagram.com/USER";
                    String nomPackageInfo ="com.instagram.android";
                    try {
                        getActivity().getPackageManager().getPackageInfo(nomPackageInfo, 0);
                        instaintent = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
                    } catch (Exception e) {
                        instaintent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                    }
                    startActivity(instaintent);
                    break;
                case ("snapchat_32"):
                    //Toast.makeText(getContext(), "you clicked snapchat", Toast.LENGTH_SHORT).show();
                    Intent snapchatIntent = new Intent(Intent.ACTION_SEND);
                    snapchatIntent.setType("*/*");
                    snapchatIntent.setPackage("com.snapchat.android");
                    startActivity(Intent.createChooser(snapchatIntent, "Open Snapchat"));
                    break;
                case ("whatsapp_32"):
                    //Toast.makeText(getContext(), "you clicked whatsapp", Toast.LENGTH_SHORT).show();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                    break;
                case ("tiwtter_32"):
                    // Toast.makeText(getContext(), "you clicked twitter", Toast.LENGTH_SHORT).show();
                    openTwitter();
                    break;
                case ("linkedin_32"):
                    //Toast.makeText(getContext(), "you clicked linkedin", Toast.LENGTH_SHORT).show();
                    Intent linkedinIntent;
                    //Toast.makeText(getContext(), "you clicked canvas", Toast.LENGTH_SHORT).show();
                    Uri.parse("linkedin://you");
                    linkedinIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=you"));
                    final PackageManager packageManager = getContext().getPackageManager();
                    final List<ResolveInfo> list = packageManager.queryIntentActivities(linkedinIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (list.isEmpty()) {
                        linkedinIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=you"));
                    }
                    startActivity(linkedinIntent);
                    break;
                case ("sms_32"):
                    //Toast.makeText(getContext(), "you clicked sms", Toast.LENGTH_SHORT).show();
                    sendMessages();
                    break;
            }
        }

        @Override
        public void error(String error) {

        }
    };

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
         gridView.setAdapter(new CustomAdapter(getContext().getApplicationContext(), ImagesList,asyncResultSocialId));
     }
    public void sendMessages(){
        String smsBody = "";

        Intent sendIntent = new Intent(android.content.Intent.ACTION_VIEW);
        // sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendIntent.setData(Uri.parse("smsto:"));
        //  sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.putExtra("sms_body", smsBody);

       /* Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClassName("com.android.mms", "com.android.mms.ui.ConversationList");*/

        try{
            startActivity(sendIntent);
            // finish();
        }catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void openTwitter(){
        // Create intent using ACTION_VIEW and a normal Twitter url:
        String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                urlEncode(" "),
                urlEncode("https://www.google.fi/"));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

// Narrow down to official Twitter app, if available:
        List<ResolveInfo> matches = getContext().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
            }
        }

        startActivity(intent);

    }
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
          //  Log.wtf(TAG, "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }
    private void getUserDetails() {
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        URL = network_url.Base_Url + network_url.GetIdentityById+ "identity_id=" + userId;

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

                            //             hexcode = convertRGBToHex(identityColor);
//

                            if (responsemessage.equals("true")) {
                                LayerDrawable shape = null;
                                int hexCode = switchColor(identityColor);
                                shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                                GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);
                                gradientDrawable.setStroke(8, ContextCompat.getColor(getContext().getApplicationContext(), hexCode));
                                linearLayout.setBackground(shape);

                                age.setText(userAge + " years old");
                                age.setTextColor(getResources().getColor(hexCode));
                                IdentitName.setText(identityName);
                                IdentitName.setTextColor(getResources().getColor(hexCode));
                                PersonName.setText(userName);
                                PersonName.setTextColor(getResources().getColor(hexCode));
                                city.setText(userCity);
                                city.setTextColor(getResources().getColor(hexCode));
                                Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/" + userImage).into(circleImageView);


                                // city.setTextColor(Color.parseColor(hexcode));
                             //   Glide.with(getContext()).load("http://vertosys.com/popnapp/code/" + identityId + ".png").into(qrCode);
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void deleteFriend() {
        SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        String keyuserId = user.get("userID");
        RequestQueue queue = Volley.newRequestQueue(getContext());
        URL = "http://vertosys.com/popnapp/deleteFriend.php?" + "user_id=" + keyuserId + "&friend_id=" + identityID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);

                        try {
                            responsemessage = response.getString("success");
                                message = response.getString("data");
                            if (responsemessage.equals("true")& message.equals("0")) {
                                Toast.makeText(getContext(), "Record has been deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), IdentiesMainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getContext(), "Record not found", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), IdentiesMainActivity.class);
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
        switch (v.getId()) {
           case (R.id.delete):
               deleteFriend();
                break;
            case (R.id.back):
                Intent intent =new Intent(getContext().getApplicationContext(), IdentiesMainActivity.class);
                startActivity(intent);
                break;
        }
        }
    }

