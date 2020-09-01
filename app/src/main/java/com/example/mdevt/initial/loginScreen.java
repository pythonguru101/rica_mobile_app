package com.example.mdevt.initial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mdevt.R;
import com.example.mdevt.agents.agentscreen;
import com.example.mdevt.networking.loginApi;
import com.example.mdevt.officer.officerscreen;
import com.example.mdevt.networking.tokenReq;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class loginScreen extends AppCompatActivity {
    AutoCompleteTextView tv1;

    ArrayList addArray = new ArrayList<>();


    TextInputEditText tv2;
    Button bt;
    TextView tv,bt1;
    ProgressBar pBar;
    SharedPreferences sharedPref;
    SharedPreferences loginpref;
    SharedPreferences roleSharedpref;
    SharedPreferences userName;
    SharedPreferences u;
    //SharedPreferences userStore;
    //private static final String STORE_USER="";

    //private ArrayAdapter<String> adapter;
    SharedPreferences.Editor userEditor, editor;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        tv = (TextView) findViewById(R.id.forgot);
        tv1 = (AutoCompleteTextView) findViewById(R.id.username);
        tv2 = (TextInputEditText) findViewById(R.id.password);
        bt = (Button) findViewById(R.id.login);
        bt1 = (TextView) findViewById(R.id.signup);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        userName = getSharedPreferences("userNameList", Context.MODE_PRIVATE);
        u=getSharedPreferences("u",Context.MODE_PRIVATE);

        Map map = userName.getAll();
        List<String> list = new ArrayList<String>(map.values());
        //userName.edit().clear().apply();

        addArray.add(tv1.getText());
        ArrayAdapter adapter = new ArrayAdapter(loginScreen.this, android.R.layout.simple_dropdown_item_1line, list);
        tv1.setAdapter(adapter);
        tv1.setThreshold(1);



        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        loginpref = getSharedPreferences("loginpref", Context.MODE_PRIVATE);
        editor = loginpref.edit();

        roleSharedpref = getSharedPreferences("roleSharedpref", Context.MODE_PRIVATE);
        if (loginpref.getBoolean("logStatus", false)) {
            if (roleSharedpref.getString("role", "00").equals("officer")) {
                startActivity(new Intent(getApplicationContext(), officerscreen.class));
                //startActivity(new Intent(getApplicationContext(), agentscreen.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), agentscreen.class));
                finish();
            }


        }


        tokenReq tq = new tokenReq();
        try {
            tq.getToken(this, pBar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (tv1.getText().toString().isEmpty()) {
                    tv1.setError("Username Empty");
                    tv1.requestFocus();

                } else if (tv2.getText().toString().isEmpty()) {
                    tv2.setError("password empty");
                    tv2.requestFocus();
                } else {


                    try {
                        loginApi obj = new loginApi();
                        //save();

                        //addArray.add(tv1.getText().toString());


                        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
                        token = sharedPref.getString("access_token", "00");
                        obj.loginUser(loginScreen.this, tv1.getText().toString(), tv2.getText().toString(), token);
                        editor.putBoolean("logStatus", true);
                        userEditor=userName.edit();
                        userEditor.putString("userName"+tv1.getText().toString(), tv1.getText().toString());
                        userEditor.apply();
                        editor.apply();
                        //user.putString(STORE_USER,tv1.getText().toString());
                        //saveArray(username,STORE_USER,getApplicationContext());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), signup.class));


            }
        });

    }


    public void reset_password(View view) {
        startActivity(new Intent(getApplicationContext(), forget.class));
    }

    /*public void save(){
        String usern=tv1.getText().toString();
        user=userStore.edit();
        user.putString(STORE_USER,usern);
        user.commit();
    }
    public void retrive(){
        if (userStore.contains(STORE_USER)){
            tv1.setText(userStore.getString(STORE_USER,""));
        }
    }

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        user=userStore.edit();
        user.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            user.putString(arrayName + "_" + i, array[i]);
        return user.commit();
    }

    public String[] loadArray(String arrayName, Context mContext) {
        int size = userStore.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = userStore.getString(arrayName + "_" + i, null);
        return array;
    }*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}

