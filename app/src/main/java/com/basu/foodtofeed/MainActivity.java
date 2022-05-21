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
    public static ArrayList<DonateForm> donatorList=fetchDonations();
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


    private static ArrayList<DonateForm> fetchDonations() {
        ArrayList<DonateForm> arr = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    for(DataSnapshot snap2:snap.getChildren()){
                        String name= snap2.child("name").getValue(String.class);
                        String address= snap2.child("address").getValue(String.class);
                        String amount= snap2.child("foodAmount").getValue(String.class);
                        String number=snap2.child("number").getValue(String.class);
                        String time=snap2.child("time").getValue(String.class);
                        DonateForm order= new DonateForm(name,number,address,amount,time);
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