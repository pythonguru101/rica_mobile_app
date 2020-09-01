package com.example.mdevt.networking;

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
import com.example.mdevt.agents.agentscreen;
import com.example.mdevt.officer.officerscreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginApi {
    String url="http://54.228.50.10/api/v1/rica_user_login/";
    SharedPreferences roleSharedpref;
    SharedPreferences.Editor roleEditor;



    public void loginUser(final Context context, String username,  String password, final String acccesstoken) throws JSONException {
        JSONObject jobj=new JSONObject();
        jobj.put("username",username);
        jobj.put("password",password);
        Log.e("username", username);
        Log.e("password", password);
        Log.e("jsonObject", jobj.toString());
        Log.e("token",acccesstoken);

        roleSharedpref=context.getSharedPreferences("roleSharedpref",Context.MODE_PRIVATE);
        roleEditor=roleSharedpref.edit();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("response",response.toString());
                        try {

                                String status=response.getString("response");
                                String role=response.getString("role");
                                if(status.equals("success")){

                                    if (role.equals("rica officer")){
                                        Toast.makeText(context," Officer successful login ",Toast.LENGTH_SHORT).show();
                                        roleEditor.putString("role","officer");
                                        roleEditor.apply();
                                        context.startActivity(new Intent(context, officerscreen.class));

                                        //context.startActivity(new Intent(context, agentscreen.class));
                                    }
                                    else if (role.equals("sub agent" )){
                                        Toast.makeText(context,"sub agent successful login",Toast.LENGTH_SHORT).show();
                                        roleEditor.putString("role","agent");
                                        roleEditor.apply();
                                        context.startActivity(new Intent(context, agentscreen.class));




                                    }

                                }
                                else {
                                    Toast.makeText(context,"wrong credentials",Toast.LENGTH_SHORT).show();
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


/*
import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginApi{
    String url="http://54.228.50.10/api/v1/";
    public void loginUser(final Context context, String username, String password, final String acccesstoken){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiEndpoint apiService=retrofit.create(ApiEndpoint.class);
        Rica rica = new Rica(username, password);
        Call<Rica> call = apiService.login("Bearer "+acccesstoken,rica);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("success",response.body().toString());


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("failed",t.getMessage().toString());

            }
        });
    }

}

*/
