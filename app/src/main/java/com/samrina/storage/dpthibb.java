package com.samrina.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class dpthibb extends AppCompatActivity {
    TextView Arrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpthibb);
        Arrow=findViewById(R.id.arrow);

        Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dpthibb.this, baby.class);
                startActivity(intent);
                finish();
            }
        });

    }
}