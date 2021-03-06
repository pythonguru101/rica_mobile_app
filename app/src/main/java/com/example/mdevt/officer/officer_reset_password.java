package com.example.mdevt.officer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mdevt.R;
import com.example.mdevt.initial.loginScreen;
import com.example.mdevt.networking.resetApi;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.ybs.passwordstrengthmeter.PasswordStrength;

import org.json.JSONException;

import static androidx.core.view.GravityCompat.START;

public class officer_reset_password extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextInputEditText mobile,old_pass,new_pass,confirm_pass;
    ProgressBar progressBar;
    Button reset;
    SharedPreferences sharedPreferences;
    SharedPreferences loginpref;
    SharedPreferences roleSharedpref;
    SharedPreferences.Editor editor;
    String token,u;
    SharedPreferences sharedPref;
    SharedPreferences userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView=(NavigationView) findViewById(R.id.navigation);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        old_pass=(TextInputEditText)findViewById(R.id.old_pass);
        new_pass=(TextInputEditText)findViewById(R.id.new_pass);
        confirm_pass=(TextInputEditText)findViewById(R.id.confirm_pass);
        mobile=(TextInputEditText)findViewById(R.id.mobile);

        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        token = sharedPref.getString("access_token", "00");

        userName = getSharedPreferences("userNameList", Context.MODE_PRIVATE);


        reset=(Button)findViewById(R.id.reset);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        loginpref=getSharedPreferences("loginpref",Context.MODE_PRIVATE);
        roleSharedpref=getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);




        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Reset Password</font>"));
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(officer_reset_password.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        pass_word();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.getText().toString().isEmpty()){
                    mobile.setError("missing old password");
                    mobile.requestFocus();
                }
                else if(old_pass.getText().toString().isEmpty()){
                    old_pass.setError("missing old password");
                    old_pass.requestFocus();
                }
                else if (new_pass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter the password",Toast.LENGTH_SHORT).show();

                }
                else if(confirm_pass.getText().toString().isEmpty()){
                    confirm_pass.setError("confirm password empty");
                    confirm_pass.requestFocus();

                }
                else if (String.valueOf(progressBar.getProgress()).equals("25")){
                    Toast.makeText(getApplicationContext(),"Weak Password",Toast.LENGTH_SHORT).show();
                }
                else if (!new_pass.getText().toString().equals(confirm_pass.getText().toString())){
                    confirm_pass.setError("Password Mismatch");
                    confirm_pass.requestFocus();
                }else{

                    resetApi reset=new resetApi();
                    try {
                        reset.resetPass(officer_reset_password.this,mobile.getText().toString(),old_pass.getText().toString(),new_pass.getText().toString(),token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }


        });
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
            startActivity(new Intent(getApplicationContext(), officer_Last10_agent.class));

        }


        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void updatePasswordStrengthView(String password) {

        TextView strengthView = (TextView) findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());
        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }
    private void pass_word(){
        new_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePasswordStrengthView(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}