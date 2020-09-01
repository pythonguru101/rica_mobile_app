package com.example.mdevt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;




import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.core.view.GravityCompat.*;

public class officerscreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextInputLayout datePass;
    TextInputEditText mobile_no;
    TextInputEditText name;
    TextInputEditText surname;
    TextInputEditText id_passport;
    TextInputEditText date;
    TextInputEditText address_1, address_2, address_3, postal_code;
    TextView errr;
    Bitmap id_photo, bitmap;
    String idbase64, address64;
    String idPath = "";
    String addressPath="";
    AutoCompleteTextView idTy;
    Button check;


    private static final int CAMERA_REQUEST = 1888;


    Calendar cal;
    DatePickerDialog datePickerDialog;
    Button btn_id, btn_address, btn_submit;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    TextView id_path, address_path;
    ImageView testimg,addressimage;
    private int idRequest = 101;
    private int addressRequest = 201;
    Uri idUri, addressUri;


    SharedPreferences validSharedpref;

    SharedPreferences sharedPref;
    SharedPreferences loginpref;
    SharedPreferences roleSharedpref;
    SharedPreferences.Editor roleEditor;
    SharedPreferences.Editor editor;
    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btn_id = (Button) findViewById(R.id.id_photo_btn);
        btn_address = (Button) findViewById(R.id.id_address_btn);
        btn_submit = (Button) findViewById(R.id.submit);

        //check=(Button)findViewById(R.id.check);

        idTy=(AutoCompleteTextView)findViewById(R.id.id);



        address_path = (TextView) findViewById(R.id.address);
        testimg = (ImageView) findViewById(R.id.testimg);
        addressimage = (ImageView) findViewById(R.id.addressimg);
        errr = (TextView) findViewById(R.id.error);

        datePass=(TextInputLayout)findViewById(R.id.datepass);
        date = (TextInputEditText) findViewById(R.id.id_passport_expiry);
        mobile_no = (TextInputEditText) findViewById(R.id.mobile_no);
        name = (TextInputEditText) findViewById(R.id.name);
        surname = (TextInputEditText) findViewById(R.id.surname);
        id_passport = (TextInputEditText) findViewById(R.id.id_passport);
        address_1 = (TextInputEditText) findViewById(R.id.address1);
        address_2 = (TextInputEditText) findViewById(R.id.address2);
        address_3 = (TextInputEditText) findViewById(R.id.address3);
        postal_code = (TextInputEditText) findViewById(R.id.postal_code);


        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        token = sharedPref.getString("access_token", "00");

        loginpref=getSharedPreferences("loginpref",Context.MODE_PRIVATE);


        roleSharedpref=getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);

        validSharedpref=getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);


        /*check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //idValidation id=new idValidation();
                try {
                    idValid(officerscreen.this,id_passport.getText().toString(),token);
                    if(validSharedpref.getString("valid","00").equals("no")){
                        //id_passport.setError("invalid id");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });*/



        id_passport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(id_passport.length()==13){
                    try {
                        idValid(officerscreen.this,id_passport.getText().toString(),token);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_passport.length()==7){
                    try {
                        idValid(officerscreen.this,id_passport.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_passport.length()==8){
                    try {
                        idValid(officerscreen.this,id_passport.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_passport.length()==9){
                    try {
                        idValid(officerscreen.this,id_passport.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_passport.length()==10){
                    try {
                        idValid(officerscreen.this,id_passport.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    id_passport.setError("please enter the correct id/passport");
                    id_passport.requestFocus();
                }

            }
        });

        //date.setEnabled(false);
        id_passport.setEnabled(false);
        idTy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (idTy.getText().toString().equals("ID")){
                    id_passport.setEnabled(true);
                    datePass.setVisibility(View.GONE);
                }
                else if (idTy.getText().toString().equals("Passport")){
                    id_passport.setEnabled(true);
                    datePass.setVisibility(View.VISIBLE);
                    date.setEnabled(true);
                }
                else{
                    idTy.setError("Select either of them");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        String[] id = new String[]{
                "ID",
                "Passport"

        };
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                officerscreen.this,
                R.layout.drop_id,
                id
        );
        idTy.setAdapter(adapter3);

        //int chooseRadioButton=idType.getCheckedRadioButtonId();



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, idRequest);
        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mobile_no.getText().toString().isEmpty()){mobile_no.setError("missing field");}
                else if(name.getText().toString().isEmpty()){name.setError("missing field");}
                else if(surname.getText().toString().isEmpty()){surname.setError("missing field");}
                else if(idTy.getText().toString().isEmpty()){idTy.setError("missing field");}
                else if(id_passport.getText().toString().isEmpty()){id_passport.setError("missing field");}
                //else if(date.getText().toString().isEmpty()){date.setError("missing field");}
                else if(address_1.getText().toString().isEmpty()){address_1.setError("missing field");}
                //else if(id_path.getText().toString().isEmpty()){id_path.setError("missing field");}
               // else if(address_path.getText().toString().isEmpty()){address_path.setError("Missing field");}
                else if(address_2.getText().toString().isEmpty()){address_2.setError("missing field");}
                else if(postal_code.getText().toString().isEmpty()){postal_code.setError("missing field");}

                else{
                    //subAgentSignUpApi object=new subAgentSignUpApi();

                    try {
                        subsignupUser(officerscreen.this,mobile_no.getText().toString(), name.getText().toString(),surname.getText().toString(),id_passport.getText().toString(),date.getText().toString(),
                                address_1.getText().toString(),address_2.getText().toString(),address_3.getText().toString(),postal_code.getText().toString(),token);
                        startActivity(new Intent(getApplicationContext(), officerscreen.class));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /*uploadImage(mobile_no.getText().toString(), name.getText().toString(),surname.getText().toString(),id_passport.getText().toString(),date.getText().toString(),
                            address_1.getText().toString(),address_2.getText().toString(),address_3.getText().toString(),postal_code.getText().toString());*/
                    //startActivity(new Intent(getApplicationContext(), officerscreen.class));

                }}

        });


        btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }
        });


        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dispatchTakePictureIntent1();
            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>RICA Agent Sign Up</font>"));

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(officerscreen.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                int mYear1 = cal.get(Calendar.YEAR);
                int mMonth1 = cal.get(Calendar.MONTH);
                int mDay1 = cal.get(Calendar.DAY_OF_MONTH);
                Calendar maxDate=Calendar.getInstance();
                maxDate.set(mYear1+10,mMonth1,mDay1);


                datePickerDialog = new DatePickerDialog(officerscreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i + "-" + (i1 + 1) + "-" + i2);
                    }
                }, day, month, year);

                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                datePickerDialog.show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(START)) {
            drawerLayout.closeDrawer(START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.agent1) {
            startActivity(new Intent(getApplicationContext(), officerscreen.class));


        }
        if (item.getItemId() == R.id.agent2) {
            startActivity(new Intent(getApplicationContext(), officer_Rica_customer.class));
        }
        if (item.getItemId() == R.id.agent3) {
            startActivity(new Intent(getApplicationContext(), officer_reset_password.class));


        }
        if (item.getItemId() == R.id.agent4) {
            startActivity(new Intent(getApplicationContext(), officer_last_ten.class));

        }
        if (item.getItemId() == R.id.agent5) {

            loginpref.edit().clear().apply();
            roleSharedpref.edit().clear().apply();


            Intent intent = new Intent(getApplicationContext(), loginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }
        if (item.getItemId()==R.id.agent6){
            startActivity(new Intent(getApplicationContext(), subsignup.class));

        }
        return true;
    }


    private void uploadImage(String mobile_number, String name,String surname,String id_passport_number,String id_passport_expiry_date,String address1,
    String address2,String address3,String postal_code) {
        File file = new File(idPath);
        File file2 = new File(addressPath);

        final postSub request = new postSub(mobile_number,name,surname,id_passport_number,
                id_passport_expiry_date,address1,address2,address3,postal_code);

        Retrofit retrofit = NetworkClient.getRetrofit();
        UploadApi uploadApi = retrofit.create(UploadApi.class);

        RequestBody idRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody addressRequestBody = RequestBody.create(MediaType.parse("image/*"), file2);
        MultipartBody.Part idPart = MultipartBody.Part.createFormData("id", file.getName(), idRequestBody);
        MultipartBody.Part addressPart = MultipartBody.Part.createFormData("address", file2.getName(), addressRequestBody);


        RequestBody mobileNumber = RequestBody.create(MediaType.parse("text/plain"),
                request.getMobile_number());
        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"),
                request.getName());
        RequestBody userSurname = RequestBody.create(MediaType.parse("text/plain"),
                request.getSurname());
        RequestBody userIdExpiryDate = RequestBody.create(MediaType.parse("text/plain"),
                request.getId_passport_expiry());
        RequestBody userIdPassportNumber = RequestBody.create(MediaType.parse("text/plain"),
                request.getId_passport_number());
        RequestBody userAddress1 = RequestBody.create(MediaType.parse("text/plain"),
                request.getAddress1());
        RequestBody userAddress2 = RequestBody.create(MediaType.parse("text/plain"),
                request.getAddress2());
        RequestBody userAddress3 = RequestBody.create(MediaType.parse("text/plain"),
                request.getAddress3());
        RequestBody userPostalCode = RequestBody.create(MediaType.parse("text/plain"),
                request.getPostal_Code());


        Call<RequestBody> call = uploadApi.uploadImage(token,idPart,addressPart,mobileNumber,userName,userSurname,
                userIdExpiryDate,userIdPassportNumber,userAddress1,userAddress2,userAddress3,userPostalCode);

        call.enqueue(new Callback<RequestBody>() {
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(officerscreen.this,"error occured",Toast.LENGTH_SHORT).show();

            }

            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.e("response", response.toString());
                Toast.makeText(officerscreen.this, "Sub Agent Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), officerscreen.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == idRequest && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(idPath);
            //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            testimg.setImageBitmap(bitmap);
        }
        /*{
            idUri = data.getData();
            //Log.e("iduri",idUri.getPath()+"");
            testimg.setImageURI(idUri);

            //id_path.setText(idUri.getPath().toString().trim());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), idUri);
                idbase64=imageToString(bitmap);
                Log.e("id" , idbase64);
                testimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        if (requestCode == addressRequest && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(addressPath);
            //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            addressimage.setImageBitmap(bitmap);
            //address64 = imageToString(bitmap);

        }

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, idRequest);
            }
        }
    }
    private void dispatchTakePictureIntent1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile1();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, addressRequest);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        idPath = image.getAbsolutePath();
        return image;
    }

    private File createImageFile1() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        addressPath = image.getAbsolutePath();
        return image;
    }


    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


    String url="http://54.228.50.10/api/v1/id_passport_number_validate/";
    SharedPreferences.Editor validEditor;
    int flag;




    public void idValid(final Context context, String id_number,  final String acccesstoken) throws JSONException {
        JSONObject jobj=new JSONObject();
        jobj.put("id_or_passport_num",id_number);
        Log.e("id_or_passport_num",id_number);

        Log.e("jsonObject", jobj.toString());
        Log.e("token",acccesstoken);

        validSharedpref=context.getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);
        validEditor=validSharedpref.edit();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, jobj, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",response.toString());
                try {

                    String status=response.getString("validation");
                    if(status.equals("true")){
                        Toast.makeText(context,"id verified!!!",Toast.LENGTH_SHORT).show();
                        btn_submit.setVisibility(View.VISIBLE);
                        validEditor.putString("valid","yes");
                        validEditor.apply();



                        //officer_Rica_customer ob=new officer_Rica_customer();
                        //ob.id_number.setError("invalid id");

                    }
                    else {
                        //Toast.makeText(context,"ID Verified",Toast.LENGTH_SHORT).show();
                        Toast.makeText(context,"invalid ID",Toast.LENGTH_SHORT).show();
                        validEditor.putString("valid","no");
                        validEditor.apply();




                        //context.startActivity(new Intent(context, loggedin.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization", "Bearer " + acccesstoken);
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(jsonReq);

    }



    String jurl="http://54.228.50.10/api/v1/sub_agent_signup/";
    public void subsignupUser(final Context context, String mobile_number, String name,String surname,String id_passport_number,String id_passport_expiry,String address1,
                              String address2,String address3,String postal_code, final String accesstoken) throws JSONException {
        JSONObject jobj = new JSONObject();
        jobj.put("mobile_number", mobile_number);
        jobj.put("name", name);
        jobj.put("surname",surname);
        jobj.put("id_passport_number",id_passport_number);
        jobj.put("id_passport_expiry_date",id_passport_expiry);
        jobj.put("photo_of_id","");
        jobj.put("address1", address1);
        jobj.put("address2",address2);
        jobj.put("address3",address3);
        jobj.put("postal_code",postal_code);
        jobj.put("photo_of_proof","");
        Log.e("aaaaaaa",jobj.toString());


        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, jurl, jobj, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",response.toString());
                try {
                    String status=response.getString("status");
                    if(status.equals("success")){
                        Toast.makeText(context,"Sub Agent Created",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Agent already exist",Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(jsonReq);
    }

}