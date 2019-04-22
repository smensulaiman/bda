package com.ekattorit.messmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MessConnect extends AppCompatActivity {
    EditText messName, messPhoneBox, messPasswordBox;
    String name, phone, pass;
    Button connectButton;
    String urlserver = "http://10.0.2.2:8080/MessServer/MessAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_connect);

        messName = (EditText) findViewById(R.id.messName);
        messPhoneBox = (EditText) findViewById(R.id.messPhoneBox);
        messPasswordBox = (EditText) findViewById(R.id.messPasswordBox);
        connectButton = (Button) findViewById(R.id.connectButton);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = messName.getText().toString();
                phone = messPhoneBox.getText().toString();
                pass = messPasswordBox.getText().toString();

                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        if (s.contains("null")) {
                        } else {
                            startActivity(new Intent(MessConnect.this,FullSetUp.class));
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("dbBox", messName.getText().toString().trim());
                        parameters.put("phnBox", messPhoneBox.getText().toString().trim());
                        parameters.put("pwdBox", messPasswordBox.getText().toString().trim());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(MessConnect.this);
                rQueue.add(request);

                //startActivity(new Intent(MessConnect.this,FullSetUp.class));
//                dbBox = request.getParameter("dbBox");
//                phnBox = request.getParameter("phnBox");
//                pwdBox = request.getParameter("pwdBox");
            }
        });

    }

}
