package com.ekattorit.messmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddBazar extends AppCompatActivity {

    TextView datetext;
    DatePicker datePicker;
    EditText editDate,editAmount;
    Button bazarSubmit;
    String urlserver = "http://10.0.2.2:8080/MessServer/AddBazar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bazar);

        datetext = (TextView)findViewById(R.id.datetext);
        datePicker = (DatePicker)findViewById(R.id.datepicker);
        editDate = (EditText)findViewById(R.id.editDate);
        editAmount = (EditText)findViewById(R.id.editAmount) ;
        bazarSubmit = (Button)findViewById(R.id.bazarSubmit);

        Date date = new Date();
        datetext.setText(date.toString());

        Calendar calender = Calendar.getInstance();

        datePicker.init(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                editDate.setText(i2+"-"+i1+"-"+i);

            }
        });

        bazarSubmit.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                final String day [] = editDate.getText().toString().split("-");
                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>(){

                    @Override
                    public void onResponse(String s) {

                            Toast.makeText(AddBazar.this,s, Toast.LENGTH_LONG).show();

                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(AddBazar.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("db",mySession.dbname);
                        parameters.put("date",day[0].toString().trim());
                        parameters.put("user",mySession.user);
                        parameters.put("amount", editAmount.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(AddBazar.this);
                rQueue.add(request);
            }
        });
    }
}
