package com.ekattorit.messmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MessRegister extends AppCompatActivity {

    EditText messname,password;
    TextView log_link_signup;
    Button registerButton;
    String urlserver = "http://10.0.2.2:8080/MessServer/CreateDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_register);

        messname = (EditText)findViewById(R.id.log_input_email);
        password = (EditText)findViewById(R.id.log_input_password);

        registerButton =  (Button)findViewById(R.id.log_btn_login);
        log_link_signup = findViewById(R.id.log_link_signup);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("true")){
                            Toast.makeText(MessRegister.this, "Login Successful"+s, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MessRegister.this,s, Toast.LENGTH_LONG).show();
                        }
                        mySession.dbname = messname.getText().toString();
                        SharedPreferences sp = getSharedPreferences("DBInfo", MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("messname", messname.getText().toString());

                        Ed.commit();

                        startActivity(new Intent(MessRegister.this,FullSetUp.class));
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MessRegister.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("dbBox", messname.getText().toString());
                        parameters.put("phnBox", password.getText().toString());
                        parameters.put("pwdBox", password.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(MessRegister.this);
                rQueue.add(request);
            }
        });

        log_link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessRegister.this,Login.class));
            }
        });
    }
}
