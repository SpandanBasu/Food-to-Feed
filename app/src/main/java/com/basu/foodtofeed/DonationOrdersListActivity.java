package com.basu.foodtofeed;

import static com.basu.foodtofeed.MainActivity.donatorList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DonationOrdersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    DonationAdapter donationAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("DonateRequests");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_orders_list);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_donations);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_donate_orders);
        loadDonations();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDonations();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadDonations() {

        //donatorList=fetchDonations();
        //Toast.makeText(this, ""+donatorList.get(0).getName()+"\n"+donatorList.get(1).getName(), Toast.LENGTH_SHORT).show();
        donationAdapter=new DonationAdapter(donatorList,this);
        recyclerView.setAdapter(donationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        donationAdapter.notifyDataSetChanged();

    }

    private ArrayList<DonateForm> fetchDonations() {
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
        //Toast.makeText(this, ""+donatorList, Toast.LENGTH_SHORT).show();
        return arr;
    }
}