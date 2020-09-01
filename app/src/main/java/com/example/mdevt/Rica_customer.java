package com.example.mdevt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
/*
import com.scrounger.countrycurrencypicker.library.Buttons.CountryCurrencyButton;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;*/

import org.json.JSONException;

import static androidx.core.view.GravityCompat.*;

public class Rica_customer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button register;
    TextInputEditText  sim,last_four,id_number,full_name,surname,dialling_code,area_code,dialling_number,address_1,address_2,address_3,sub_urb,postal_code,city_town,country;
    TextInputLayout textInputLayout, textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1, autoCompleteTextView2, autoCompleteTextView3, autoCompleteTextView4;
    SharedPreferences sharedPref;
    RadioButton radioButton;
    RadioGroup proof;
    String token;
    String address_proof;

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
        country=(TextInputEditText)findViewById(R.id.contry1);

        proof=(RadioGroup)findViewById(R.id.proof);



        register=(Button)findViewById(R.id.register);
        //CountryCurrencyButton button = (CountryCurrencyButton) findViewById(R.id.button);

        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        token = sharedPref.getString("access_token", "00");




        register.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //radioButton=(RadioButton)findViewById(proof.getCheckedRadioButtonId());
                                            address_proof="True";  //radioButton.getText().toString();

                                            if (autoCompleteTextView.getText().toString().isEmpty()) {
                                                autoCompleteTextView.setError("missing field");
                                            }//network
                                            else if (autoCompleteTextView1.getText().toString().isEmpty()) {
                                                autoCompleteTextView1.setError("missing field");
                                            }//new
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
                                            } else if (address_2.getText().toString().isEmpty()) {
                                                address_2.setError("missing field");
                                            } else if (sub_urb.getText().toString().isEmpty()) {
                                                sub_urb.setError("missing field");
                                            } else if (postal_code.getText().toString().isEmpty()) {
                                                postal_code.setError("missing field");
                                            } else if (city_town.getText().toString().isEmpty()) {
                                                city_town.setError("missing field");
                                            } else if (country.getText().toString().isEmpty()) {
                                                country.setError("missing field");
                                            } else
                                                {

                                                RicaApi ob = new RicaApi();
                                                try {
                                                    ob.rica(Rica_customer.this, autoCompleteTextView.getText().toString(), autoCompleteTextView1.getText().toString(),
                                                            autoCompleteTextView2.getText().toString(),  sim.getText().toString(),
                                                            last_four.getText().toString(),autoCompleteTextView3.getText().toString(),country.getText().toString(), id_number.getText().toString(),
                                                            full_name.getText().toString(), surname.getText().toString()
                                                            , address_1.getText().toString(), address_2.getText().toString(),
                                                            address_3.getText().toString(), sub_urb.getText().toString(), postal_code.getText().toString(),
                                                            city_town.getText().toString(),address_proof, token);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });




        String[] networks = new String[]{
                "Cell c",
                "Lyca",
                "MTN",
                "Telkom",
                "Virgin",
                "Vodacom"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Rica_customer.this,
                R.layout.drop_network,
                networks
        );
        autoCompleteTextView.setAdapter(adapter);



        String[] newsim = new String[]{
                "NEW",
                "Existing"
        };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                Rica_customer.this,
                R.layout.drop_new,
                newsim
        );
        autoCompleteTextView1.setAdapter(adapter1);



        String[] sim = new String[]{
                "Cellphone number",
                "SIM",
                "Starter pack"

        };
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                Rica_customer.this,
                R.layout.drop_sim,
                sim
        );
        autoCompleteTextView2.setAdapter(adapter2);



        String[] id = new String[]{
                "Business Registeration",
                "Passport"

        };
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                Rica_customer.this,
                R.layout.drop_id,
                id
        );
        autoCompleteTextView3.setAdapter(adapter3);




       /* button.setOnClickListener(new CountryCurrencyPickerListener() {
            @Override
            public void onSelectCountry(Country country) {
                if (country.getCurrency() == null) {
                    Toast.makeText(Rica_customer.this,
                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Rica_customer.this,
                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), country.getCurrency().getSymbol())
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSelectCurrency(Currency currency) {

            }
        });*/




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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Rica_customer.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        if (item.getItemId() == R.id.agent1) {
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));

        }
        if (item.getItemId() == R.id.agent2) {
            startActivity(new Intent(getApplicationContext(), Rica_customer.class));

        }
        if (item.getItemId() == R.id.agent3) {
            startActivity(new Intent(getApplicationContext(), reset_password.class));


        }
        if (item.getItemId() == R.id.agent4) {
            startActivity(new Intent(getApplicationContext(), last_ten.class));

        }
        if (item.getItemId() == R.id.agent5) {
            Intent intent = new Intent(getApplicationContext(), loginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        
        return true;
    }
}