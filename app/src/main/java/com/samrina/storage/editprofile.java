package com.samrina.storage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {

    public static final String TAG = "TAG";
    private static final int PICK_IMAGE_REQUEST = 1;

    String saveCurrentDate, saveCurrentTime, productRandomKey;


    EditText  newfname, newlname, newemail,newphone,newaddress;
    Button update;
    TextView changePic,arrow;
    TextView editTextfirstname,editTextlastname, editTextemail, editTextphoneNo, editTextaddress;

    FirebaseAuth fAuth;
    FirebaseFirestore db;
    String userID;
    private Uri mImageUri;
    ImageView mImageView;
    private StorageReference mStorageRef;
    String downloadImgUrl;
    String atcURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        db =FirebaseFirestore.getInstance();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        editTextfirstname = findViewById(R.id.fname);
        editTextlastname = findViewById(R.id.lname);
        editTextemail = findViewById(R.id.email);
        editTextphoneNo = findViewById(R.id.phone);
        editTextaddress = findViewById(R.id.regAddress);
        update = findViewById(R.id.update);
        newfname=findViewById(R.id.fnew);
        newlname=findViewById(R.id.lnew);
        mImageView = findViewById(R.id.pp);
        newphone=findViewById(R.id.newphone);
        newaddress=findViewById(R.id.regnewAddress);
        changePic = findViewById(R.id.changePic);
        arrow=findViewById(R.id.arrow);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editprofile.this,MainActivity.class);
                startActivity(intent);

            }

        });


        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        editTextphoneNo.setText(document.getString("phone"));
                        editTextfirstname.setText(document.getString("firstName"));
                        editTextlastname.setText(document.getString("lastName"));
                        editTextemail.setText(document.getString("email"));
                        editTextaddress.setText(document.getString("address"));
                        String imgUrl = document.getString("image");
                        Picasso.get().load(imgUrl).into(mImageView);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Opened");
                openFileChooser();
            }
        });




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = editTextfirstname.getText().toString();
                String fNew = newfname.getText().toString();
                if(fNew.isEmpty()){

                }else{
                    editTextfirstname.setText("");
                    newfname.setText("");
                    updatefirstName(firstname,fNew);


                }

                String lastname = editTextlastname.getText().toString();
                String lNew = newlname.getText().toString();
                if(lNew.isEmpty()){

                }else{
                    editTextlastname.setText("");
                    newlname.setText("");
                    updatelastName(lastname,lNew);


                }




                String phonee = editTextphoneNo.getText().toString();
                String newphonee = newphone.getText().toString();
                if(newphonee.isEmpty()){

                }else{
                    editTextphoneNo.setText("");
                    newphone.setText("");
                    updatephonee(phonee,newphonee);
                }

                String addresss = editTextaddress.getText().toString();
                String newaddresss = newaddress.getText().toString();
                if(newaddresss.isEmpty()){

                }else{
                    editTextaddress.setText("");
                    newaddress.setText("");
                    updateaddresss(addresss,newaddresss);
                }


                Intent intent = new Intent(editprofile.this, landingpage.class);
                startActivity(intent);
                finish();
            }

            private void updateaddresss(String addresss, String newaddresss) {
                Map<String,Object>userDetail4=new HashMap<>();
                userDetail4.put("address",newaddresss);
                db.collection("users")
                        .whereEqualTo("address", addresss)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() && !task.getResult().isEmpty()){

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("users")
                                    .document(documentID)
                                    .update(userDetail4)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            System.out.println("Updated");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Failed");
                                }
                            });

                        }


                    }
                });
            }

            private void updatephonee(String phonee, String newphonee) {
                Map<String,Object>userDetail3=new HashMap<>();
                userDetail3.put("phone",newphonee);
                db.collection("users")
                        .whereEqualTo("phone", phonee)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() && !task.getResult().isEmpty()){

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("users")
                                    .document(documentID)
                                    .update(userDetail3)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            System.out.println("Updated");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Failed");
                                }
                            });

                        }


                    }
                });
            }



            private void updatelastName(String lastname, String lNew) {
                Map<String,Object>userDetail1=new HashMap<>();
                userDetail1.put("lastName",lNew);
                db.collection("users")
                        .whereEqualTo("lastName", lastname)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() && !task.getResult().isEmpty()){

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("users")
                                    .document(documentID)
                                    .update(userDetail1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            System.out.println("Updated");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Failed");
                                }
                            });

                        }


                    }
                });
            }

        });
    }

    private void updatefirstName(String firstname, String fNew) {
        Map<String,Object>userDetail=new HashMap<>();
        userDetail.put("firstName",fNew);

        db.collection("users")
                .whereEqualTo("firstName", firstname)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful() && !task.getResult().isEmpty()){

                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    db.collection("users")
                            .document(documentID)
                            .update(userDetail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    System.out.println("Updated");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Failed");
                        }
                    });

                }


            }
        });



       }

    private void updatePic(String firstUrl) {


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
                Toast.makeText(editprofile.this, "Error:" + message, Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(editprofile.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
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

                            Toast.makeText(editprofile.this, "Image Saved to database Successfully", Toast.LENGTH_LONG).show();
                            Toast.makeText(editprofile.this, downloadImgUrl, Toast.LENGTH_LONG).show();

                            Map<String,Object>userDetail=new HashMap<>();
                            userDetail.put("image",atcURL);

                            db.collection("users")
                                    .whereEqualTo("image", firstUrl)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if(task.isSuccessful() && !task.getResult().isEmpty()){

                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        String documentID = documentSnapshot.getId();
                                        db.collection("users")
                                                .document(documentID)
                                                .update(userDetail)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        System.out.println("Updated");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println("Failed");
                                            }
                                        });

                                    }


                                }
                            });


                        }



                    }
                });
            }
        });



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