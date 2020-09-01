package com.example.mdevt.networking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mdevt.officer.officerscreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signUpApi{
    String url="http://54.228.50.10/api/v1/rica_user_signup/";
    public void signupUser(final Context context, String username, String password, final String accesstoken) throws JSONException {
        JSONObject jobj = new JSONObject();
        jobj.put("mobile_number", username);
        jobj.put("password", password);
        jobj.put("name","");
        jobj.put("id_photo", "");
        jobj.put("address", "");
        jobj.put("is_checked", "False");
        Log.e("username", username);
        Log.e("password", password);
        Log.e("jsonObject", jobj.toString());


        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                        String status=response.getString("status");
                        String officer=response.getString("is_checked");
                        if(status.equals("success"))
                        {
                            Toast.makeText(context,"your sign up is successful.",Toast.LENGTH_SHORT).show();
                            if (officer.equals("True")){
                                context.startActivity(new Intent(context, officerscreen.class));
                                Toast.makeText(context,"Welcome Officer",Toast.LENGTH_SHORT).show();
                            }
                            else if(officer.equals("False")){
                                Toast.makeText(context,"Your application is pending approval",Toast.LENGTH_SHORT).show();
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"user already exist",Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(jsonReq);

    }
}
