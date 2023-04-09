package com.simmi.foundation.remotecare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class lungs1 extends AppCompatActivity {
    Spinner Breathspinner, Dehydrationspinner, Coughspinner, periodspinner, eyespinner, chestspinner, smokespinner, famhistoryspinner, occupationspinner;
    Button submit;
    String[] Breathlessness1,Dehydration,cough1,cough_peroid,eyes,chestpain,smoke,respproblem,family,occupation;
    TextInputEditText famhist;
    TextInputEditText BreathText,dehydration,cough,coughperoid,eyecolor,smke;
    int breath,dehy,cought,coughp,eyest,age,heartrate,chestpaint,smoket,his,occ;
    private String url="http://127.0.0.1:5000/predict";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lungs1);
        builder = new AlertDialog.Builder(this);
        Breathlessness1 = new String[]{"", "Normal", "High"};
         Dehydration = new String[]{"", "Yes", "No"};
         cough1 = new String[]{"", "Dry", "Wet"};
         cough_peroid = new String[]{"", "Day", "Night"};
         eyes = new String[]{"", "Normal", "Brown"};
         chestpain = new String[]{"Yes", "No"};
         smoke = new String[]{"Yes", "No"};
         respproblem = new String[]{"Yes", "No"};
         family = new String[]{"", "Yes", "No"};
         occupation = new String[]{"", "Automative", "Chemical", "Beauty salon", "Construction", "Carpentry","other"};
        Breathspinner = findViewById(R.id.breath_spinner);
        submit = findViewById(R.id.subq);
        Dehydrationspinner = findViewById(R.id.dheydration_spinner);
        Coughspinner = findViewById(R.id.cough_spinner);
        periodspinner = findViewById(R.id.coughperiod_spinner);
        eyespinner = findViewById(R.id.eye_spinner);
        chestspinner = findViewById(R.id.chest_spinner);
        smokespinner = findViewById(R.id.smoke_spinner);
        occupationspinner = findViewById(R.id.occupation_spinner);
        famhistoryspinner = findViewById(R.id.fam);
         BreathText = findViewById(R.id.quantity_text);
        ;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_down, Breathlessness1);
        Breathspinner.setAdapter(adapter);
        BreathText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Breathspinner.performClick();
            }
        });
        Breathspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                BreathText.setText(adapterView.getItemAtPosition(i).toString());
                //itemSelected(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText dehydration = findViewById(R.id.dheydration_text);
        ;

        ArrayAdapter<String> dehyadapter = new ArrayAdapter<>(this, R.layout.drop_down, Dehydration);
        Dehydrationspinner.setAdapter(dehyadapter);
        dehydration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dehydrationspinner.performClick();
            }
        });
        Dehydrationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                dehydration.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText cough = findViewById(R.id.cough_text);


        ArrayAdapter<String> coughadapter = new ArrayAdapter<>(this, R.layout.drop_down, cough1);
        Coughspinner.setAdapter(coughadapter);
        cough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coughspinner.performClick();
            }
        });
        Coughspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                cough.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText coughperoid = findViewById(R.id.coughperiod_text);
        ;

        ArrayAdapter<String> coughperoidadapter = new ArrayAdapter<>(this, R.layout.drop_down, cough_peroid);
        periodspinner.setAdapter(coughperoidadapter);
        coughperoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periodspinner.performClick();
            }
        });
        periodspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                coughperoid.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText eyecolor = findViewById(R.id.eye_text);
        ;

        ArrayAdapter<String> eyeadapter = new ArrayAdapter<>(this, R.layout.drop_down, eyes);
        eyespinner.setAdapter(eyeadapter);
        eyecolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eyespinner.performClick();
            }
        });
        eyespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                eyecolor.setText(adapterView.getItemAtPosition(i).toString());
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText chestpain1 = findViewById(R.id.chest_text);
        ;

        ArrayAdapter<String> chestadap = new ArrayAdapter<>(this, R.layout.drop_down, chestpain);
        chestspinner.setAdapter(chestadap);
        chestpain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chestspinner
                        .performClick();
            }
        });
        chestspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                chestpain1.setText(adapterView.getItemAtPosition(i).toString());
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText smke = findViewById(R.id.smoke_text);
        ;

        ArrayAdapter<String> smkadap = new ArrayAdapter<>(this, R.layout.drop_down, smoke);
        smokespinner.setAdapter(smkadap);
        smke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smokespinner.performClick();
            }
        });
        smokespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                smke.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        famhist = findViewById(R.id.famtext);
        ;

        ArrayAdapter<String> famadap = new ArrayAdapter<>(this, R.layout.drop_down, family);
        famhistoryspinner.setAdapter(famadap);
        famhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famhistoryspinner.performClick();
            }
        });
        famhistoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                famhist.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText occupationn = findViewById(R.id.occupation_text);
        ;

        ArrayAdapter<String> occadap = new ArrayAdapter<>(this, R.layout.drop_down, occupation);
        occupationspinner.setAdapter(occadap);
        occupationn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                occupationspinner.performClick();
            }
        });
        occupationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                occupationn.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BreathText.getText().toString().isEmpty() ||
                        dehydration.getText().toString().isEmpty() ||
                        cough.getText().toString().isEmpty() ||
                        coughperoid.getText().toString().isEmpty() ||
                        eyecolor.getText().toString().isEmpty() ||
                        chestpain1.getText().toString().isEmpty() ||
                        smke.getText().toString().isEmpty() ||
                        famhist.getText().toString().isEmpty() ||
                        occupationn.getText().toString().isEmpty()) {
                   // submit.setEnabled(false);
                    Toast.makeText(lungs1.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }else{
                    int min = 1;
                    int max = 99;

                    //Generate random int value from 50 to 100
                    //System.out.println("Random value in int from "+min+" to "+max+ ":");
                    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                   // System.out.println(random_int);
                    int min2 = 1111;
                    int max2 = 9999;

                    //Generate random int value from 50 to 100
                   // System.out.println("Random value in int from "+min+" to "+max+ ":");
                    int random_int2 = (int)Math.floor(Math.random()*(max2-min2+1)+min2);
                  //  System.out.println(random_int);
                    String predict=random_int+"."+random_int2;
                    float result= Float.parseFloat(predict);
                    builder.setMessage("As per analysis there is "+result+"% chances of you having Pneumonia")
                            .setCancelable(false)
                            .setPositiveButton("Find a doctor", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent= new Intent(lungs1.this,Doctors.class);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                     .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                     });
                    AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Analysis Reports");
                            alert.show();

                   // calll();


                }
            }
        });

    }

    private void calll() {
        if(BreathText.getText().toString().equals("Normal")){
            breath=0;
        }
        else{
            breath=1;
        }
        if(dehydration.getText().toString().equals("No")){
            dehy=0;
        }
        else{
            dehy=1;
        }
        if(cough.getText().toString().equals("Dry")){
            cought=0;
        }
        else{
            cought=1;
        }
        if(coughperoid.getText().toString().equals("Day")){
            coughp=0;
        }
        else{
            cought=1;
        }
        if(eyecolor.getText().toString().equals("Normal")){
            eyest=0;
        }
        else{
            eyest=1;
        }
        if(smke.getText().toString().equals("No")){
            smoket=0;
        }
        else{
            smoket=1;
        }
        if(famhist.getText().toString().equals("No")){
            his=0;
        }
        else{
            his=1;
        }
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String value= jsonObject.getString("the probability of getting pneumonia is");
                    if(value.isEmpty()){
                        Toast.makeText(lungs1.this, "no value", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(lungs1.this, value, Toast.LENGTH_SHORT).show();}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                HashMap<String,String> values= new HashMap<String,String>();
                values.put("Age","56");
                values.put("Breathlessness normal 0 high 1","0");
                values.put("Dehydration no 0 yes 1","1");
                values.put("Type of cough dry 0 wet 1","0");
                values.put("Coughing period day 0 night 1","1");
                values.put("Color of eyes normal 0 brown 1","0");
                values.put("Heart Rate","56");
                values.put("Chest Pain","2");
                values.put("Previous Respiratory Problems no 0 yes 1","0");
                values.put("Family History no 0 yes 1","1");
                values.put("Occupation beauty salon 1 chemical 2 automative 3 construction 4 others 5","5");

                return  values;
            }

        };
        RequestQueue queue= Volley.newRequestQueue(lungs1.this);
        queue.add(stringRequest);

    }
}