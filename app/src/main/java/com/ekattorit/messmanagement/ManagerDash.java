package com.ekattorit.messmanagement;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagerDash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dash);


        //for(memberModel myList:Store.list){
           // Toast.makeText(ManagerDash.this,myList.name+myList.number,Toast.LENGTH_SHORT).show();
        //}
    }
}
