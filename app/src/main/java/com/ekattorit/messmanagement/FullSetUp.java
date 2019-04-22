package com.ekattorit.messmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FullSetUp extends AppCompatActivity {

    Button  gobtn;
    EditText imember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_set_up);


        gobtn = (Button)findViewById(R.id.gobtn);
        imember = (EditText)findViewById(R.id.imember);

        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullSetUp.this,ManageMess.class);
                intent.putExtra("memberNumber",imember.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
