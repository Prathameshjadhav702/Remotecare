package com.simmi.foundation.remotecare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class Heartdeasis extends AppCompatActivity {
    private HashMap<String, Object> data = new HashMap<>();
    private ArrayAdapter<String> arrayAdapter;
    String[] sexdata = {"Male","Female"};
    Spinner data1;
    private Spinner spinner;
    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartdeasis);


    }
}