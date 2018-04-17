package com.popn.PopFragments;

import android.app.Activity;
import android.content.Context;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.popn.Adapters.IdentityCustomeAdapter;
import com.popn.AsyncResult;
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopActivities.SignUpEmailActivity;
import com.popn.PopActivities.SignUpPersonalInfoActivity;
import com.popn.PopModels.ConnectionModel;
import com.popn.PopModels.IdentityInformationModel;
import com.popn.PopModels.SocialIdModel;
import com.popn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditInfoFragment extends Fragment implements View.OnClickListener{
    public static final int GET_FROM_GALLERY = 3;
    public static String Get_Idenitiy_color = "229,229,229";
    AsyncResult<String> asyncResult_selectedCategory;
    ImageView imageView,pinkCircle,purpleCircle,yellowCircle,skyCircle,grayCircle,qr_code;
    LinearLayout linearLayout,image_layout;
    TextView done,back,delete;
    String keyuserId;
    CircleImageView personImage;
    IdentityInformationModel identityInformationModel;
    EditText IdName,PersonName,Age,Location;
    String profilepicencodedString=null;
    SocialIdModel socialIdModel1;
    GridView gridView;
    Network_url network_url;
    List<SocialIdModel> ImagesList =new ArrayList<>();
    SocialIdModel socialIdModel;
    List<SocialIdModel> osImages =new ArrayList<>();
    IdentityCustomeAdapter identityCustomeAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    public EditInfoFragment newInstance(AsyncResult<String > asyncResult_selectedCategory,IdentityInformationModel identityInformationModel) {
        EditInfoFragment fragment = new EditInfoFragment();
        Bundle args = new Bundle();
        this.asyncResult_selectedCategory=asyncResult_selectedCategory;
        this.identityInformationModel=identityInformationModel;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);
       // imageView =(ImageView)view.findViewById(R.id.imageView_gray_plus);
        pinkCircle =(ImageView)view.findViewById(R.id.pink_circle);
        purpleCircle =(ImageView)view.findViewById(R.id.purple_circle);
        yellowCircle =(ImageView)view.findViewById(R.id.yellow_circle);
        skyCircle =(ImageView)view.findViewById(R.id.sky_blue_circle);
        grayCircle =(ImageView)view.findViewById(R.id.gray_circle);
        gridView =(GridView)view.findViewById(R.id.customgrid);
        personImage =(CircleImageView) view.findViewById(R.id.profile_image);
        qr_code =(ImageView)view.findViewById(R.id.imageView2);
        IdName =(EditText)view.findViewById(R.id.identity_Name);
        PersonName =(EditText)view.findViewById(R.id.person_Name);

        Age =(EditText)view.findViewById(R.id.age);
        done =(TextView)view.findViewById(R.id.Done);
        delete =(TextView)view.findViewById(R.id.delete);
        back =(TextView)view.findViewById(R.id.back);
        Location =(EditText)view.findViewById(R.id.location);

        linearLayout =(LinearLayout) view.findViewById(R.id.mainLayout);
        image_layout =(LinearLayout) view.findViewById(R.id.linear_layout);
        network_url = new Network_url();
        pinkCircle.setOnClickListener(this);
        purpleCircle.setOnClickListener(this);
        yellowCircle.setOnClickListener(this);
        skyCircle.setOnClickListener(this);
        grayCircle.setOnClickListener(this);
        image_layout.setOnClickListener(this);
        done.setOnClickListener(this);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        setData(identityInformationModel);
        getSocialId();
        return view;
    }
    AsyncResult<String> asyncResult_clickRegisterBtn = new AsyncResult<String>() {
        @Override
        public void success(String click) {
            asyncResult_selectedCategory.success(identityInformationModel.getIdentityId());

        }
        @Override
        public void error(String error) {

        }
    };
    AsyncResult<Integer> asyncResult_clickCrossBtn = new AsyncResult<Integer>() {
        @Override
        public void success(Integer pos) {
            List<SocialIdModel> ImagesList1 =new ArrayList<>();
            int i=pos;
            ImagesList.remove(i);

            identityCustomeAdapter.notifyDataSetChanged();

        }
        @Override
        public void error(String error) {

        }
    };
    private void deleteSocialIds() {
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        //http://vertosys.com/popnapp/delete_social_identity.php?identity_id=&social_id=
        String URL = network_url.Base_Url + network_url.DeleteSocialIds+ ""+ "=" + identityInformationModel.getIdentityId();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);

                        try {
                            String responsemessage = response.getString("success");
                            String message = response.getString("message");
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
    private void getSocialId() {
        RequestQueue queue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            queue = Volley.newRequestQueue(getContext().getApplicationContext());
        }
        String URL = network_url.Base_Url + network_url.Get_Identity_Social_By_Id+ network_url.IdentityId+ "=" + identityInformationModel.getIdentityId();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        //  progressBar.setVisibility(View.GONE);

                        try {
                            String responsemessage = response.getString("success");
                            String message = response.getString("message");
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
        socialIdModel1=new SocialIdModel("gray_plus","imageView_gray_plus");
        ImagesList.add(socialIdModel1);
        identityCustomeAdapter = new IdentityCustomeAdapter(getContext().getApplicationContext(), ImagesList,true,asyncResult_clickRegisterBtn,asyncResult_clickCrossBtn);
        gridView.setAdapter(identityCustomeAdapter);
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

   public void setData(IdentityInformationModel identityInformationModel){
       LayerDrawable shape = null;
       int hexCode = switchColor(identityInformationModel.getIdentityColor());
       shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
       GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);
       gradientDrawable.setStroke(10, ContextCompat.getColor(getContext().getApplicationContext(), hexCode));
        linearLayout.setBackground(shape);
        Age.setText(identityInformationModel.getIdentityUserAge());
        Age.setTextColor(getResources().getColor(hexCode));
        IdName.setText(identityInformationModel.getIdentityName());
        IdName.setTextColor(getResources().getColor(hexCode));
        Location.setText(identityInformationModel.getIdentityCity());
        Location.setTextColor(getResources().getColor(hexCode));
        PersonName.setText(identityInformationModel.getIdentityUserName());
        PersonName.setTextColor(getResources().getColor(hexCode));
        Glide.with(getContext()).load("http://vertosys.com/popnapp/code/" + identityInformationModel.getIdentityId() + ".png").into(qr_code);
        Glide.with(getContext().getApplicationContext()).load("http://vertosys.com/popnapp/" + identityInformationModel.getIdentityUserImage()).into(personImage);
   }

    public void uploadImage(){
        SessionManager sessionManager =new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        keyuserId = user.get("userID");
        String URL = network_url.Base_Url + network_url.UploadImage;

        //sending image to server
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                // progressDialog.dismiss();
                try {
                    JSONObject response = new JSONObject(s);
                    String resposne_message = response.getString("success");
                    String res = response.getString("message");

                    if (resposne_message.equals("true") ) {
                        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
                    }

                    Log.d("My App", response.toString());

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + s + "\"");
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("identity_id", keyuserId);
                parameters.put("image", profilepicencodedString);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void getAuthenticateLogin()
    {
        //IdName,PersonName,Age,Location
        String identityName = IdName.getText().toString().replaceAll(" ", "%20");
        String name = PersonName.getText().toString().replaceAll(" ", "%20");
        String age = Age.getText().toString().replaceAll(" ", "%20");
        String city = Location.getText().toString().trim().replaceAll(" ", "%20");

        if(identityName!=null && name!=null && age!=null && city!=null) {
            RequestQueue queue = null;
            queue = Volley.newRequestQueue(getContext());

            String URL = network_url.Base_Url + network_url.Edit_Identity +"identity_id="+ identityInformationModel.getIdentityId()+"&identity_name"+"="+identityName+"&identity_username="+ name
                    +"&identity_userage="+age +"&identity_city="+city+"&identity_userid="+ identityInformationModel.getIdentityUserId()
                    +"&identity_color="+Get_Idenitiy_color +"&identity_userimage=" + identityInformationModel.getIdentityUserImage();
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
        }else{
            Toast.makeText(getContext(), "Please enter the valid information", Toast.LENGTH_SHORT).show();
        }

    }
    public void deleteIdentity()
    {

        SessionManager sessionManager =new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        String keyuserId = user.get("userID");

            RequestQueue queue = null;
            queue = Volley.newRequestQueue(getContext());

            String URL = network_url.Base_Url + network_url.DeleteById +"user_id="+keyuserId +"&identity_id="+identityInformationModel.getIdentityId();
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



    @Override
    public void onClick(View v) {
        LayerDrawable shape = null;
        switch (v.getId()) {
            case (R.id.pink_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextPink));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color = "255,45,85";
                }

                break;
            case (R.id.purple_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorPrimary));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color = "88,86,214";
                }
                break;
            case (R.id.yellow_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextYellow));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color = "255,149,0";
                }

                break;
            case (R.id.sky_blue_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextBlue));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color = "90,200,250";
                }

                break;
            case (R.id.gray_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextGray));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color = "229,229,229";
                }

                break;
            case (R.id.linear_layout):
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
            case (R.id.Done):
                getAuthenticateLogin();
                break;
            case (R.id.back):
                Intent intent =new Intent(getContext().getApplicationContext(), IdentiesMainActivity.class);
                startActivity(intent);
                break;
            case (R.id.delete):
                deleteIdentity();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getApplicationContext().getContentResolver(), selectedImage);
                // personImage.setImageBitmap(bitmap);
                String database1=encodeToBase64(bitmap);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                        bitmap, 120, 120, false);
                personImage.setImageBitmap(resizedBitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                profilepicencodedString = Base64.encodeToString(b, Base64.DEFAULT);
                if(profilepicencodedString!=null)
                {
                    uploadImage();
                }else{
                    profilepicencodedString="test.jpeg";
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static String encodeToBase64(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


  /*  public void setImage()
    {
        SessionManager sessionManager =new SessionManager(getContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        String keyuserId = user.get("userID");
        if(profilepicencodedString==null)
        {
            profilepicencodedString="test.jpeg";
        }
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(getContext());
//social_id, identity_id, social_profileid
        String URL = network_url.Base_Url + network_url.UploadImage +"identity_id="+keyuserId+"&image="+profilepicencodedString;
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


    }*/

}
