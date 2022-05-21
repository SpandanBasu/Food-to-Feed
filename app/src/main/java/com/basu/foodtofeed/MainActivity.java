package com.basu.foodtofeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef = database.getReference("DonateRequests");
    public static DatabaseReference myRefHotspots = database.getReference("HungerHotspots");
    public static ArrayList<DonateForm> donatorList=fetchDonations();
    public static ArrayList<HotSpotForm> hotspotList=fetchHotspots();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout donateFood= findViewById(R.id.donate);
        RelativeLayout locateHotspot= findViewById(R.id.hotspot);
        RelativeLayout healthyNutrition= findViewById(R.id.healthy);

        donateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DonateFoodActivity.class));
            }
        });
        locateHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HungerHotspotActivity.class));
            }
        });
        healthyNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HealthyNutritionActivity.class));
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.donation_orders:
                startActivity(new Intent(MainActivity.this, DonationOrdersListActivity.class));
                break;

            case R.id.hunger_list:
                startActivity(new Intent(MainActivity.this, HungerHotspotsListActivity.class));
                break;
        }
        return true;
    }


    public static ArrayList<DonateForm> fetchDonations() {
        ArrayList<DonateForm> arr = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    for(DataSnapshot snap2:snap.getChildren()){
                        DonateForm order= snap2.getValue(DonateForm.class);
                        //Toast.makeText(DonationOrdersListActivity.this, ""+name+" "+number, Toast.LENGTH_SHORT).show();
                        arr.add(order);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Do nothing
            }
        });
        //Toast.makeText(this, ""+arr, Toast.LENGTH_SHORT).show();
        return arr;
    }

    public static ArrayList<HotSpotForm> fetchHotspots() {
        ArrayList<HotSpotForm> arr = new ArrayList<>();
        myRefHotspots.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    for(DataSnapshot snap2:snap.getChildren()){
                        HotSpotForm order= snap2.getValue(HotSpotForm.class);
                        //Toast.makeText(DonationOrdersListActivity.this, ""+name+" "+number, Toast.LENGTH_SHORT).show();
                        arr.add(order);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Do nothing
            }
        });
        //Toast.makeText(this, ""+arr, Toast.LENGTH_SHORT).show();
        return arr;
    }
}