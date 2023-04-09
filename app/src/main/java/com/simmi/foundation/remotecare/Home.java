package com.simmi.foundation.remotecare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView heart,lung;
        TextView name;
        String Fname,phn;
        ImageView imageView;
        DoctorAdapter adapter;
       RecyclerView recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<HashMap<String,Object>>  list= new ArrayList<>();
//        listSubscription = new ArrayList<>();
//        donationAdapter= new DonationAdapter(this,listDonation);
        adapter = new DoctorAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        name=view.findViewById(R.id.name);
        heart=view.findViewById(R.id.event_description);
        lung=view.findViewById(R.id.event_description1);
        imageView=view.findViewById(R.id.profilepicture);
        Bundle data= getArguments();
        if(data!=null){
            Fname= data.getString("FName");
            phn= data.getString("phn");
            name.setText(Fname);
        }
/*        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Users").child(data.getString("phn"));
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean value= snapshot.hasChild("ProfilePicture");
                if(value.equals(true)){
                    if(value.equals(true)) {
                        String profileurl=snapshot.child("ProfilePicture").getValue(String.class);
                        //  Toast.makeText(getContext(), profileurl, Toast.LENGTH_SHORT).show();
                        if (!profileurl.equals("")) {
                            // profile.setImageResource(R.drawable.profile);
                            Glide.with(getContext()).load(profileurl).into(imageView);
                            // profile.setImageURI(Uri.parse(profileurl));
                        }else{
                            imageView.setImageResource(R.drawable.profile);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        DatabaseReference FBDB = FirebaseDatabase.getInstance().getReference("Doctors").child("lung");
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
                Intent intent = new Intent(getActivity(), Heartdeasis.class);
                startActivity(intent);
            }
        });
        lung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), lungs_ui_ux.class));
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
            return new DoctorAdapter.ViewHolder(v);
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