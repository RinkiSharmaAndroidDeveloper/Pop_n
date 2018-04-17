package com.popn.PopFragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.popn.AsyncResult;
import com.popn.PopActivities.Network_url;
import com.popn.PopModels.SignupModel;
import com.popn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vs1 on 3/20/2018.
 */

public class RegistrationFragment extends AppCompatActivity implements View.OnClickListener {
    public static final int GET_FROM_GALLERY = 3;
    public static String Get_Idenitiy_color = "#ff2d55";
    AsyncResult<String> asyncResult_selectedCategory;
    EditText identityName;
    SignupModel signupModel;
    TextView personName,personAge,PersonLocation,done;
    CircleImageView personImage;
    ImageView imageView,pinkCircle,purpleCircle,yellowCircle,skyCircle,grayCircle;
    LinearLayout linearLayout,imageLaout;
    Network_url network_url=new Network_url();
    ProgressDialog progressDialog;
    String IdentityName,profilepicencodedString="Test.jpeg";
    String email, passCode, FName, LName, DateBirth, isSignUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_fragment);
        progressDialog =new ProgressDialog(this);

        imageView =(ImageView)findViewById(R.id.imageView_gray_plus);
        pinkCircle =(ImageView)findViewById(R.id.pink_circle);
        purpleCircle =(ImageView)findViewById(R.id.purple_circle);
        yellowCircle =(ImageView)findViewById(R.id.yellow_circle);
        skyCircle =(ImageView)findViewById(R.id.sky_blue_circle);
        grayCircle =(ImageView)findViewById(R.id.gray_circle);
        personImage =(CircleImageView)findViewById(R.id.profile_image);
        identityName =(EditText)findViewById(R.id.identity_Name);
        imageLaout =(LinearLayout) findViewById(R.id.image_layout);
        linearLayout =(LinearLayout) findViewById(R.id.mainLayout);

        personName =(TextView)findViewById(R.id.person_name);
        personAge =(TextView) findViewById(R.id.age);
        done =(TextView)findViewById(R.id.Done);
        PersonLocation =(TextView)findViewById(R.id.location);

        pinkCircle.setOnClickListener(this);
        purpleCircle.setOnClickListener(this);
        yellowCircle.setOnClickListener(this);
        skyCircle.setOnClickListener(this);
        grayCircle.setOnClickListener(this);
        imageLaout.setOnClickListener(this);
        done.setOnClickListener(this);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
            passCode= intent.getStringExtra("password");
            FName=intent.getStringExtra("firstName");
            LName=intent.getStringExtra("lastName");
            DateBirth=intent.getStringExtra("dateBirth");
            isSignUp=intent.getStringExtra("isFirstSignup");
            //String email, String passcode, String emailId, String firstName, String lastName

            personName.setText(FName+" "+LName);
            personAge.setText(DateBirth);



      //  setUserInfo(signupModel);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                asyncResult_selectedCategory.success("1");
            }
        });




    }
    public RegistrationFragment newInstance(AsyncResult<String> asyncResult_selectedCategory, SignupModel signupModel) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        this.asyncResult_selectedCategory=asyncResult_selectedCategory;
        this.signupModel=signupModel;
        return fragment;
    }


    public void getAuthenticateLogin()
    {
        IdentityName = identityName.getText().toString();
        if(IdentityName!=null) {
            RequestQueue queue = null;
            queue = Volley.newRequestQueue(this);

          /*  String URL = network_url.Base_Url + network_url.Add_Identity +"identity_name"+"="+FName+LName+"&identity_username="+ IdentityName
                   +"&identity_userage="+DateBirth +"&identity_city="+ "Chandigarh"+"&identity_userid="+ "9215299771"+"&identity_color="+Get_Idenitiy_color
            +"&identity_userimage="+ profilepicencodedString;    */

            String URL = network_url.Base_Url + network_url.Add_Identity;
            progressDialog.show();
           JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("phone_no",FName+" "+LName);
                //   jsonBody.put("site", driverModel.getDriverSite());
                jsonBody.put("email", IdentityName);
                //  jsonBody.put("job_type", driverModel.getJobType());
                jsonBody.put("password", DateBirth);
                jsonBody.put("first_name", "Chandigarh");
                jsonBody.put("last_name", "9215299771");
                jsonBody.put("dob", Get_Idenitiy_color);
               // jsonBody.put("identity_userimage", profilepicencodedString);
                //  jsonBody.put("urgent_job",driverModel.getJobUrgency());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                            Log.e("Response", response.toString());
                            String responsemessage = null;
                            try {
                                String resposne_message = response.getString("message");
                                Toast.makeText(RegistrationFragment.this, resposne_message, Toast.LENGTH_SHORT).show();

                                   /* Intent intent=new Intent(AssignJobDriverActivity.this,JobListCounterServices.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);*/
                                progressDialog.hide();

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
        identityName.setError("Please enter identity name.");
    }

    }

  /*  public void setUserInfo(SignupModel signupModel){
        personName.setText(FName+" "+LName);
        personAge.setText(DateBirth);
    PersonLocation.setText(signupModel.getLocation());
    }
*/

    @Override
    public void onClick(View v) {
        LayerDrawable shape = null;
        switch (v.getId()) {
            case (R.id.pink_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);
                    gradientDrawable.setStroke(3,ContextCompat.getColor(this,R.color.colorTextPink));
                    linearLayout.setBackground(shape);

                    Get_Idenitiy_color="#ff2d55";
                }

                break;
            case (R.id.purple_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);
                    gradientDrawable.setStroke(3,ContextCompat.getColor(this,R.color.colorPrimary));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color="#3F51B5";
                }
                break;
            case (R.id.yellow_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(3,ContextCompat.getColor(this,R.color.colorTextYellow));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color="#FF9501";
                }

                break;
            case (R.id.sky_blue_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(3,ContextCompat.getColor(this,R.color.colorTextBlue));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color="#5CC8FA";
                }

                break;
            case (R.id.gray_circle):
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.background_shadow);
                    GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);

                    gradientDrawable.setStroke(3,ContextCompat.getColor(this,R.color.colorTextGray));

                    linearLayout.setBackground(shape);
                    Get_Idenitiy_color="#717070";
                }

                break;
            case (R.id.image_layout):
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
            case (R.id.Done):
                getAuthenticateLogin();
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
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
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
}
