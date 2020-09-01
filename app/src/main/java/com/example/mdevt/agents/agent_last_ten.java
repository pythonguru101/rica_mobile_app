package com.example.mdevt.agents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mdevt.networking.last10Agents.ListViewAdaptor;
import com.example.mdevt.R;
import com.example.mdevt.networking.last10Agents.Status;
import com.example.mdevt.initial.loginScreen;
import com.example.mdevt.officer.officer_Last10_agent;
import com.example.mdevt.officer.officer_Rica_customer;
import com.example.mdevt.officer.officer_last_ten;
import com.example.mdevt.officer.officer_reset_password;
import com.example.mdevt.officer.officerscreen;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.view.GravityCompat.START;

public class agent_last_ten extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    SharedPreferences loginpref;
    SharedPreferences roleSharedpref;

    private static final String JSON_URL = "http://54.228.50.10/api/v1/last10_rica_customer/";

    ListView listView;
    List<Status> sampleList;
    String token;
    SharedPreferences sharedPref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_last_ten);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView=(NavigationView) findViewById(R.id.navigation);
        toolbar=(Toolbar) findViewById(R.id.toolbar);

        listView=(ListView) findViewById(R.id.listView);
        sampleList=new ArrayList<>();

        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        token = sharedPref.getString("access_token", "00");
        try {
            loadSampleList(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loginpref=getSharedPreferences("loginpref", Context.MODE_PRIVATE);
        roleSharedpref=getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Last 10 RICA Customer</font>"));
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(agent_last_ten.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(START)){
            drawerLayout.closeDrawer(START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.agent2){
            startActivity(new Intent(getApplicationContext(), agentscreen.class));
        }
        if (item.getItemId()==R.id.agent3){
            startActivity(new Intent(getApplicationContext(), agent_reset_password.class));


        }
        if (item.getItemId()==R.id.agent4){
            startActivity(new Intent(getApplicationContext(), agent_last_ten.class));


        }



        if (item.getItemId()==R.id.agent5){


            loginpref.edit().clear().apply();
            roleSharedpref.edit().clear().apply();

            Intent intent = new Intent(getApplicationContext(), loginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }


        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void loadSampleList(final String acccesstoken)throws JSONException{
        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray sampleListArray = obj.getJSONArray("last10_rica_customers");

                            for (int i = 0; i <sampleListArray.length(); i++) {
                                JSONObject sampleObject = sampleListArray.getJSONObject(i);


                                Status status = new Status(sampleObject.getString("pk"), sampleObject.getJSONObject("fields").getString("full_name"),
                                        sampleObject.getJSONObject("fields").getString("surname"),"SUCCESS");
                                sampleList.add(status);
                            }
                            ListViewAdaptor adaptor = new ListViewAdaptor(sampleList, getApplicationContext());
                            listView.setAdapter(adaptor);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() ,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization", "Bearer " + acccesstoken);
                return headers;
            }
        };;


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}



