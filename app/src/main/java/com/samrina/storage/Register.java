package com.samrina.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "TAG";
    private static final int PICK_IMAGE_REQUEST = 1;

    String saveCurrentDate, saveCurrentTime, productRandomKey;




    EditText firstname;
    EditText lastname;
    EditText age;
    EditText phoneNum;
    EditText pw1;
    EditText pw2;
    EditText email;
    String sex, imgURLA;
    EditText address;
    Spinner spn;
    String gender[];
    Button register;
    FirebaseAuth Auth;
    ProgressBar progressBar;
    TextView signed,pw1error,pw2error;
    FirebaseFirestore fStore;
    String userID;
    TextView mButtonChooseImage;
    ImageView mImageView;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    String downloadImgUrl;
    String atcURL;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firstname = findViewById(R.id.regFirst);
        lastname = findViewById(R.id.regLast);
        age = findViewById(R.id.regage);
        phoneNum = findViewById(R.id.input_mobile_number);
        pw1 = findViewById(R.id.regPsw);
        pw2 = findViewById(R.id.regCnfrm);
        email = findViewById(R.id.regEmail);
        address = findViewById(R.id.regAddress);
        progressBar = findViewById(R.id.progressbarReg);
        register = findViewById(R.id.button_upload);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mImageView = findViewById(R.id.imageViewP);


        progressBar=findViewById(R.id.progressbar2);
        Auth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        spn = findViewById(R.id.regSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.my_selected_item);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        spn.setAdapter(adapter);
        spn.setOnItemSelectedListener(this);





        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = email.getText().toString().trim();
                String psw = pw1.getText().toString().trim();
                String confirm = pw2.getText().toString().trim();
                String firstName=  firstname.getText().toString().trim();
                String lastName = lastname.getText().toString().trim();
                String phone = phoneNum.getText().toString().trim();
                String ageNum = age.getText().toString().trim();
                String genderr = sex.trim();
                String adrs = address.getText().toString().trim();




                if(TextUtils.isEmpty(emailId)){
                    email.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(psw)){
                    pw1.setError("Password is required");
                    return;
                }
                if (psw.length()<6){
                    pw1.setError("Password is less than 6 characteristics");
                    return;
                }
                if(!psw.equals(confirm)){
                    pw2.setError("Password does not match!");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);


                Auth.createUserWithEmailAndPassword(emailId,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = Auth.getCurrentUser().getUid();




                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat currentData = new SimpleDateFormat("MMM DD, yyyy");
                            saveCurrentDate = currentData.format(calendar.getTime());


                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                            saveCurrentTime = currentTime.format(calendar.getTime());

                            productRandomKey = saveCurrentDate + saveCurrentTime;


                            StorageReference filepath = mStorageRef.child(mImageUri.getLastPathSegment() + productRandomKey);

                            final UploadTask uploadTask = filepath.putFile(mImageUri);


                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String message = e.toString();
                                    Toast.makeText(Register.this, "Error:" + message, Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(Register.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                        @Override
                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                            if(!task.isSuccessful()){
                                                throw
                                                        task.getException();
                                            }


                                            downloadImgUrl = filepath.getDownloadUrl().toString();
                                            return filepath.getDownloadUrl();
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if(task.isSuccessful()){
                                                downloadImgUrl = task.getResult().toString();
                                                atcURL = downloadImgUrl;

                                                    Toast.makeText(Register.this, "Image Saved to database Successfully", Toast.LENGTH_LONG).show();
                                                    Toast.makeText(Register.this, downloadImgUrl, Toast.LENGTH_LONG).show();

                                                    progressBar.setVisibility(View.VISIBLE);
                                                    register.setVisibility(View.INVISIBLE);

                                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                            "+977" + phoneNum.getText().toString(),
                                                            60,
                                                            TimeUnit.SECONDS,
                                                            Register.this,
                                                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                                @Override
                                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                                    progressBar.setVisibility(View.GONE);
                                                                    register.setVisibility(View.VISIBLE);

                                                                }

                                                                @Override
                                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                                    progressBar.setVisibility(View.GONE);
                                                                    register.setVisibility(View.VISIBLE);
                                                                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }

                                                                @Override
                                                                public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                                    progressBar.setVisibility(View.GONE);
                                                                    register.setVisibility(View.VISIBLE);

                                                                    Intent intent = new Intent(Register.this, verificationOtp.class);
                                                                    intent.putExtra("mobile", phoneNum.getText().toString());
                                                                    intent.putExtra("firstname", firstname.getText().toString());
                                                                    intent.putExtra("lastname", lastname.getText().toString());
                                                                    intent.putExtra("age", age.getText().toString());
                                                                    intent.putExtra("gender", spn.getSelectedItem().toString());
                                                                    intent.putExtra("imgUrl", atcURL.toString());
                                                                    intent.putExtra("email", email.getText().toString());
                                                                    intent.putExtra("psw", pw1.getText().toString());
                                                                    intent.putExtra("address", address.getText().toString());
                                                                    intent.putExtra("backendotp", backendotp);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                    );
                                                }



                                        }
                                    });
                                }
                            });

                        }else {
                            Toast.makeText(Register.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sex = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), sex, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
        }

    }

}


