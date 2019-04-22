package com.ekattorit.messmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekattorit.messmanagement.session.mySession;

import java.util.HashMap;
import java.util.Map;

public class AllMember extends AppCompatActivity {
    String urlserver = "http://10.0.2.2:8080/MessServer/AllMember";
    TextView members;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_member);

        members = (TextView)findViewById(R.id.members);

        getMember();
    }

    public void getMember() {
        StringRequest request = new StringRequest(Request.Method.POST,urlserver, new Response.Listener<String>(){

            @Override
            public void onResponse(String s) {
                if(s.contains("null")){
                }else {
                    members.setText(s);
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
                parameters.put("db", mySession.dbname);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(AllMember.this);
        rQueue.add(request);
    }
}
