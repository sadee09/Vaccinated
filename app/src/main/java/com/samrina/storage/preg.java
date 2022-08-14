package com.samrina.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.samrina.storage.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static android.content.ContentValues.TAG;

public class preg extends AppCompatActivity {
    public static final String TAG = "TAG";

    TextView td1,td2,arrow;
    ListView listViewData;
    ArrayAdapter<String> adapter;
    FirebaseFirestore fStore;
    String userID;
    private FirebaseAuth auth;

    String[] arrayPeliculas = {"Tetanus Vaccine (I)","Tetanus Vaccine (II)"};
    ArrayList<String> myvaccine =new ArrayList<>();
    ArrayList<String> needtoRemove =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preg);
        myvaccine.addAll(Arrays.asList(arrayPeliculas));
        td1=findViewById(R.id.td1);
        td2=findViewById(R.id.td2);
        arrow=findViewById(R.id.arrow);
        auth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,  myvaccine);
        listViewData.setAdapter(adapter);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(preg.this, landingpage.class);
                startActivity(intent);

            }

        });
        td1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(preg.this, td.class);
                startActivity(intent);

            }

        });
        td2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(preg.this, td.class);
                startActivity(intent);

            }

        });









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done){
            String itemSelected = "Selected Items: \n";
            for(int i=0; i<listViewData.getCount(); i++){
                if(listViewData.isItemChecked(i)){
                    String selectedString = (String) listViewData.getItemAtPosition(i);
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";
                    needtoRemove.add(selectedString);
                    listViewData.setItemChecked(i, false);
                    if(i== 0){
                        td1.setVisibility(View.GONE);
                    }else if(i ==1){
                        td2.setVisibility(View.GONE);
                    }
                }
            }
            myvaccine.removeAll(needtoRemove);
            adapter.notifyDataSetChanged();
            System.out.println(itemSelected);
            Toast.makeText(preg.this, itemSelected, Toast.LENGTH_LONG).show();
            Map<String,Object> user =new HashMap<>();
            user.put("PregDetails",itemSelected);
            fStore.collection("details").document(userID).update(user);

        }
        return  super.onOptionsItemSelected(item);
    }
}

