package com.ekattorit.messmanagement;

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

public class AddPayment extends AppCompatActivity {

    TextView datetext, showdate;
    DatePicker datePicker;
    EditText editDate, editPayment;
    Button bazarSubmit;
    String urlserver = "http://10.0.2.2:8080/MessServer/AddPayment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        datetext = (TextView) findViewById(R.id.datetext);
        showdate = (TextView) findViewById(R.id.showdate);
        datePicker = (DatePicker) findViewById(R.id.datepicker);
        editDate = (EditText) findViewById(R.id.editDate);
        editPayment = (EditText) findViewById(R.id.editPayment);
        bazarSubmit = (Button) findViewById(R.id.bazarSubmit);


        Date date = new Date();
        datetext.setText(date.toString());

        Calendar calender = Calendar.getInstance();

        datePicker.init(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                editDate.setText(i2 + "-" + i1 + "-" + i);
            }
        });


        bazarSubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(AddPayment.this, s, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(AddPayment.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                        ;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("db", mySession.dbname);
                        parameters.put("user", mySession.user);
                        parameters.put("date", editDate.getText().toString());
                        parameters.put("payment", editPayment.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(AddPayment.this);
                rQueue.add(request);
            }
        });
    }
}
