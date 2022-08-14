package com.samrina.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class notification extends AppCompatActivity {

    String title, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        title = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");

        System.out.println(title);
    }
}