package com.basu.foodtofeed;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {
    private ArrayList<DonateForm> donatorList;
    private Context context;
    BottomSheetDialog bottomSheetDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("DonateRequests");
    public DonationAdapter(ArrayList<DonateForm> donatorList, Context context) {
        this.donatorList = donatorList;
        this.context = context;
    }

    @NonNull
    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.ViewHolder holder, int position) {
        holder.donatorName.setText(donatorList.get(position).getName());
        holder.donatorAddress.setText(donatorList.get(position).getAddress());
        String time= donatorList.get(position).getTime().substring(0,5);
        holder.orderTime.setText(time);
        holder.menuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog= new BottomSheetDialog(context,R.style.BottomSheetTheme);
                View bsView= LayoutInflater.from(context).inflate(R.layout.food_item_bs_layout,
                        view.findViewById(R.id.bottom_sheet));

                bsView.findViewById(R.id.bs_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialogue= new AlertDialog.Builder(context);
                        alertDialogue.setTitle("Delete");
                        alertDialogue.setMessage("Do you really want to delete?");
                        alertDialogue.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myRef.child(donatorList.get(position).getNumber()).child(donatorList.get(position).getTime()).removeValue().addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    donatorList.remove(position);
                                                    notifyItemRemoved(position);
                                                    notifyItemRangeChanged(position,donatorList.size());
                                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(context, "Can not delete file", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                );
                            }
                        });
                        alertDialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialogue.show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bsView.findViewById(R.id.bs_properties).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialogue= new AlertDialog.Builder(context);
                        alertDialogue.setTitle("Details");
                        String one ="Donator Name: "+donatorList.get(position).getName();
                        String two="Address: "+donatorList.get(position).getAddress();
                        String three="Mobile Number: "+donatorList.get(position).getNumber();
                        String four="Time of Order: "+donatorList.get(position).getTime();
                        String five="Amount(of person): "+donatorList.get(position).getFoodAmount();

                        alertDialogue.setMessage(one+"\n\n"+two+"\n\n"+three+"\n\n"+four+"\n\n"+five);
                        alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialogue.show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bsView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return donatorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView menuMore;
        TextView donatorName,donatorAddress,orderTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuMore=(ImageView)itemView.findViewById(R.id.more_option);
            donatorName=(TextView)itemView.findViewById(R.id.donator_name);
            donatorAddress=(TextView)itemView.findViewById(R.id.donator_address);
            orderTime=(TextView)itemView.findViewById(R.id.order_time);
        }
    }
}
