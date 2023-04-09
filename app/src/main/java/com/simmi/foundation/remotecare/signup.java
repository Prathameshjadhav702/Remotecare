package com.simmi.foundation.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class signup extends AppCompatActivity {
    Button submit;
    EditText phonenumber, pass, confpass, firstname, lastname, adress;
    ImageButton back;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        submit = findViewById(R.id.sub);
        //back=findViewById(R.id.backk);
        phonenumber = findViewById(R.id.phonenumber);
        pass = findViewById(R.id.pass1);
        confpass = findViewById(R.id.confpass);
        firstname = findViewById(R.id.Firstname);
        lastname = findViewById(R.id.lastname);
        adress = findViewById(R.id.address);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phnnum = phonenumber.getEditableText().toString();
                String fname = firstname.getEditableText().toString();
                String lname = lastname.getEditableText().toString();
                String adresss = adress.getEditableText().toString();
                String pass1 = pass.getEditableText().toString();
                String pass2 = confpass.getEditableText().toString();
                if (phnnum.equals(null)) {
                    Toast.makeText(signup.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (fname.equals(null)) {
                    Toast.makeText(signup.this, "Please enter Firstname", Toast.LENGTH_SHORT).show();
                } else if (lname.equals(null)) {
                    Toast.makeText(signup.this, "Please enter Lastname", Toast.LENGTH_SHORT).show();
                } else if (pass1.equals(null)) {
                    Toast.makeText(signup.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if (pass2.equals(null)) {
                    Toast.makeText(signup.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                } else if (!pass2.equals(pass1)) {
                    Toast.makeText(signup.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                } else if (adress.equals(null)) {
                    Toast.makeText(signup.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                } else {
                    Query ref = database.getReference("Users").orderByChild("PHONE").equalTo(phnnum);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(signup.this, "Account already created", Toast.LENGTH_SHORT).show();
                            } else {
                                sendotp();
                               /* FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                                DatabaseReference PHONEE = rootNode.getReference("Users").child(phnnum).child("PHONE");
                                DatabaseReference FNAMEE = rootNode.getReference("Users").child(phnnum).child("FIRSTNAME");
                                DatabaseReference LNAME = rootNode.getReference("Users").child(phnnum).child("LASTNAME");
                                DatabaseReference ADRESS = rootNode.getReference("Users").child(phnnum).child("ADDRESS");
                                DatabaseReference PASS = rootNode.getReference("Users").child(phnnum).child("PASSWORD");
                                //  DatabaseReference PASSWORD =rootNode.getReference("Users").child(PHNUM).child("PASSWORD");
                                FNAMEE.setValue(fname);
                                PHONEE.setValue(phnnum);
                                PASS.setValue(pass2);
                                ADRESS.setValue(adresss);
                                LNAME.setValue(lname);*/

                                // Toast.makeText(register.this, "DATA ENTERED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

    }

    private void sendotp() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(signup.this, "Verification error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, token);
                Toast.makeText(signup.this, s, Toast.LENGTH_SHORT).show();
                Intent intet= new Intent(signup.this, otp.class);
               // intet.putExtra("phn", );
                intet.putExtra("verfication id",s);
                startActivity(intet);

            }


        };
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+918779638195")       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}