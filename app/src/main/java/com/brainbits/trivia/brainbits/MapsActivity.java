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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Vars
    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String WRITE_EXTERNAL = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String READ_EXTERNAL = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private boolean mPermissionsGranted = false;
    private Location mCurrentLocation;

    private String UsrNm;
    private String pass;

    private SessionManager manager;

    // Google maps API vars
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // Widgets
    private FloatingActionButton logout;
    private FloatingActionButton findme;
    private FloatingActionButton profile;
    private FloatingActionButton taskDone;

    // Dialogs
    private Dialog logOutDialog;
    private Dialog CorrectDialog;
    private Dialog WrongDialog;
    private Dialog MissionListDialog;
    private Dialog levelUpDialog;

    // Handles everything that is to happen when the Activity first starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.mCurrentLocation = new Location("");
        manager = new SessionManager(this);

        logOutDialog = new Dialog(this);
        CorrectDialog = new Dialog(this);
        WrongDialog = new Dialog(this);
        MissionListDialog = new Dialog(this);
        levelUpDialog = new Dialog(this);

        initMap();

        logout = (FloatingActionButton)findViewById(R.id.fab_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutPopUp();
            }
        });

        findme = (FloatingActionButton)findViewById(R.id.fab_find);
        findme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                Toast.makeText(MapsActivity.this, currentDateandTime, Toast.LENGTH_LONG).show();


            }
        });

        profile = (FloatingActionButton) findViewById(R.id.fab_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent x = new Intent(MapsActivity.this,GeneralActivity.class);
                startActivity(x);
            }
        });

        taskDone = (FloatingActionButton) findViewById(R.id.fab_settings);
        taskDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMission();
            }
        });


    }

    // Handles what happens after permissions are either granted or rejected
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

    // Handles everything that is to happen when the GoogleMap is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style_dark_blue));

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

    }

    //Manage dialog options
    private void logOutPopUp(){
        logOutDialog.setContentView(R.layout.dialog_logout);
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
                manager.logoutUser();
                final Intent i = new Intent(MapsActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logOutDialog.show();
    }

    //Check if the current task has been done correctly
    private void checkTask(String mission, JSONArray missions) {

        boolean done = false;
        String quest = null;

        for (int i = 0; i < missions.length(); i++){

            try {

                JSONObject tmp = missions.getJSONObject(i);
                String tmpname = tmp.getString(manager.MISSION_NAME_TAG);

                if (tmpname.equals(mission)) {

                    double lat = tmp.getDouble(manager.MISSION_LATITULE_TAG);
                    double lng = tmp.getDouble(manager.MISSION_LONGITUDE_TAG);

                    Location sol = new Location("");
                    sol.setLatitude(lat);
                    sol.setLongitude(lng);

                    getDeviceLocation();
                    float distance = getDistance(sol,mCurrentLocation);
                    float tolerance = 30;

                    if (distance < tolerance || distance == tolerance) {

                        done = true;
                        quest = tmp.getString(manager.MISSION_QUEST_TAG);

                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        if (done){

            boolean isLevelup = false;

            int current = manager.getRank();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            manager.completeMission(mission, quest, currentDateandTime);
            int newValue = manager.getRank();

            if (newValue > current) {

                isLevelup = true;

            }





            CorrectDialog.setContentView(R.layout.dialog_correct_location);
            Button ok = (Button)CorrectDialog.findViewById(R.id.correct_ok);

            final boolean finalIsLevelup = isLevelup;
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (finalIsLevelup) {

                        CorrectDialog.dismiss();
                        levelUp();

                    } else {
                        CorrectDialog.dismiss();
                    }
                }
            });

            CorrectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CorrectDialog.show();
        } else {

            WrongDialog.setContentView(R.layout.dialog_incorrect_location);
            Button ok = (Button)WrongDialog.findViewById(R.id.incorrect_ok);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WrongDialog.dismiss();
                }
            });

            WrongDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WrongDialog.show();

        }
    }

    // Gets the current device location
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
                            mCurrentLocation = current;
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

    // Move the map to a specified location and zoom by 'zoom' ammount
    private void goToLocation(Location location, int zoom) {
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        LatLng loc = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(loc, zoom);
        mMap.moveCamera(update);
    }

    // Initializes everything related to the GoogleMap and its use
    private void initMap () {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Requests user for the permissions the application requires for proper use
    private void getPermissions (){
        String[] permissions = {FINE_LOCATION,COARSE_LOCATION,WRITE_EXTERNAL,READ_EXTERNAL};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mPermissionsGranted = true;
            }else {
                ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);
        }

    }


    private void checkMission () {

        MissionListDialog.setContentView(R.layout.dialog_mission_list);
        Button ok = (Button)MissionListDialog.findViewById(R.id.dialog_mission_list_ok);
        ListView list = (ListView) MissionListDialog.findViewById(R.id.dialog_mission_list);

        final JSONArray Missionarray = manager.retrieveMissions();


        final ArrayList<String> mMissions = new ArrayList<>();

        for (int i = 0; i < Missionarray.length(); i++) {

            try {

                JSONObject json = Missionarray.getJSONObject(i);
                String mission_name = json.getString(manager.MISSION_NAME_TAG);
                mMissions.add(mission_name);

            } catch (JSONException e) {

                e.printStackTrace();
            }


        }

        final ArrayAdapter adapter = new ArrayAdapter<String>(MapsActivity.this,
                android.R.layout.simple_list_item_1,mMissions);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkTask(mMissions.get(position),Missionarray);
                MissionListDialog.dismiss();
            }
        });



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MissionListDialog.dismiss();
            }
        });


        MissionListDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MissionListDialog.show();
    }


    //return the distance between two locations
    private float getDistance(Location current, Location point){

        return current.distanceTo(point);

    }

    public void levelUp () {

        levelUpDialog.setContentView(R.layout.dialog_level_up);

        Button ok = (Button) levelUpDialog.findViewById(R.id.level_ok);
        TextView rank = (TextView) levelUpDialog.findViewById(R.id.level_name);
        ImageView rankImg = (ImageView) levelUpDialog.findViewById(R.id.level_image);

        int rank_num = manager.getRank();

        switch (rank_num){

            case 0: {

                rank.setText("Newbie");
                rankImg.setImageResource(R.drawable.rank_newbie);
                break;

            }

            case 1:{

                rank.setText("Private");
                rankImg.setImageResource(R.drawable.rank_private);
                break;

            }

            case 2:{

                rank.setText("Private First Class");
                rankImg.setImageResource(R.drawable.rank_private_first_class);
                break;

            }

            case 3: {

                rank.setText("Sergeant");
                rankImg.setImageResource(R.drawable.rank_sergeant);
                break;

            }

            case 4:{

                rank.setText("Sergeant First class");
                rankImg.setImageResource(R.drawable.rank_sergeant_first_class);
                break;

            }

            case 5:{

                rank.setText("First Sergeant");
                rankImg.setImageResource(R.drawable.rank_sergeant_first);
                break;

            }

            case 6:{

                rank.setText("Sergeant Command Major");
                rankImg.setImageResource(R.drawable.rank_sergeant_command_major);
                break;

            }



        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelUpDialog.dismiss();
            }
        });

        levelUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        levelUpDialog.show();

    }

}
