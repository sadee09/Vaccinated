package com.samrina.storage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class verificationOtp extends AppCompatActivity {
    public static final String TAG = "TAG";


    private FirebaseAuth auth;
    String saveCurrentDate, saveCurrentTime, productRandomKey;
    ProgressBar progressB;
    String userID;
    String downloadImgUrl;
    EditText inputnumber1, inputnumber2, inputnumber3, inputnumber4, inputnumber5, inputnumber6;
    String getotpBackend,getname1, getname2, getage1, getgender1, getmobile1, getemail1,getpsw1,getaddress1;
    String atcURL;
    FirebaseFirestore fStore;
    private Uri mImageUri;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyenteredotp);

        auth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");



        final Button verifybuttonclick = findViewById(R.id.buttongetotp);


        inputnumber1 = findViewById(R.id.inputotp1);
        inputnumber2 = findViewById(R.id.inputotp2);
        inputnumber3 = findViewById(R.id.inputotp3);
        inputnumber4 = findViewById(R.id.inputotp4);
        inputnumber5 = findViewById(R.id.inputotp5);
        inputnumber6 = findViewById(R.id.inputotp6);

        TextView textView = findViewById(R.id.textmobile);
        textView.setText(String.format(
                "+977-%s", getIntent().getStringExtra("mobile")
        ));

        getotpBackend = getIntent().getStringExtra("backendotp");
        getname1 = getIntent().getStringExtra("firstname");
        getname2 = getIntent().getStringExtra("lastname");
        getage1 = getIntent().getStringExtra("age");
        getgender1 = getIntent().getStringExtra("gender");
        getmobile1 = getIntent().getStringExtra("mobile");
        getaddress1 = getIntent().getStringExtra("address");
        getemail1 = getIntent().getStringExtra("email");
        getpsw1 = getIntent().getStringExtra("psw");
        atcURL = getIntent().getStringExtra("imgUrl");

        final ProgressBar progressBarverifyotp = findViewById(R.id.progressbarCheck);

        verifybuttonclick.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!inputnumber1.getText().toString().trim().isEmpty() && !inputnumber2.getText().toString().trim().isEmpty() && !inputnumber3.getText().toString().trim().isEmpty() && !inputnumber4.getText().toString().trim().isEmpty() && !inputnumber5.getText().toString().trim().isEmpty() && !inputnumber6.getText().toString().trim().isEmpty())
                        {
                            String entercodeotp = inputnumber1.getText().toString() +
                                    inputnumber2.getText().toString() +
                                    inputnumber3.getText().toString() +
                                    inputnumber4.getText().toString() +
                                    inputnumber5.getText().toString() +
                                    inputnumber6.getText().toString();

                            if(getotpBackend!= null){
                                progressBarverifyotp.setVisibility(View.VISIBLE);
                                verifybuttonclick.setVisibility(View.INVISIBLE);

                                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                        getotpBackend, entercodeotp
                                );

                                userID = auth.getCurrentUser().getUid();

                                auth.createUserWithEmailAndPassword(getemail1,getpsw1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        progressBarverifyotp.setVisibility(View.GONE);
                                                        verifybuttonclick.setVisibility(View.VISIBLE);

                                                        if(task.isSuccessful()){

                                                            DocumentReference documentReference = fStore.collection("users").document(userID);
                                                            Map<String,Object> user =new HashMap<>();
                                                            user.put("firstName",getname1);
                                                            user.put("lastName",getname2);
                                                            user.put("email",getemail1);
                                                            user.put("age",getage1);
                                                            user.put("gender",getgender1);
                                                            user.put("phone",getmobile1);
                                                            user.put("address",getaddress1);
                                                            user.put("password",getpsw1);
                                                            user.put("image",atcURL);
                                                            System.out.println(atcURL);

                                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {

                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Log.d(TAG, "onSuccess:user is profile is Created for"+userID);

                                                                    startActivity(new Intent(verificationOtp.this, login.class));

                                                                }
                                                            });

                                                        }else{
                                                            Toast.makeText(verificationOtp.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                    }
                                });

                            }else{
                                Toast.makeText(verificationOtp.this, "Please check internet", Toast.LENGTH_SHORT).show();

                            }
                            Toast.makeText(verificationOtp.this, "otp verify", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(verificationOtp.this, "please enter all number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        numberotpmove();


        findViewById(R.id.textresendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+977" + textView.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        verificationOtp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(verificationOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                               getotpBackend = newbackendotp;
                                Toast.makeText(verificationOtp.this, "OTP sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });



    }

    private void numberotpmove() {

        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
