package com.ekattorit.messmanagement;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaCas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekattorit.messmanagement.session.Store;
import com.ekattorit.messmanagement.session.mySession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageMess extends AppCompatActivity {

    int memberNumber;
    Button submit;
    ArrayList<memberModel> memberList;
    Dialog dialog;
    String urlserver = "http://10.0.2.2:8080/MessServer/InsertAllMembers";
    String messname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_mess);


        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

        messname = sp1.getString("messname", null);

        memberList = new ArrayList<>();
        memberNumber = Integer.parseInt(getIntent().getStringExtra("memberNumber"));
        Toast.makeText(ManageMess.this, "Total member :"+memberNumber, Toast.LENGTH_LONG).show();

        submit = (Button)findViewById(R.id.submit);
        dialog = new Dialog(ManageMess.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.member_entry);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        final TextView member_number = (TextView) dialog.findViewById(R.id.member_number);
        final EditText membername = (EditText) dialog.findViewById(R.id.editname);
        final EditText membernumber = (EditText) dialog.findViewById(R.id.editnumber);
        Button submitmember = (Button) dialog.findViewById(R.id.submit);

        submitmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = membername.getText().toString();
                String number = membernumber.getText().toString();

                if (!name.equals("") && !number.equals("")) {
                    memberList.add(new memberModel(name,number));
                    memberNumber--;
                    if(memberNumber==0){
                        StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>(){

                            @Override
                            public void onResponse(String s) {
                                Toast.makeText(ManageMess.this,"Response :"+s, Toast.LENGTH_SHORT).show();
                            }
                        },new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(ManageMess.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("len",String.valueOf(memberList.size()));
                                parameters.put("db", mySession.dbname);
                                memberModel model;
                                for(int i=0;i<memberList.size();i++){
                                    parameters.put("name"+i,memberList.get(i).getName());
                                    parameters.put("number"+i,memberList.get(i).getNumber());
                                }

                                return parameters;
                            }
                        };

                        RequestQueue rQueue = Volley.newRequestQueue(ManageMess.this);
                        rQueue.add(request);
                        dialog.dismiss();
                        Intent intent = new Intent(ManageMess.this,DashBoard.class);
                        startActivity(intent);
                        }else {
                        membername.setText("");
                        membernumber.setText("");
                    }

                    }

                }

        });
    }

    private ArrayList<Item> generateItemsList() {
        ArrayList<Item> list = new ArrayList<>();

        for (int i=0;i<memberNumber;i++){

            list.add(new Item("name"));
        }

        return list;
    }
}
