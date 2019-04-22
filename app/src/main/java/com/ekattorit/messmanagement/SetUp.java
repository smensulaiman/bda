package com.ekattorit.messmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetUp extends AppCompatActivity {

    Button setupmess,setuplogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        setupmess = (Button)findViewById(R.id.setupmess);
        setuplogin = (Button)findViewById(R.id.setuplogin);

        setupmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetUp.this,MessConnect.class));
            }
        });

        setuplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetUp.this,Login.class));
            }
        });
    }
}
