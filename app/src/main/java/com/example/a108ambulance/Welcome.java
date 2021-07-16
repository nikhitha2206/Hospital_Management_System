package com.example.a108ambulance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

import static com.example.a108ambulance.R.id.drawer_layout;


public class Welcome extends AppCompatActivity {
    ImageView b1;
    EditText t1;
    EditText t2;
    ImageView i1;
    MainActivity m1 = new MainActivity();
    Button imort;
    ImageView imageView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PatientDetails patientDetails;
    public DrawerLayout drawerLayout;
    //public ActionBarDrawerToggle actionBarDrawerToggle;
    //NavigationView navigationView;
    //View header;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setActionBar(toolbar);
        //setNavigationViewListner();

        i1 = findViewById(R.id.emergencybutton);
        t1 = findViewById(R.id.view1);
        t2 = findViewById(R.id.view2);
        imort = findViewById(R.id.nav_camera);
        firebaseDatabase = FirebaseDatabase.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        //actionBarDrawerToggle = new ActionBarDrawerToggle(
                //this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       // drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();
        /*if(actionBarDrawerToggle!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        databaseReference = firebaseDatabase.getReference("PatientDetails");
        patientDetails = new PatientDetails();

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number_holder = t1.getText().toString();
                String address = t2.getText().toString();
                String phone_number = "8309295163";

                if (TextUtils.isEmpty(number_holder) && TextUtils.isEmpty(address)) {
                    Toast.makeText(Welcome.this, "Please Enter Number and Address", Toast.LENGTH_SHORT).show();
                } else {
                    Intent phone_intent = new Intent(Intent.ACTION_CALL);
                    phone_intent.setData(Uri.parse("tel:" + phone_number));
                    startActivity(phone_intent);
                    addDatatoFirebase(number_holder, address);
                }

            }
        });
    }

    private void addDatatoFirebase(String number, String address) {
        patientDetails.setNumber(number);
        patientDetails.setAddress(address);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                databaseReference.child(number).setValue("Ambulance Service");
                Toast.makeText(Welcome.this, "Calling....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Welcome.this, "Failed to reach you" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



   /* public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    /*private void setNavigationViewListner()
    {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }
    //@SuppressWarnings("StatementWithEmptyBody")
    /*public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(Welcome.this,ImportingImage.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        }  else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

}




