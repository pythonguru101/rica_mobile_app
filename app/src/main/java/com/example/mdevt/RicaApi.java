package com.example.mdevt;

import android.content.Context;
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

public class RicaApi {
    String url="http://54.228.50.10/api/v1/rica_customer_register/";
    public void rica(final Context context, String network, String new_exist, String regiser, String sim,
                     String last4, String id_type, String country, String id_number,
                        String full_name, String surname,String address1,
                        String address2, String address3, String suburb, String postal_code,String city_town,String proof_of_address, final String accesstoken) throws JSONException {
        JSONObject jobj = new JSONObject();
        jobj.put("network", network);
        jobj.put("new_existing_subscriber", new_exist);
        jobj.put("register_with",regiser);
        jobj.put("sim",sim);
        jobj.put("last4",last4);
        jobj.put("id_type", id_type);
        jobj.put("country", country);
        jobj.put("id_number",id_number);
        jobj.put("full_name",full_name);
        jobj.put("surname",surname);
        jobj.put("country_code","");
        jobj.put("area_code","");
        jobj.put("dailing_number","");
        jobj.put("address1",address1);
        jobj.put("address2","");
        jobj.put("address3","");
        jobj.put("suburb",suburb);
        jobj.put("postal_code",postal_code);
        jobj.put("city_or_town",city_town);
        jobj.put("proof_of_address",proof_of_address);

        Log.e("aaaaaaa",jobj.toString());


        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",response.toString());
                try {
                    String status=response.getString("status");
                    String res=response.getString("response");
                    if(status.equals("success")){
                        Toast.makeText(context,"Rica customer Registered",Toast.LENGTH_SHORT).show();
                    }
                    if (res.equals("This rica customer was already created")){
                        Toast.makeText(context,"Registration has been already done!!!!",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"This rica customer was already created",Toast.LENGTH_SHORT).show();


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

