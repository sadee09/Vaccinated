package com.samrina.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class covid extends AppCompatActivity {
    public static final String TAG = "TAG";

    TextView verocell,covishield,pfizer,moderna,jandj,arrow,booster;
    ListView listViewData;
    ArrayAdapter<String> adapter;
    FirebaseFirestore fStore;
    String userID;
    private FirebaseAuth auth;
    String[] arrayPeliculas = {"Verocell","Covishield","Pfizer","Moderna","Johnson & Johnson","Booster"};
    ArrayList<String> myvaccine =new ArrayList<>();
    ArrayList<String> needtoRemove =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        myvaccine.addAll(Arrays.asList(arrayPeliculas));
        verocell=findViewById(R.id.verocell);
        covishield=findViewById(R.id.covishield);
        pfizer=findViewById(R.id.pfizer);
        moderna=findViewById(R.id.moderna);
        jandj=findViewById(R.id.jj);
        arrow=findViewById(R.id.arrow);
        auth = FirebaseAuth.getInstance();
        booster=findViewById(R.id.booster);
        fStore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, myvaccine);
        listViewData.setAdapter(adapter);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(covid.this, landingpage.class);
                startActivity(intent);

            }

        });


        verocell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(covid.this, verocell.class);
                startActivity(intent);

            }

        });
        booster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(covid.this, booster.class);
                startActivity(intent);

            }

        });



        covishield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(covid.this, Covishield.class);
                startActivity(intent);
                finish();

            }

        });

        pfizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(covid.this, pfizer.class);
                startActivity(intent);
                finish();

            }

        });

        jandj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(covid.this, JandJ.class);
                startActivity(intent);
                finish();

            }

        });

        moderna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(covid.this, moderna.class);
                startActivity(intent);
                finish();

            }

        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_done) {
            String itemSelected = "Selected Items: \n";
            for (int i = 0; i < listViewData.getCount(); i++) {
                if (listViewData.isItemChecked(i)) {
                    String selectedString = (String) listViewData.getItemAtPosition(i);
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";
                    needtoRemove.add(selectedString);
                    listViewData.setItemChecked(i, false);
                    if(i== 0){
                        verocell.setVisibility(View.GONE);
                    }else if(i ==1){
                        covishield.setVisibility(View.GONE);
                    }else if(i ==2){
                        pfizer.setVisibility(View.GONE);
                    }else if(i ==3){
                        moderna.setVisibility(View.GONE);
                    }else if(i ==4){
                        jandj.setVisibility(View.GONE);
                    }else if(i ==5){
                        booster.setVisibility(View.GONE);
                    }
                }
            }
            myvaccine.removeAll(needtoRemove);
            adapter.notifyDataSetChanged();
            System.out.println(itemSelected);
            Toast.makeText(covid.this, itemSelected, Toast.LENGTH_LONG).show();
            Map<String, Object> user = new HashMap<>();
            user.put("CovidDetails", itemSelected);
            fStore.collection("details").document(userID).update(user);

        }
        return super.onOptionsItemSelected(item);
    }
}