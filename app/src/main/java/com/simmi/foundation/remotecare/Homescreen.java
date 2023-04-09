package com.simmi.foundation.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Homescreen extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
FloatingActionButton button;
    String FName,phn,LName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        Intent intent=getIntent();
         FName = intent.getStringExtra("name");
         phn = intent.getStringExtra("phnone");
         LName = intent.getStringExtra("lastname");
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        button=findViewById(R.id.fab);
        Home fragement= new Home();
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        Bundle bundle= new Bundle();
        bundle.putString("phn",phn);
        bundle.putString("FName",FName);
        bundle.putString("LName",LName);
        fragement.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment,new Home()).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Home()).commit();
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(Homescreen.this,Doctors.class);
      startActivity(intent);
      finish();
    }
});
    }
    private BottomNavigationView.OnNavigationItemSelectedListener listener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment= new Home();
                         break;
                        case R.id.account:
                            selectedFragment= new Account();
                            break;
                    }
                    Bundle bundle= new Bundle();
                    bundle.putString("phn",phn);
                    bundle.putString("FName",FName);
                    bundle.putString("LName",LName);
                    selectedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFragment).commit();
                    return  true;
                }
            };
}