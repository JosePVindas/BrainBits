package com.brainbits.trivia.brainbits;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private FloatingActionButton logout;
    private FloatingActionButton findme;

    private Dialog logOutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        logOutDialog = new Dialog(this);
        //getPermissions();
        initMap();

        logout = (FloatingActionButton)findViewById(R.id.fab_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToActivity("LogOut");
                logOutPopUp();
            }
        });

        findme = (FloatingActionButton)findViewById(R.id.fab_find);
        findme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }

        getPermissions();

        if (mPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        // Go to the general TEC location
        //goToLocation(9.857247, -83.9145017, 15);

    }

    //Manage dialog options
    private void logOutPopUp(){
        logOutDialog.setContentView(R.layout.logout_dialog);
        Button dismiss = (Button)logOutDialog.findViewById(R.id.no_btn);
        Button accept = (Button)logOutDialog.findViewById(R.id.yes_btn);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutDialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity("LogOut");
            }
        });

        logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logOutDialog.show();
    }

    private void getDeviceLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{

            if (mPermissionsGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"Successful");
                            Location current = (Location) task.getResult();
                            goToLocation(current,15);
                        } else {
                            Log.d(TAG,"Unsuccesful");
                            Toast.makeText(MapsActivity.this,"Location unavailable",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        } catch(SecurityException e) {
            Log.d(TAG,e.getMessage());
        }
    }

    // Move the map to a specified location
    private void goToLocation(double lat, double lng) {
        LatLng location = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(location);
        mMap.moveCamera(update);
    }

    // Move the map to a specified location and zoom by 'zoom' ammount.
    private void goToLocation(double lat, double lng, int zoom) {
        LatLng location = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, zoom);
        mMap.moveCamera(update);
    }

    // Move the map to a specified location and zoom by 'zoom' ammount.
    private void goToLocation(Location location, int zoom) {
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        LatLng loc = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(loc, zoom);
        mMap.moveCamera(update);
    }

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final String WRITE_EXTERNAL = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String READ_EXTERNAL = Manifest.permission.READ_EXTERNAL_STORAGE;

    private static final int PERMISSION_REQUEST_CODE = 1234;
    private boolean mPermissionsGranted = false;

    private void initMap () {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getPermissions (){
        String[] permissions = {FINE_LOCATION,COARSE_LOCATION,WRITE_EXTERNAL,READ_EXTERNAL};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this.getApplicationContext(),WRITE_EXTERNAL) == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this.getApplicationContext(),READ_EXTERNAL) == PackageManager.PERMISSION_GRANTED){
                        mPermissionsGranted = true;
                    } else {
                        ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);
                    }
                }else {
                    ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);
                }
            }else {
                ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionsGranted = false;

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mPermissionsGranted = false;
                            return;
                        }
                    }
                    mPermissionsGranted = true;
                    //initialize map
                }
            }
        }
    }

    private void goToActivity(String activity){
        switch(activity){
            case"LogOut": {
                final Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
            }

        }
    }
}
