package com.basu.foodtofeed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class HealthyNutritionActivity extends AppCompatActivity {
    LinearLayout form;
    EditText weightEdit,heightEdit,genderEdit;
    double weight,height;
    boolean male=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_nutrition);
        weightEdit=findViewById(R.id.txtWeight);
        heightEdit=findViewById(R.id.txtHeight);
        genderEdit=findViewById(R.id.txtGender);
        Button calc= findViewById(R.id.btnCalc);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight=Double.parseDouble(weightEdit.getText().toString());
                height=Double.parseDouble(heightEdit.getText().toString());
                male= genderEdit.getText().toString().equals("M");
                weightEdit.setText("");
                heightEdit.setText("");
                genderEdit.setText("");
                double ibw=calculateBodyWeight(height, male);
                double ibmi=calculateBodyMassIndex(height, ibw, male);
                double abmi=calculateBodyMassIndex(height, weight, male);
                AlertDialog.Builder alertDialogue= new AlertDialog.Builder(HealthyNutritionActivity.this);
                alertDialogue.setTitle("Calculated Measurements");
                String one=String.format("Ideal Body Weight: %.2f",ibw);
                String two=String.format("Ideal Body Mass Index: %.2f",ibmi);;
                String three=String.format("Actual Body Mass Index: %.2f",abmi);
                String word=weight>ibw?"loose":"gain";
                double desiredWeightChange=Math.abs(weight-ibw);
                String DWC=String.format("%.2f",desiredWeightChange);
                String four;
                if(abmi<18.5){
                    four="UNDERWEIGHT!";
                }else if(abmi>=18.5 && abmi<=24.9){
                    four="NORMAL or IDEAL";
                }else if(abmi>=25 && abmi<=29.9){
                    four="OVERWEIGHT!";
                }else{
                    four="OBESE!";
                }
                String five="You should "+word+" "+DWC+" Kgs of weight!";
                alertDialogue.setMessage("\n"+one+"\n\n"+two+"\n\n"+three+"\n\n"+"Body State: "+four+"\n\n"+five);
                alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialogue.show();
            }
        });
    }
    private double calculateBodyWeight(double height, boolean male) {
        double ibw=0;
        if(male){
            ibw= 50 + (0.91 * (height-152.4));
        }else{
            ibw= 45.5 + (0.91* (height-152.4));
        }
        return ibw;
    }
    private double calculateBodyMassIndex(double height, double weight, boolean male) {
        double bmi=(weight/Math.pow(height,2))*10000;
        return bmi;

    }

}