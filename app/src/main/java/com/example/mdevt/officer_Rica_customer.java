package com.example.mdevt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.scrounger.countrycurrencypicker.library.Buttons.CountryCurrencyButton;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static androidx.core.view.GravityCompat.START;

public class officer_Rica_customer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button register,scan;
    TextInputEditText  sim,last_four,id_number,full_name,surname,dialling_code,area_code,dialling_number,address_1,address_2,address_3,sub_urb,postal_code,city_town,country;
    TextInputLayout textInputLayout, textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1, autoCompleteTextView2, autoCompleteTextView3, autoCompleteTextView4;
    SharedPreferences sharedPref,sharedPreferences;
    RadioButton radioButtonYes,radioButtonNo;
    RadioGroup proof;
    String token;
    String address_proof;
    TextInputEditText date;

    SharedPreferences validSharedpref;

    SharedPreferences loginpref;
    SharedPreferences roleSharedpref;
    String lastFourDigits = "";

    TextView result;

    Calendar cal;
    DatePickerDialog datePickerDialog;
    Button check1;



    SharedPreferences.Editor editor;
    public static final String BARCODE_KEY = "BARCODE";

    private Barcode barcodeResult;
    TextInputLayout dateExp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rica_customer);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer1);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textInputLayout = (TextInputLayout) findViewById(R.id.network1);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.network);
        textInputLayout1 = (TextInputLayout) findViewById(R.id.new_sim1);
        autoCompleteTextView1 = (AutoCompleteTextView) findViewById(R.id.new_sim);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.register_sim1);
        autoCompleteTextView2 = (AutoCompleteTextView) findViewById(R.id.register_sim);
        textInputLayout3 = (TextInputLayout) findViewById(R.id.id1);
        autoCompleteTextView3 = (AutoCompleteTextView) findViewById(R.id.id);
        textInputLayout3 = (TextInputLayout) findViewById(R.id.id1);

        date = (TextInputEditText) findViewById(R.id.id_passport_expiry);

        sim=(TextInputEditText)findViewById(R.id.SIM);
        last_four=(TextInputEditText)findViewById(R.id.Last_4digits_of_sim);
        id_number=(TextInputEditText)findViewById(R.id.id_number);
        full_name=(TextInputEditText)findViewById(R.id.full_name);
        surname=(TextInputEditText)findViewById(R.id.sur_name);

        address_1=(TextInputEditText)findViewById(R.id.address_1);
        address_2=(TextInputEditText)findViewById(R.id.address_2);
        address_3=(TextInputEditText)findViewById(R.id.address_3);
        sub_urb=(TextInputEditText)findViewById(R.id.sub_urb);
        postal_code=(TextInputEditText)findViewById(R.id.post_code);
        city_town=(TextInputEditText)findViewById(R.id.city_town);
        //country=(TextInputEditText)findViewById(R.id.contry1);
        final CountryCurrencyButton button=(CountryCurrencyButton)findViewById(R.id.button);

        proof=(RadioGroup)findViewById(R.id.proof);
        radioButtonYes=(RadioButton)findViewById(R.id.yes);
        radioButtonNo=(RadioButton)findViewById(R.id.no);


        scan=(Button)findViewById(R.id.scanner);
        result=(TextView)findViewById(R.id.result);
       // check1=(Button)findViewById(R.id.check1);

        dateExp=(TextInputLayout)findViewById(R.id.dateexp);


        register=(Button)findViewById(R.id.register);
        //CountryCurrencyButton button = (CountryCurrencyButton) findViewById(R.id.button);


        loginpref=getSharedPreferences("loginpref",Context.MODE_PRIVATE);
        roleSharedpref=getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);


        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        token = sharedPref.getString("access_token", "00");

        validSharedpref=getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(autoCompleteTextView.getText().toString().equals("Vodacom")){
                    sim.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
                    sim.setInputType(InputType.TYPE_CLASS_NUMBER);

                }
                else if(autoCompleteTextView.getText().toString().equals("Cell c")){
                    sim.setFilters(new InputFilter[] { new InputFilter.LengthFilter(19) });
                    sim.setInputType(InputType.TYPE_CLASS_NUMBER);

                }
                else if(autoCompleteTextView.getText().toString().equals("MTN")){
                    sim.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                    sim.setInputType(InputType.TYPE_CLASS_NUMBER);

                }
                else if(autoCompleteTextView.getText().toString().equals("Telkom")){
                    sim.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
                    sim.setInputType(InputType.TYPE_CLASS_NUMBER);

                }

            }
        });








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

                datePickerDialog = new DatePickerDialog(officer_Rica_customer.this, new DatePickerDialog.OnDateSetListener() {
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


        id_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(id_number.length()==13){
                    try {
                        idValid(officer_Rica_customer.this,id_number.getText().toString(),token);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_number.length()==7){
                    try {
                        idValid(officer_Rica_customer.this,id_number.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_number.length()==8){
                    try {
                        idValid(officer_Rica_customer.this,id_number.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_number.length()==9){
                    try {
                        idValid(officer_Rica_customer.this,id_number.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if (id_number.length()==10){
                    try {
                        idValid(officer_Rica_customer.this,id_number.getText().toString(),token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    id_number.setError("please enter the correct id/passport");
                    id_number.requestFocus();
                }


            }
        });

        button.setEnabled(false);
        date.setEnabled(false);

        autoCompleteTextView3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (autoCompleteTextView3.getText().toString().equals("Passport")){
                    button.setEnabled(true);
                    date.setEnabled(true);
                    dateExp.setVisibility(View.VISIBLE);

                }
                else if (autoCompleteTextView3.getText().toString().equals("ID Number")){
                    button.setEnabled(false);
                    date.setEnabled(false);
                    dateExp.setVisibility(View.GONE);
                }
                else{
                    autoCompleteTextView3.setError("choose either of them");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        register.setEnabled(false);
        proof.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case (R.id.yes):
                        register.setEnabled(true);
                        break;
                    case (R.id.no):
                        register.setEnabled(false);
                }
            }
        });

        last_four.setEnabled(false);
        sim.setEnabled(false);
        scan.setEnabled(false);
        autoCompleteTextView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (autoCompleteTextView2.getText().toString().equals("Starter pack")){
                    sim.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                    sim.setInputType(InputType.TYPE_CLASS_TEXT);

                    sim.setHint("KIT Number*");
                    sim.setEnabled(true);
                    last_four.setEnabled(false);
                    scan.setEnabled(true);

                }
                else if (autoCompleteTextView2.getText().toString().equals("Cellphone number")){
                    sim.setHint("Cell Number*");
                    sim.setEnabled(true);
                    last_four.setEnabled(false);

                }
                else if (autoCompleteTextView2.getText().toString().equals("SIM")){
                    sim.setHint("SIM*");
                    sim.setEnabled(true);
                    last_four.setEnabled(true);
                    scan.setEnabled(true);
                }
                else{
                    autoCompleteTextView2.setError("choose either of them");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        sim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String sc=sim.getText().toString();
                if (sc.length() > 4)
                {
                    lastFourDigits = sc.substring(sc.length() - 4);
                }
                last_four.setText(lastFourDigits);



            }



            @Override
            public void afterTextChanged(Editable editable) {

            }
        });








        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //radioButton=(RadioButton)findViewById(proof.getCheckedRadioButtonId());
                address_proof="True";  //radioButton.getText().toString();

                if (autoCompleteTextView.getText().toString().isEmpty()) {
                    autoCompleteTextView.setError("missing field");
                }//network
               /* else if (autoCompleteTextView1.getText().toString().isEmpty()) {
                    autoCompleteTextView1.setError("missing field");
                }//new*/
                else if (autoCompleteTextView2.getText().toString().isEmpty()) {
                    autoCompleteTextView2.setError("missing field");
                }//SIM
                else if (autoCompleteTextView3.getText().toString().isEmpty()) {
                    autoCompleteTextView3.setError("missing field");
                }//ID
                else if (sim.getText().toString().isEmpty()) {
                    sim.setError("missing field");
                } else if (last_four.getText().toString().isEmpty()) {
                    last_four.setError("Missing field");
                } else if (id_number.getText().toString().isEmpty()) {
                    id_number.setError("missing field");
                } else if (full_name.getText().toString().isEmpty()) {
                    full_name.setError("missing field");
                } else if (surname.getText().toString().isEmpty()) {
                    surname.setError("missing field");
                } else if (address_1.getText().toString().isEmpty()) {
                    address_1.setError("missing field");
                }/* else if (address_2.getText().toString().isEmpty()) {
                    address_2.setError("missing field");
                } */else if (sub_urb.getText().toString().isEmpty()) {
                    sub_urb.setError("missing field");
                } else if (postal_code.getText().toString().isEmpty()) {
                    postal_code.setError("missing field");
                } else if (city_town.getText().toString().isEmpty()) {
                    city_town.setError("missing field");
                }/* else if (country.getText().toString().isEmpty()) {
                    country.setError("missing field");
                }*/ else
                {

                    RicaApi ob = new RicaApi();
                    try {
                        ob.rica(officer_Rica_customer.this, autoCompleteTextView.getText().toString(), null,
                                autoCompleteTextView2.getText().toString(),  sim.getText().toString(),
                                last_four.getText().toString(),autoCompleteTextView3.getText().toString(),button.getText().toString(), id_number.getText().toString(),
                                full_name.getText().toString(), surname.getText().toString()
                                , address_1.getText().toString(), null,
                                null, sub_urb.getText().toString(), postal_code.getText().toString(),
                                city_town.getText().toString(),address_proof, token);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });




        String[] networks = new String[]{
                "MTN",
                "Vodacom",
                "Cell c",
                "Telkom",
                "Lyca",
                "Virgin"

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                officer_Rica_customer.this,
                R.layout.drop_network,
                networks
        );
        autoCompleteTextView.setAdapter(adapter);



        /*String[] newsim = new String[]{
                "NEW"
        };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                officer_Rica_customer.this,
                R.layout.drop_new,
                newsim
        );
        autoCompleteTextView1.setAdapter(adapter1);*/



        String[] sim = new String[]{
                //"Cellphone number",
                "SIM",
                "Starter pack"

        };
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                officer_Rica_customer.this,
                R.layout.drop_sim,
                sim
        );
        autoCompleteTextView2.setAdapter(adapter2);



        String[] id = new String[]{
                "ID Number",
                "Passport"

        };
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                officer_Rica_customer.this,
                R.layout.drop_id,
                id
        );
        autoCompleteTextView3.setAdapter(adapter3);




        button.setOnClickListener(new CountryCurrencyPickerListener() {
            @Override
            public void onSelectCountry(Country country) {
                if (country.getCurrency() == null) {
                    Toast.makeText(officer_Rica_customer.this,
                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(officer_Rica_customer.this,
                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), country.getCurrency().getSymbol())
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSelectCurrency(Currency currency) {

            }
        });




        /*textInputLayout4=(TextInputLayout)findViewById(R.id.contry1);
        autoCompleteTextView4=(AutoCompleteTextView)findViewById(R.id.country);
        String[] country=new String[]{
                "United States", "Canada", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and/or Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecudaor", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France, Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast",
                "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kosovo", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua",
                "Niger", "Nigeria", "Niue", "Norfork Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea",
                "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone",
                "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia South Sandwich Islands", "South Sudan",
                "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbarn and Jan Mayen Islands", "Swaziland", "Sweden",
                "Switzerland", "Syrian Arab Republic", "Taiwan", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo",
                "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",
                "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States minor outlying islands",
                "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City State", "Venezuela", "Vietnam",
                "Virigan Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara",
                "Yemen", "Yugoslavia", "Zaire", "Zambia", "Zimbabwe"

        };
        ArrayAdapter<String> adapter4=new ArrayAdapter<>(
                Rica_customer.this,
                R.layout.drop_country,
                country
        );
        autoCompleteTextView4.setAdapter(adapter4);*/


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Rica Customer Screen</font>"));
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(officer_Rica_customer.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


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
        if (item.getItemId()==R.id.agent1) {
            startActivity(new Intent(getApplicationContext(), officerscreen.class));
        }
        if (item.getItemId()==R.id.agent2){
            startActivity(new Intent(getApplicationContext(), officer_Rica_customer.class));
        }
        if (item.getItemId()==R.id.agent3){
            startActivity(new Intent(getApplicationContext(), officer_reset_password.class));


        }
        if (item.getItemId()==R.id.agent4){
            startActivity(new Intent(getApplicationContext(), officer_last_ten.class));

        }
        if (item.getItemId()==R.id.agent5){

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
    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(officer_Rica_customer.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withText("Scanning...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        result.setText(barcode.rawValue);
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BARCODE_KEY, barcodeResult);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MaterialBarcodeScanner.RC_HANDLE_CAMERA_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScan();
            return;
        }
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(android.R.string.ok, listener)
                .show();
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
                        register.setVisibility(View.VISIBLE);
                        validEditor.putString("valid","yes");
                        validEditor.apply();



                        //officer_Rica_customer ob=new officer_Rica_customer();
                        //ob.id_number.setError("invalid id");

                    }
                    else {
                        //Toast.makeText(context,"ID Verified",Toast.LENGTH_SHORT).show();
                        Toast.makeText(context,"invalid ID",Toast.LENGTH_SHORT).show();
                        register.setVisibility(View.INVISIBLE);
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

    public void checkyourid(View view) {
        try {
            idValid(officer_Rica_customer.this,id_number.getText().toString(),token);
            if(validSharedpref.getString("valid","00").equals("no")){
                //id_passport.setError("invalid id");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}