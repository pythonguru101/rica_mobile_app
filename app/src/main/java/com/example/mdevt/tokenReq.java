package com.example.mdevt;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class tokenReq {
    String url="http://54.228.50.10/api/v1/o/token/";


    public void getToken(Context context, final ProgressBar pBar) throws JSONException {
        final SharedPreferences sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor =sharedPref.edit();
        JSONObject jobj = new JSONObject();
        jobj.put("grant_type", "password");
        jobj.put("username", "surajbohra210@gmail.com");
        jobj.put("client_id","surajbohra210@gmail.com");
        jobj.put("client_secret", "");
        jobj.put("password", "durkfjrj");

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String t =response.getString("access_token");
                    editor.putString("access_token", t);
                    editor.apply();
                    pBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(request);
    }
}
