package com.basu.foodtofeed;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import java.util.List;
import java.util.Locale;

public class DonateFoodActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_SETTINGS = 123;
    private static final int COARSE_LOCATION_PERMISSION = 1;
    private static final int FINE_LOCATION_PERMISSION = 2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("DonateRequests");
    EditText nameEdit, addressEdit, landmarkEdit, mobEdit, amountEdit;

    Double lat,lon;
    private String name, address, landmark, mobile, amount;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_food);
        Button btnSend = findViewById(R.id.btnSend);

        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Please allow Storage permission", Toast.LENGTH_SHORT).show();
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                //
            }else{
                ActivityCompat.requestPermissions(DonateFoodActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},COARSE_LOCATION_PERMISSION);
            }
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            Uri uri = Uri.fromParts("package",getPackageName(),null);
//            intent.setData(uri);
//            startActivityForResult(intent,REQUEST_PERMISSION_SETTINGS);
        }



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameEdit=findViewById(R.id.txtName);
                addressEdit=findViewById(R.id.txtAddress);
                landmarkEdit=findViewById(R.id.txtLandmark);
                mobEdit=findViewById(R.id.txtMob);
                amountEdit=findViewById(R.id.txtFeed);
                name=nameEdit.getText().toString().trim();
                address=addressEdit.getText().toString().trim();
                landmark=landmarkEdit.getText().toString().trim();
                mobile=mobEdit.getText().toString().trim();
                amount=amountEdit.getText().toString().trim();
                sendRequest(name,address,landmark,mobile,amount);
                clearEditField();
            }
        });
    }

    private void clearEditField() {
        nameEdit.setText("");
        addressEdit.setText("");
        landmarkEdit.setText("");
        mobEdit.setText("");
        amountEdit.setText("");
    }

    private void sendRequest(String name, String address, String landmark, String mobile, String amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        DonateForm donation= new DonateForm(name, mobile, address+" Landmark: "+landmark, Integer.parseInt(amount),currentDateandTime);
        myRef.child(mobile).child(currentDateandTime).setValue(donation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DonateFoodActivity.this, "PickUp Request sent Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DonateFoodActivity.this, "ERROR: Couldn't send request", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current Location", strReturnedAddress.toString());
            } else {
                Toast.makeText(this, "No Address returned!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current address", "Canont get Address!");
            Toast.makeText(this, "No Address returned!", Toast.LENGTH_SHORT).show();
        }
        return strAdd;
    }
}