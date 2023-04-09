package com.simmi.foundation.remotecare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String LName,Fname,phn;
    ImageView imageView;
    private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private DatabaseReference FBDB = _firebase.getReference("Users");
   // private DatabaseReference FBDB = _firebase.getReference("User");

    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
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
        return inflater.inflate(R.layout.fragment_account2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView=view.findViewById(R.id.name);
             imageView=view.findViewById(R.id.profile_picture);
             TextView pass = view.findViewById(R.id.pass);
             TextView chats = view.findViewById(R.id.chat);
             TextView aboutapp = view.findViewById(R.id.about);
             TextView loc = view.findViewById(R.id.loc);
              pass.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                  }
              });


        Bundle data= getArguments();
        if(data!=null){
            phn= data.getString("phn");
            LName= data.getString("FName");
            Fname= data.getString("LName");
            textView.setText(phn);
        }
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change Password");

                // Inflate the layout for the dialog
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_layout, null);

                // Get a reference to the EditText view
                final EditText editText = dialogView.findViewById(R.id.edit_text);

                // Set the dialog view
                builder.setView(dialogView);

                // Add action buttons
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle submit button click
                        String text = editText.getText().toString();
                        // Do something with the entered text
                        DatabaseReference data=FirebaseDatabase.getInstance().getReference("Users").child(phn).child("PASSWORD");
                        data.setValue(text);
                        Toast.makeText(getActivity(), "Password  Changed sucessfully", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(getActivity(),Login.class);
                        startActivity(i);

                    }
                });
                builder.setNegativeButton("Cancel", null);

                // Show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagepicker();
            }
        });
    }

    private void imagepicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(intent, 102);
    }
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 102) {
                final FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference FS = storage.getReference();
                imageView.setImageURI(data.getData());
                String strogekey = FBDB.push().getKey();
                FS.child(strogekey).putFile(data.getData()).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        //submit.setEnabled(false);
                        Toast.makeText(getContext(), "Please wait, Image is being uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return FS.child(strogekey).getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String   uri = task.getResult().toString();
                       // FirebaseAuth mAuth = FirebaseAuth.getInstance();
                     //   String UID = mAuth.getCurrentUser().getUid();
                       // Toast.makeText(getContext(), UID, Toast.LENGTH_SHORT).show();
                        DatabaseReference surl = FirebaseDatabase.getInstance().getReference("Users").child(phn).child("ProfilePicture");
                        surl.setValue(uri);
                    }
                });
            }
        }
    }
}