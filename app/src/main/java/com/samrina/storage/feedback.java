package com.samrina.storage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class feedback extends AppCompatActivity {
    EditText ouremail, feedbackbody;
    TextView subject, arrow, firstname, lastname;
    Button button;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        db = FirebaseFirestore.getInstance();
        ouremail = findViewById(R.id.ouremail);
        firstname = findViewById(R.id.name);
        lastname = findViewById(R.id.last);
        feedbackbody = findViewById(R.id.feedbackbody);
        button = findViewById(R.id.button);
        arrow = findViewById(R.id.arrow);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(feedback.this, landingpage.class);
                startActivity(intent);
                finish();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Subject = firstname.getText().toString();
                String Body = feedbackbody.getText().toString();
                String ourEmail = ouremail.getText().toString();
                String[] email_divide = ourEmail.split(",");
                Intent button = new Intent(Intent.ACTION_SEND);
                button.putExtra(Intent.EXTRA_EMAIL, email_divide);
                button.putExtra(Intent.EXTRA_SUBJECT, Subject);
                button.putExtra(Intent.EXTRA_TEXT, Body);
                button.setType("feedbackbody/rfc822");
                button.setPackage("com.google.android.gm");
                startActivity(button);
            }
        });


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        firstname.setText(document.getString("firstName"));
                        lastname.setText(document.getString("lastName"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }
}




