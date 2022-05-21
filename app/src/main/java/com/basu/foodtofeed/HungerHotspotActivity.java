package com.basu.foodtofeed;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HungerHotspotActivity extends AppCompatActivity {
    private static final int COARSE_LOCATION_PERMISSION = 1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("HungerHotspots");
    EditText addressEdit, landmarkEdit, amountEdit;
    private String address, landmark, amount;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunger_hotspot);
        Button btnMark = findViewById(R.id.btnMark);
        addressEdit=findViewById(R.id.txtAddress_hotspot);
        landmarkEdit=findViewById(R.id.txtLandmark_hotspot);
        amountEdit=findViewById(R.id.txt_number_people);
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Please allow Storage permission", Toast.LENGTH_SHORT).show();
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                //
            }else{
                ActivityCompat.requestPermissions(HungerHotspotActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},COARSE_LOCATION_PERMISSION);
            }
        }
        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsNotEmpty()){
                    address=addressEdit.getText().toString().trim();
                    landmark=landmarkEdit.getText().toString().trim();
                    amount=amountEdit.getText().toString().trim();
                    markLocation(address,landmark,amount);
                    clearEditField();
                }else{
                    Toast.makeText(HungerHotspotActivity.this, "Empty fields are not Allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean fieldsNotEmpty() {
        if(addressEdit.getText().toString().equals("") || amountEdit.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void clearEditField() {
        addressEdit.setText("");
        landmarkEdit.setText("");
        amountEdit.setText("");
    }

    private void markLocation(String address, String landmark,String amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        HotSpotForm hotspot= new HotSpotForm(address+" Landmark: "+landmark, amount, currentDateandTime);
        myRef.child(address).child(currentDateandTime).setValue(hotspot).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(HungerHotspotActivity.this, "HotSpot Marked Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(HungerHotspotActivity.this, "ERROR: Couldn't mark HotSpot", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}