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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class resetApi {

    String url="http://54.228.50.10/api/v1/password_reset/";
    public void resetPass(final Context context, String username, String old_password,String new_password ,final String accesstoken) throws JSONException {
        JSONObject jobj = new JSONObject();
        jobj.put("username", username);
        jobj.put("old_password", old_password);
        jobj.put("new_password",new_password);
        Log.e("username", username);
        Log.e("jsonObject", jobj.toString());


        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    String status = response.getString("status");
                    if (status.equals("Password was changed successfully"))
                    {
                        Toast.makeText(context," Your Password was changed",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"can't find mobile number",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



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

