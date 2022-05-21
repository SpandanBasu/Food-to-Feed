package com.basu.foodtofeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

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
}