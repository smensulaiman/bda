package com.ekattorit.messmanagement;

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

public class DemoAllBazar extends AppCompatActivity {

    String urlserver = "http://10.0.2.2:8080/MessServer/AllBazar";
    TextView bazarname,total;
    Button calculate;
    String nab;
    int totalBazar=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_all_bazar);


        calculate = (Button) findViewById(R.id.calculate);
        bazarname = (TextView) findViewById(R.id.bazars);
        total = (TextView) findViewById(R.id.total);
        getBazar();
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameAndBazar [] = nab.split(",");

                for(String n:nameAndBazar){
                    if(n!=null){
                        String amount []=n.split(":");
                        //total.append(String.valueOf(amount.length));
                        if(amount.length>1) {
                            totalBazar += Integer.parseInt(amount[1].trim());
                        }
                    }
                    mySession.allbazar = totalBazar;
                }
                total.setText(String.valueOf(totalBazar));
                mySession.mealrate = (int) totalBazar/mySession.allmeal;
                int mealrate = mySession.mealrate;
                Toast.makeText(DemoAllBazar.this,"Current Meal Rate : "+mealrate,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getBazar() {

        StringRequest request = new StringRequest(Request.Method.POST,urlserver, new Response.Listener<String>(){

            @Override
            public void onResponse(String s) {
                if(s.contains("null")){
                }else {
                    nab = s;
                    bazarname.setText(nab);
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

        RequestQueue rQueue = Volley.newRequestQueue(DemoAllBazar.this);
        rQueue.add(request);
    }
}
