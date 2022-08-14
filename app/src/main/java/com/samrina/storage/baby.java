package com.samrina.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
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

public class baby extends AppCompatActivity {
    public static final String TAG = "TAG";


    ListView listViewData;
    TextView arrow,bcg,pcv1,pcv2,dpthhib1,dpthhib2,dpthhib3,polio1,polio2,polio3,fipv1,fipv2,measles1,measles2,japanese,pcv3;
    ArrayAdapter<String> adapter;
    FirebaseFirestore fStore;
    String userID;
    private FirebaseAuth auth;
    String[] arrayPeliculas = {"Bacillus Calmette- Guerin", "Haemophilus influenzae,DPT,Hepatitis I","Polio I","Pneumococcal conjugate Vaccination (PCV) I","Fractional-Dose Inactivated Poliovirus Vaccine(FIPV) I", "Haemophilus influenzae,DPT,Hepatitis II","Polio2","Pneumococcal conjugate Vaccination (PCV) II" ,"Haemophilus influenzae,DPT,Hepatitis III", "Polio3",  "Fractional-Dose Inactivated Poliovirus Vaccine(FIPV)(Stalk)", "Measles-rubella I","Pneumococcal conjugate Vaccination (PCV) III", "Japanese Encephalitis","Measles-rubella2"};
    ArrayList<String> myvaccine =new ArrayList<>();
    ArrayList<String> needtoRemove =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myvaccine.addAll(Arrays.asList(arrayPeliculas));
        setContentView(R.layout.activity_baby);
        bcg=findViewById(R.id.bcg);
        pcv1=findViewById(R.id.pcv1);
        pcv2=findViewById(R.id.pcv2);
        dpthhib1=findViewById(R.id.dpthibb1);
        dpthhib2=findViewById(R.id.dpthibb2);
        dpthhib3=findViewById(R.id.dpthibb3);
        polio1=findViewById(R.id.polio1);
        polio2=findViewById(R.id.polio2);
        polio3=findViewById(R.id.polio3);
        fipv1=findViewById(R.id.fipv1);
        fipv2=findViewById(R.id.fipv2);
        measles1=findViewById(R.id.measlesrubella1);
        measles2=findViewById(R.id.measlesrubella2);
        japanese=findViewById(R.id.japanese);
        pcv3=findViewById(R.id.pcv3);
        arrow=findViewById(R.id.arrow);

        auth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();
        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, myvaccine);
        listViewData.setAdapter(adapter);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, landingpage.class);
                startActivity(intent);

            }

        });
        bcg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, BCG.class);
                startActivity(intent);

            }

        });
        pcv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, pcv.class);
                startActivity(intent);

            }

        });
        pcv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, pcv.class);
                startActivity(intent);

            }

        });
        pcv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, pcv.class);
                startActivity(intent);

            }

        });
        dpthhib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, dpthibb.class);
                startActivity(intent);

            }

        });
        dpthhib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, dpthibb.class);
                startActivity(intent);

            }

        });
        dpthhib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, dpthibb.class);
                startActivity(intent);

            }

        });
        polio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, Polio.class);
                startActivity(intent);

            }

        });
        polio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, Polio.class);
                startActivity(intent);

            }

        });
        polio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, Polio.class);
                startActivity(intent);

            }

        });
        fipv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, FIPV.class);
                startActivity(intent);

            }

        });
        fipv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, FIPV.class);
                startActivity(intent);

            }

        });
        measles1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, measles.class);
                startActivity(intent);

            }

        });
        measles2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, measles.class);
                startActivity(intent);

            }

        });
        japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baby.this, japanese.class);
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
                if(listViewData.isItemChecked(i)) {
                    String selectedString = (String) listViewData.getItemAtPosition(i);
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";
                    needtoRemove.add(selectedString);
                    listViewData.setItemChecked(i, false);
                    if (i == 0) {
                        bcg.setVisibility(View.GONE);
                    } else if (i == 1) {
                        dpthhib1.setVisibility(View.GONE);
                    } else if (i == 2) {
                        polio1.setVisibility(View.GONE);
                    } else if (i == 3) {
                        pcv1.setVisibility(View.GONE);
                    } else if (i == 4) {
                        fipv1.setVisibility(View.GONE);
                    } else if (i == 5) {
                        dpthhib2.setVisibility(View.GONE);

                    } else if (i == 6) {
                        polio2.setVisibility(View.GONE);
                    } else if (i == 7) {
                        pcv2.setVisibility(View.GONE);
                    } else if (i == 8) {
                        dpthhib3.setVisibility(View.GONE);
                    } else if (i == 9) {
                        polio3.setVisibility(View.GONE);
                    } else if (i == 10) {
                        fipv2.setVisibility(View.GONE);
                    } else if (i == 11) {
                        measles1.setVisibility(View.GONE);
                    } else if (i == 12) {
                        pcv3.setVisibility(View.GONE);
                    } else if (i == 13) {
                        japanese.setVisibility(View.GONE);
                    } else if (i == 14) {
                        measles2.setVisibility(View.GONE);
                    }
                }
            }
            myvaccine.removeAll(needtoRemove);
            adapter.notifyDataSetChanged();
            System.out.println(itemSelected);
            Toast.makeText(baby.this, itemSelected, Toast.LENGTH_LONG).show();
            Map<String, Object> map = new HashMap<>();
            map.put("babyDetails",itemSelected);
            fStore.collection("details").document(userID).set(map);

        }
        return  super.onOptionsItemSelected(item);
    }
}