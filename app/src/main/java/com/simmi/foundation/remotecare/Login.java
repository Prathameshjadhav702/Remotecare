package com.simmi.foundation.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
Button submit,register;
EditText phonenumber,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit=findViewById(R.id.textView5);
        register=findViewById(R.id.neww);
        phonenumber=findViewById(R.id.phn);
        pass=findViewById(R.id.password);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phnnum = phonenumber.getEditableText().toString();
                String password = pass.getEditableText().toString();
                if (phnnum.equals(null)) {
                    Toast.makeText(Login.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (password.equals(null)) {
                    Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkUser = reference.orderByChild("PHONE").equalTo(phnnum);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String passwordFromDB = snapshot.child(phnnum).child("PASSWORD").getValue(String.class);
                                String name = snapshot.child(phnnum).child("FIRSTNAME").getValue(String.class);
                                String lastname = snapshot.child(phnnum).child("LASTNAME").getValue(String.class);

                                if (passwordFromDB.equals(password)) {
                                    Intent intent = new Intent(Login.this, Homescreen.class);
                                    intent.putExtra("phnone",phnnum);
                                    intent.putExtra("name",name);
                                    intent.putExtra("lastname",lastname);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(Login.this, "Welcome User", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "PASSWORD DOESN'T MATCH", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this, "Please create your account", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, signup.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

