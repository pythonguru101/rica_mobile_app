package com.example.mdevt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import static androidx.core.view.GravityCompat.START;

public class last_ten extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_ten);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView=(NavigationView) findViewById(R.id.navigation);
        toolbar=(Toolbar) findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Rica Statuses</font>"));
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(last_ten.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
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
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }
        if (item.getItemId()==R.id.agent2){
            startActivity(new Intent(getApplicationContext(), Rica_customer.class));
        }
        if (item.getItemId()==R.id.agent3){
            startActivity(new Intent(getApplicationContext(), reset_password.class));


        }
        if (item.getItemId()==R.id.agent4){
            startActivity(new Intent(getApplicationContext(), last_ten.class));


        }
        if (item.getItemId()==R.id.agent5){
            Intent intent = new Intent(getApplicationContext(), loginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }


        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}