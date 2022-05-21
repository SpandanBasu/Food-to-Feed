package com.basu.foodtofeed;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.basu.foodtofeed.databinding.ActivitySplashScreenBinding;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_DISPLAY_LENGTH = 2000;
    private static final int REQUEST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility((View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN));
        getWindow().setStatusBarColor(Color.TRANSPARENT) ;
        setContentView(R.layout.activity_splash_screen);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_down);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(700);

        ImageView splash = (ImageView) findViewById(R.id.img_splash);
        splash.startAnimation(animation);

        if((ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED))
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, Manifest.permission.ACCESS_FINE_LOCATION))
            {

                ActivityCompat.requestPermissions(SplashScreen.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },REQUEST_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(SplashScreen.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                },REQUEST_CODE);
            }
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}