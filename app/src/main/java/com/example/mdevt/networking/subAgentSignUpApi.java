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

public class subAgentSignUpApi {
    String url="http://54.228.50.10/api/v1/sub_agent_signup/";
    public void subsignupUser(final Context context, String mobile_number, String name,String surname,String id_passport_number,String id_passport_expiry,String address1,
                              String address2,String address3,String postal_code, final String accesstoken) throws JSONException {
        JSONObject jobj = new JSONObject();
        jobj.put("mobile_number", mobile_number);
        jobj.put("name", name);
        jobj.put("surname",surname);
        jobj.put("id_passport_number",id_passport_number);
        jobj.put("id_passport_expiry_date",id_passport_expiry);
        jobj.put("photo_of_id","");
        jobj.put("address1", address1);
        jobj.put("address2",address2);
        jobj.put("address3",address3);
        jobj.put("postal_code",postal_code);
        jobj.put("photo_of_proof","");
        Log.e("aaaaaaa",jobj.toString());


        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",response.toString());
                try {
                    String status=response.getString("status");
                    if(status.equals("success")){
                        Toast.makeText(context,"Sub Agent Created",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Agent already exist",Toast.LENGTH_SHORT).show();


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
