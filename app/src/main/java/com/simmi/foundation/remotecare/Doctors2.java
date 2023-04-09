package com.simmi.foundation.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Doctors2 extends AppCompatActivity {
TextView name,spl,biblio;
ImageView view;
RatingBar rate;
Button button,book;
Integer mYear,mMonth,mDay;
    private GoogleMap mMap;
    String longi,lat;
    private LocationManager locationManager;
    SupportMapFragment supportMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors2);
        name=findViewById(R.id.textView14);
        spl=findViewById(R.id.textView18);
        biblio=findViewById(R.id.datab);
        rate=findViewById(R.id.ratingBar);
        view=findViewById(R.id.imageview12);
        book=findViewById(R.id.book);
        button=findViewById(R.id.button2);
        CardView map= findViewById(R.id.map);
        Intent intent = getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
      //  client = LocationServices.getFusedLocationProviderClient(this);

        getCurrentLocation();
        String Name = intent.getStringExtra("Name");
        String Spl = intent.getStringExtra("Spl");
        String address = intent.getStringExtra("address");
        String Rating = intent.getStringExtra("Rating");
        String Bibliography = intent.getStringExtra("Bibliography");
         lat = intent.getStringExtra("lat");
         longi = intent.getStringExtra("long");
        String surl = intent.getStringExtra("surl");
            String phn="+91"+intent.getStringExtra("Phone");
        name.setText(Name);
        spl.setText(Spl);
        Glide.with(getApplicationContext()).load(surl).into(view);
        biblio.setText(Bibliography);
        rate.setRating(Float.valueOf(Rating));
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phn, null));
                startActivity(intent);
            }
        });
    }

    private void getCurrentLocation() {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap map) {
                double la= Double.parseDouble(lat); double log= Double.parseDouble(longi);
                LatLng latLng= new LatLng(la,log);
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                map.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                // Animating to the touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                map.addMarker(markerOptions);
              //  Marker marker =  map.addMarker(new MarkerOptions().position(new LatLng(19.018044, 72.84362)));


            }
        });
    }

    private void call() {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(Doctors2.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "dd/MM/yy"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
              //  yourEditText.setText(sdf.format(myCalendar.getTime()));

                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;
                if(mDay!=null){
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Doctors2.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            // time.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    //mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }

            }
        }, mYear, mMonth, mDay);
        //mDatePicker.setTitle("Select date");
        mDatePicker.show();



    }


}