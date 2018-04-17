package com.popn.PopFragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.popn.AsyncResult;
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopActivities.SessionManager;
import com.popn.PopModels.IdentityInformationModel;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.bitmap;

/**
 * Created by Android-Dev2 on 4/4/2018.
 */

public class AddIdentityFragment extends Fragment implements View.OnClickListener {
    public static final int CAMERA_REQUEST = 2;
    public static final int RequestPermissionCode = 1;
    public static final int GET_FROM_GALLERY = 3;
    public static String Get_Idenitiy_color = "229,229,229";
    ImageView pinkCircle,purpleCircle,yellowCircle,skyCircle,grayCircle;
    EditText identityName, personName, Age, Location;
    LinearLayout mainLayout, imageLayout;
    private BottomSheetBehavior mBottomSheetBehavior1;
    TextView photoGallery, camera, save,back;
    Network_url network_url;
    IdentityInformationModel identityInformationModel;
    AsyncResult<IdentityInformationModel> asyncResult_selectedCategory;
    SessionManager sessionManager;
    String profilepicencodedString, keyuserId;
    Bitmap bitmap;
    String UserId;
    CircleImageView profileImage;
    HashMap<String, String> user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    public AddIdentityFragment(){

    }

    public AddIdentityFragment(AsyncResult<IdentityInformationModel> asyncResult_selectedCategory){
        this.asyncResult_selectedCategory = asyncResult_selectedCategory;
    }

    public AddIdentityFragment newInstance(IdentityInformationModel identityInformationModel) {
        AddIdentityFragment fragment = new AddIdentityFragment();
        Bundle args = new Bundle();
       // this.asyncResult_selectedCategory=asyncResult_selectedCategory;
        this.identityInformationModel=identityInformationModel;
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_identity_fragment, container, false);
        identityName = (EditText) view.findViewById(R.id.identity_Name);
        personName = (EditText) view.findViewById(R.id.person_Name);
        Age = (EditText) view.findViewById(R.id.age);
        Location = (EditText) view.findViewById(R.id.location);
        mainLayout = (LinearLayout) view.findViewById(R.id.mainLayout);
        imageLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        photoGallery = (TextView) view.findViewById(R.id.photoGallery);
        back = (TextView) view.findViewById(R.id.back);
        camera = (TextView) view.findViewById(R.id.camera);
        save = (TextView) view.findViewById(R.id.Save);
        pinkCircle =(ImageView)view.findViewById(R.id.pink_circle);
        purpleCircle =(ImageView)view.findViewById(R.id.purple_circle);
        yellowCircle =(ImageView)view.findViewById(R.id.yellow_circle);
        skyCircle =(ImageView)view.findViewById(R.id.sky_blue_circle);
        grayCircle =(ImageView)view.findViewById(R.id.gray_circle);

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        network_url = new Network_url();
        sessionManager =new SessionManager(getContext());
        identityInformationModel = new IdentityInformationModel();

        View bottomSheet = view.findViewById(R.id.bottom_sheet1);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        UserId = user.get("identity_userID");
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior1.setHideable(true);
        mBottomSheetBehavior1.setPeekHeight(300);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);

        mainLayout.setOnClickListener(this);
        imageLayout.setOnClickListener(this);
        photoGallery.setOnClickListener(this);
        camera.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        pinkCircle.setOnClickListener(this);
        purpleCircle.setOnClickListener(this);
        yellowCircle.setOnClickListener(this);
        skyCircle.setOnClickListener(this);
        grayCircle.setOnClickListener(this);

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
        LayerDrawable shape = null;
        switch (v.getId()){
            case (R.id.linear_layout):
                if(mBottomSheetBehavior1.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(View bottomSheet, int newState) {
                            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                                //mButton2.setText(R.string.button2_peek);
                            }
                            else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                                //mButton2.setText(R.string.button2);
                            }
                        }

                        @Override
                        public void onSlide(View bottomSheet, float slideOffset) {
                        }
                    });
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);
                    // mButton2.setText(R.string.button2_hide);
                } else if(mBottomSheetBehavior1.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
                    // mButton2.setText(R.string.button2_peek);
                }
                break;
            case (R.id.mainLayout):
                if(mBottomSheetBehavior1.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;
            case (R.id.photoGallery):
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
            case (R.id.camera):
                EnableRuntimePermission();
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, CAMERA_REQUEST);
                break;
            case (R.id.Save):
                saveUserInformation();
                break;

            case (R.id.back):
                Intent intent =new Intent(getContext(), IdentiesMainActivity.class);
                startActivity(intent);
                break;
            case (R.id.pink_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.add_identity_background);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextPink));

                    mainLayout.setBackground(shape);
                    Get_Idenitiy_color = "255,45,85";
                }
                break;
            case (R.id.purple_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.add_identity_background);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorPrimary));

                    mainLayout.setBackground(shape);
                    Get_Idenitiy_color = "88,86,214";
                }
                break;
            case (R.id.yellow_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.add_identity_background);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextYellow));

                    mainLayout.setBackground(shape);
                    Get_Idenitiy_color = "255,149,0";
                }
                break;
            case (R.id.sky_blue_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.add_identity_background);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10, ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextBlue));

                    mainLayout.setBackground(shape);
                    Get_Idenitiy_color = "90,200,250";
                }
                break;
            case (R.id.gray_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.add_identity_background);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(10,ContextCompat.getColor(getContext().getApplicationContext(),R.color.colorTextGray));

                    mainLayout.setBackground(shape);
                    Get_Idenitiy_color = "229,229,229";
                }
                break;
        }
    }

    public void EnableRuntimePermission() {

        if ( ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA) ) {

            Toast.makeText(getActivity(), "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // take a picture from camera
        if ( requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK ) {
            // Uri selectedImage = data.getData();
            bitmap = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            profilepicencodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            if ( profilepicencodedString != null ) {
                //setImage();
                uploadImage();
            } else {
                profilepicencodedString = "test.jpeg";
            }

        }

        //Detects request codes
        if ( requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK ) {
            Uri selectedImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getApplicationContext().getContentResolver(), selectedImage);
                // personImage.setImageBitmap(bitmap);
               // String database1 = encodeToBase64(bitmap);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                        bitmap, 120, 120, false);
                profileImage.setImageBitmap(resizedBitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                profilepicencodedString = Base64.encodeToString(b, Base64.DEFAULT);
                if ( profilepicencodedString != null ) {
                    //setImage();
                    uploadImage();
                } else {
                    profilepicencodedString = "test.jpeg";
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

    public void saveUserInformation()
    {
        String IdentityName = identityName.getText().toString().replaceAll(" ", "%20");
        String name = personName.getText().toString().replaceAll(" ", "%20");
        String age = Age.getText().toString().replaceAll(" ", "%20");
        String city = Location.getText().toString().replaceAll(" ", "%20");
        if(identityName!=null && name!=null && age!=null && city!=null) {
            RequestQueue queue = null;
            queue = Volley.newRequestQueue(getContext());

            String URL = network_url.Base_Url + network_url.Add_Identity +"identity_name"+"="+IdentityName+"&identity_username="+ name
                    +"&identity_userage="+age +"&identity_city="+city+"&identity_userid="+ UserId +"&identity_color="+Get_Idenitiy_color;
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
}
