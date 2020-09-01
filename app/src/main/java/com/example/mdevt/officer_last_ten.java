package com.example.mdevt;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.view.GravityCompat.START;

public class officer_last_ten extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    SharedPreferences loginpref;
    SharedPreferences roleSharedpref;

    private static final String JSON_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";

    ListView listView;
    List<Status> sampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_ten);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView=(NavigationView) findViewById(R.id.navigation);
        toolbar=(Toolbar) findViewById(R.id.toolbar);

        listView=(ListView) findViewById(R.id.listView);
        sampleList=new ArrayList<>();
        loadSampleList();

        loginpref=getSharedPreferences("loginpref",Context.MODE_PRIVATE);
        roleSharedpref=getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Rica Statuses</font>"));
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(officer_last_ten.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void loadSampleList(){
        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray sampleListArray = obj.getJSONArray("heroes");
                            for (int i = 0; i < sampleListArray.length(); i++) {
                                JSONObject sampleObject = sampleListArray.getJSONObject(i);

                                Status status = new Status(sampleObject.getString("name"), sampleObject.getString("imageurl"));
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
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}