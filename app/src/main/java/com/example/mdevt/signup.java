package com.example.mdevt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ybs.passwordstrengthmeter.PasswordStrength;

import org.json.JSONException;

public class signup extends AppCompatActivity {
    Button button,button1;
    TextInputEditText input1;
    TextInputEditText input2;
    TextInputEditText input3;
    ProgressBar progressBar;
    SharedPreferences sharedPref;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        button=(Button) findViewById(R.id.cancel);
        button1=(Button) findViewById(R.id.next);
        input1=(TextInputEditText) findViewById(R.id.sname);
        input2=(TextInputEditText) findViewById(R.id.spass);
        input3=(TextInputEditText) findViewById(R.id.sconfirm);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        token = sharedPref.getString("access_token", "00");

        pass_word();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), loginScreen.class));

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input1.getText().toString().isEmpty()){
                    input1.setError("Please enter username");
                    input1.requestFocus();
                }
                else if (input2.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"password empty",Toast.LENGTH_SHORT).show();

                }
                else if(input3.getText().toString().isEmpty()){
                    input3.setError("confirm password empty");
                    input3.requestFocus();

                }
                else if (String.valueOf(progressBar.getProgress()).equals("25")){
                    Toast.makeText(getApplicationContext(),"Weak Password",Toast.LENGTH_SHORT).show();
                }
                else if (!input2.getText().toString().equals(input3.getText().toString())){
                    input3.setError("Password Mismatch");
                    input3.requestFocus();
                }
                else
                    {
                    signUpApi ob=new signUpApi();
                    try
                    {
                        ob.signupUser(signup.this,input1.getText().toString(),input2.getText().toString(), token);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
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
        input2.addTextChangedListener(new TextWatcher() {
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