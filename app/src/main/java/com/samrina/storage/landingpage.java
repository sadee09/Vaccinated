package com.samrina.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class landingpage extends AppCompatActivity {
    TextView Person, Feedback, Notification,Map,baby,preg,covid;
    ImageView babypic,pregpic,covidpic,mappic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        Person = findViewById(R.id.person);
        Feedback = findViewById(R.id.feedback);
        Notification = findViewById(R.id.notification);
        Map=findViewById(R.id.map);
        baby=findViewById(R.id.babyvaccine);
        preg=findViewById(R.id.pregnancyvaccine);
        covid=findViewById(R.id.covid);
        babypic=findViewById(R.id.imageViewbaby);
        pregpic=findViewById(R.id.imageViewPreg);
        covidpic=findViewById(R.id.imageViewCovid);
        mappic=findViewById(R.id.imageViewmap);

        Person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

        });
        Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this, feedback.class);
                startActivity(intent);
                finish();

            }

        });
        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this, notification.class);
                startActivity(intent);
                finish();

            }

        });
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,MapsActivity .class);
                startActivity(intent);
                finish();

            }
        });
        mappic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,MapsActivity .class);
                startActivity(intent);
                finish();

            }
        });
        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,baby .class);
                startActivity(intent);
                finish();

            }
        });
        babypic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,baby .class);
                startActivity(intent);
                finish();

            }
        });
        preg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,preg .class);
                startActivity(intent);
                finish();

            }
        });
        pregpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,preg .class);
                startActivity(intent);
                finish();

            }
        });
        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,covid .class);
                startActivity(intent);
                finish();

            }
        });
        covidpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this,covid.class);
                startActivity(intent);
                finish();

            }
        });







    }
}