package com.basu.foodtofeed;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HotspotAdapter extends RecyclerView.Adapter<HotspotAdapter.ViewHolder> {
    private ArrayList<HotSpotForm> hotspotList;
    private Context context;
    BottomSheetDialog bottomSheetDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("HungerHotspots");

    public HotspotAdapter(ArrayList<HotSpotForm> hotspotList, Context context) {
        this.hotspotList = hotspotList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotspotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotspot_list_item,parent,false);
        return new HotspotAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotspotAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.hotspotAddress.setText(hotspotList.get(position).getAddress());
        holder.hotspotAmount.setText(hotspotList.get(position).getFoodAmount());
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
                                HotSpotForm hotspotItem= hotspotList.get(position);
                                try{
                                    hotspotList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position,hotspotList.size());
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                }catch(Exception e){
                                    Log.e("Error Exception","!!!!!!!!!!!");
                                }
                                String arrT[]=hotspotItem.getAddress().split("Landmark");
                                String key=arrT[0].trim();
                                Toast.makeText(context, key, Toast.LENGTH_SHORT).show();
                                myRef2.child(key).child(hotspotItem.getTime()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Deleted Success", Toast.LENGTH_SHORT).show();
                                    }
                                });
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
                        String one="Address: "+hotspotList.get(position).getAddress();
                        String two="Time of Marking: "+hotspotList.get(position).getTime();
                        String three="Amount(of person): "+hotspotList.get(position).getFoodAmount();

                        alertDialogue.setMessage(one+"\n\n"+two+"\n\n"+three);
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
        return hotspotList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView menuMore;
        TextView hotspotAddress,hotspotAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuMore=(ImageView)itemView.findViewById(R.id.more_option);
            hotspotAddress=(TextView)itemView.findViewById(R.id.address_hotspot);
            hotspotAmount=(TextView)itemView.findViewById(R.id.hotspot_amount);
        }
    }
}
