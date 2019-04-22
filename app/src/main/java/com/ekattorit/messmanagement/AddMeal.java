package com.ekattorit.messmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class AddMeal extends AppCompatActivity {

    TextView daymeal,nightmeal;
    Button dayplus,dayminuse,nightplus,nightminuse,add;
    String urlserver = "http://10.0.2.2:8080/MessServer/Addmeal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        daymeal = (TextView)findViewById(R.id.daymeal);
        nightmeal = (TextView)findViewById(R.id.nightmeal);


        dayplus = (Button)findViewById(R.id.dayplus);
        dayminuse = (Button)findViewById(R.id.dayminuse);
        nightplus = (Button)findViewById(R.id.nightplus);
        nightminuse = (Button)findViewById(R.id.nightminuse);
        add = (Button)findViewById(R.id.add);

        dayplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daymeal.setText(String.valueOf(Integer.parseInt(daymeal.getText().toString())+1));
            }
        });
        dayminuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int m = Integer.parseInt(daymeal.getText().toString())-1;
                if(m<0){
                    m=0;
                }
                daymeal.setText(String.valueOf(m));
            }
        });

        nightplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightmeal.setText(String.valueOf(Integer.parseInt(nightmeal.getText().toString())+1));
            }
        });
        nightminuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int m = Integer.parseInt(nightmeal.getText().toString())-1;
                if(m<0){
                    m=0;
                }
                nightmeal.setText(String.valueOf(m));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>(){

                    @Override
                    public void onResponse(String s) {
                        if(s.contains("null")){
                            Toast.makeText(AddMeal.this, "Incorrect Information", Toast.LENGTH_LONG).show();
                        }else {
                            //mySession.user = s.toString().trim();
                            Toast.makeText(AddMeal.this, "Meal Added" + s, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddMeal.this, DashBoard.class));
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(AddMeal.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("db", mySession.dbname);
                        parameters.put("name", mySession.user);
                        parameters.put("day", daymeal.getText().toString());
                        parameters.put("night", nightmeal.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(AddMeal.this);
                rQueue.add(request);

            }
        });
    }
}
