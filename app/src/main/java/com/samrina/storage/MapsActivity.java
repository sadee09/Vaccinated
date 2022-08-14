package com.samrina.storage;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.samrina.storage.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Address> listGeocoder;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private ActivityMapsBinding binding;
    ArrayList<LatLng> arrayList=new ArrayList<LatLng>();
    LatLng starhospital = new LatLng(27.68194209510884, 85.30287612611552);
    LatLng teku = new LatLng(27.69603963954379, 85.30615514584304);
    LatLng teaching = new LatLng(27.735623682706137, 85.33096885310243);
    LatLng kanti = new LatLng(27.734830485965748, 85.32838163916344);
    LatLng policehosp = new LatLng(27.731252058450625, 85.3232843242666);
    LatLng civilhosp = new LatLng(27.6864930913319, 85.33878233960839);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (isLocationPermissionGranted()){

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
            arrayList.add(starhospital);
            arrayList.add(teku);
            arrayList.add(teaching);
            arrayList.add(kanti);
            arrayList.add(policehosp);
            arrayList.add(civilhosp);

            try {
                listGeocoder = new Geocoder(this).getFromLocationName("Kathmandu Engineering College, Ganeshman Singh Road, Kathmandu", 1);
            } catch (Exception e) {
                e.printStackTrace();

            }
            double longituge = listGeocoder.get(0).getLongitude();
            double latitude = listGeocoder.get(0).getLatitude();
            Log.i("GOOGLE_MAP_TAG", "Address has longitude" +
                    ":::" + String.valueOf(longituge) + "Latitude" + String.valueOf(latitude));
        } else {
            requestLocationPermission();
        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i=0;i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Vaccination center"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));

        }


        // Add a marker in Sydney and move the camera

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



    }



    private boolean isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ){
            return true;
        }
        else {
            return false;
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }
}
