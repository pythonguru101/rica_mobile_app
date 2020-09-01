package com.example.mdevt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class forget extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Reset Password</font>"));
        textView=(TextView) findViewById(R.id.tvw);
        textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    public void returnLogin(View view) {
        startActivity(new Intent(getApplicationContext(), loginScreen.class));
    }
}