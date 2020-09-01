package com.example.mdevt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class idValidation {
    String url="http://54.228.50.10/api/v1/id_passport_number_validate/";
    SharedPreferences validSharedpref;
    SharedPreferences.Editor validEditor;
    int flag;




    public void idValid(final Context context, String id_number,  final String acccesstoken) throws JSONException {
        JSONObject jobj=new JSONObject();
        jobj.put("id_or_passport_num",id_number);
        Log.e("id_or_passport_num",id_number);

        Log.e("jsonObject", jobj.toString());
        Log.e("token",acccesstoken);

        validSharedpref=context.getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);
        validEditor=validSharedpref.edit();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",response.toString());
                try {

                    String status=response.getString("validation");
                    if(status.equals("true")){
                        Toast.makeText(context,"id verified!!!",Toast.LENGTH_SHORT).show();
                        validEditor.putString("valid","yes");
                        validEditor.apply();



                        //officer_Rica_customer ob=new officer_Rica_customer();
                        //ob.id_number.setError("invalid id");

                    }
                    else {
                        //Toast.makeText(context,"ID Verified",Toast.LENGTH_SHORT).show();
                        Toast.makeText(context,"invalid ID",Toast.LENGTH_SHORT).show();
                        validEditor.putString("valid","no");
                        validEditor.apply();




                        //context.startActivity(new Intent(context, loggedin.class));
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
                headers.put("Authorization", "Bearer " + acccesstoken);
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(jsonReq);

    }
}
