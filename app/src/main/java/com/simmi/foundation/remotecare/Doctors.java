package com.simmi.foundation.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Doctors extends AppCompatActivity {
    private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private Chip heart,lung;
    DoctorAdapter adapter;
    ArrayList<HashMap<String,Object>> list = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        heart = findViewById(R.id.heart);
        lung = findViewById(R.id.lung);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list= new ArrayList<>();
//        listSubscription = new ArrayList<>();
//        donationAdapter= new DonationAdapter(this,listDonation);
        adapter = new DoctorAdapter(this,list);
        recyclerView.setAdapter(adapter);

        DatabaseReference FBDB = _firebase.getReference("Doctors").child("Heart");
        FBDB.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HashMap<String, Object> _map = dataSnapshot.getValue(_ind);
                    //check status & update by sub id
                    list.add(_map);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upcoming Button

                    Chip temp = findViewById(R.id.temp);
                    temp.setChipBackgroundColor(lung.getChipBackgroundColor());
                    temp.setTextColor(lung.getTextColors());
                    lung.setChipBackgroundColor(heart.getChipBackgroundColor());
                    lung.setTextColor(heart.getTextColors());
                    heart.setChipBackgroundColor(temp.getChipBackgroundColor());
                    heart.setTextColor(temp.getTextColors());
                 DatabaseReference FBDB = _firebase.getReference("Doctors").child("Heart");
                 FBDB.addValueEventListener(new ValueEventListener() {
                     @SuppressLint("NotifyDataSetChanged")
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                         list.clear();
                         for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                             HashMap<String, Object> _map = dataSnapshot.getValue(_ind);
                             //check status & update by sub id
                             list.add(_map);
                         }
                         adapter.notifyDataSetChanged();
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });



            }
        });

        lung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //past events button

                    Chip temp = findViewById(R.id.temp);
                    temp.setChipBackgroundColor(heart.getChipBackgroundColor());
                    temp.setTextColor(heart.getTextColors());
                heart.setChipBackgroundColor(lung.getChipBackgroundColor());
                heart.setTextColor(lung.getTextColors());
                lung.setChipBackgroundColor(temp.getChipBackgroundColor());
                lung.setTextColor(temp.getTextColors());
                DatabaseReference FBDB = _firebase.getReference("Doctors").child("lung");
                FBDB.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            HashMap<String, Object> _map = dataSnapshot.getValue(_ind);
                            //check status & update by sub id
                            list.add(_map);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

     class DoctorAdapter extends  RecyclerView.Adapter<DoctorAdapter.ViewHolder>{
        ArrayList<HashMap<String,Object>> list;
        Context context;

        public DoctorAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.heart_doctor_list,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

                holder.name.setText(list.get(position).get("Name").toString());
                holder.spl.setText(list.get(position).get("Spl").toString());

                //holder.date.setText(list.get(position).get("date").toString());
            String profileurl= list.get(position).get("surl").toString();
            Glide.with(holder.imageview.getContext()).load(profileurl).into(holder.imageview);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(holder.spl.getContext(), Doctors2.class);
                    intent.putExtra("Name",list.get(position).get("Name").toString());
                    intent.putExtra("Spl",list.get(position).get("Spl").toString());
                    intent.putExtra("address",list.get(position).get("Address").toString());
                    intent.putExtra("Rating",list.get(position).get("Rating").toString());
                    intent.putExtra("Bibliography",list.get(position).get("Bibliography").toString());
                    intent.putExtra("surl",list.get(position).get("surl").toString());
                    intent.putExtra("Phone",list.get(position).get("Phone").toString());
                    intent.putExtra("lat",list.get(position).get("Latitude").toString());
                    intent.putExtra("long",list.get(position).get("Longitude").toString());
                    context.startActivity(intent);


                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageview;
            TextView name,spl;
            CardView cardView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                cardView = itemView.findViewById(R.id.main_layout);
                imageview=itemView.findViewById(R.id.imageview12);
                name=itemView.findViewById(R.id.textView11);
                spl=itemView.findViewById(R.id.spl);

            }
        }
    }
}