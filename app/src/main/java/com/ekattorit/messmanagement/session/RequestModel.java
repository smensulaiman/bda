package com.ekattorit.messmanagement.session;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestModel {
    String urlserver = "http://10.0.2.2:8080/MessServer/AllMeal";
    StringRequest request = new StringRequest(Request.Method.POST,urlserver, new Response.Listener<String>(){

        @Override
        public void onResponse(String s) {
            if(s.contains("null")){
            }else {
            }
        }
    },new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError volleyError) {
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("key", "value");
            return parameters;
        }
    };

    //RequestQueue rQueue = Volley.newRequestQueue(RequestModel.this);
               // rQueue.add(request);
}
