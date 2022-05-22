package com.basu.foodtofeed;

import static com.basu.foodtofeed.MainActivity.hotspotList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HungerHotspotsListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    HotspotAdapter hotspotAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("HungerHotspots");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunger_hotspots_list);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_hunger);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_hunger_hotspots);
        loadHotspots();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadHotspots();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void loadHotspots() {
        hotspotAdapter=new HotspotAdapter(hotspotList,this);
        recyclerView.setAdapter(hotspotAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setItemAnimator(null);
        hotspotAdapter.notifyDataSetChanged();

    }
}