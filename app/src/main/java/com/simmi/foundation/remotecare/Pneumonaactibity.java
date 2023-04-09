package com.simmi.foundation.remotecare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;


//import app.ij.mlwithtensorflowlite.ml.Model;

public class Pneumonaactibity extends AppCompatActivity {
    ImageView imageView;
    private Bitmap img;
    private Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneumonaactibity);
         imageView=findViewById(R.id.imageView6);
        ImageButton imageButton=findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
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

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 102) {
                assert data != null;
                imageView.setImageURI(data.getData());
                Uri uri = data.getData();
                try {
                    img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            /*    try {

                } catch (IOException e) {
                    // TODO Handle the exception
                }*/

                CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                        .requireWifi()
                        .build();
                FirebaseModelDownloader.getInstance()
                        .getModel("MODEL", DownloadType.LOCAL_MODEL, conditions)
                        .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                            @Override
                            public void onSuccess(CustomModel model) {
                                // Download complete. Depending on your app, you could enable
                                // the ML feature, or switch from the local model to the remote
                                // model, etc.
                                File modelFile = model.getFile();
                                if (modelFile != null) {
                                    Interpreter.Options options = new Interpreter.Options();
                                    interpreter = new Interpreter(modelFile,options);
                                }
                                else{
                                    Toast.makeText(Pneumonaactibity.this, "MODEL FILE EMPTY", Toast.LENGTH_SHORT).show();
                                }
                        }
            });
                // Preprocess the input image using TensorImage

                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1,224, 224, 3}, DataType.FLOAT32);
                TensorImage tensorImage = new TensorImage( DataType.FLOAT32);
                Bitmap imgl = Bitmap.createScaledBitmap(img, 224, 224,true);
                tensorImage.load(imgl);



                float[][] outputFeature0 = new float[1][1];
                interpreter.run(inputFeature0.getBuffer(), outputFeature0);

                float predictedValue = outputFeature0[0][0];
                Toast.makeText(this, String.valueOf(predictedValue), Toast.LENGTH_SHORT).show();


            }
    }
}


}
