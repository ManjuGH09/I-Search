package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;
import java.util.Random;

public class Registration2Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText lpName,lpAge,lpAddress,lpPhoneNum,lpColour,lpHeight,lpDate,lpTime,lpLostPlace;
    private Button lpSubmit,lpEdit;
    private RadioButton rb1,rb2,rb3,rb4;
    private RadioGroup rg1,rg2,rg3,rg4;
    private String Name,Age,Address,PhoneNum,Colour,Height,Date,Time,LostPlace,Gender,MentallyStrong,MissedBefore,Status,ampm;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int year,month,dateOfMonth,currentHour,currentMinute;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        lpName=findViewById(R.id.etLPName);
        lpAge=findViewById(R.id.etLPAge);
        lpAddress=findViewById(R.id.etLPAddress);
        lpPhoneNum=findViewById(R.id.etLPPhoneNum);
        lpColour=findViewById(R.id.etLPColour);
        lpHeight=findViewById(R.id.etLPHeight);
        lpDate=findViewById(R.id.etLPDate);
        lpTime=findViewById(R.id.etLPTime);
        lpLostPlace=findViewById(R.id.etLPLostAddress);
        lpSubmit=findViewById(R.id.btnLPSubmit);
        /*lpEdit=findViewById(R.id.btnLPEdit);*/
        rg1=findViewById(R.id.rgGender);
        rg2=findViewById(R.id.rgMentallyStrong);
        rg3=findViewById(R.id.rgMissedBefore);
        rg4=findViewById(R.id.rgStatus);

        calendar=Calendar.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        /*lpEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration2Activity.this,EditLostPersonActivity.class));
            }
        });*/

        lpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if (validate()==true)
                {
                    UserLostPerson();
                    notification();
                    Toast.makeText(Registration2Activity.this,"Registration Completed",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Registration2Activity.this,"Registration Not Completed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        lpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dateOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Registration2Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                lpDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },year,month,dateOfMonth);
                datePickerDialog.show();
            }
        });

        lpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute=calendar.get(Calendar.MINUTE);
                timePickerDialog=new TimePickerDialog(Registration2Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay>=12)
                                    ampm="PM";
                                else
                                    ampm="AM";
                                lpTime.setText(String.format("%02d:%02d",hourOfDay,minute)+ampm);
                            }
                        },currentHour,currentMinute,false);
                timePickerDialog.show();
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.register);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                    {
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.register:
                    {
                        return true;
                    }
                    case R.id.information:
                    {
                        startActivity(new Intent(getApplicationContext(),InformationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                }
                return false;
            }
        });
    }
    private void UserLostPerson()
    {
        String LPName,LPAge,LPAddress,LPPhoneNum,LPColour,LPHeight,LPDate,LPTime,LPLostPlace,LPGender,LPMissedBefore,LPMentallyStrong,LPStatus;
        LPName=Name;
        LPAge=Age;
        LPAddress=Address;
        LPPhoneNum=PhoneNum;
        LPColour=Colour;
        LPHeight=Height;
        LPDate=Date;
        LPTime=Time;
        LPLostPlace=LostPlace;
        LPGender=Gender;
        LPMentallyStrong=MentallyStrong;
        LPMissedBefore=MissedBefore;
        LPStatus=Status;
        UserLostPerson userLostPerson = new UserLostPerson(LPName,LPAge,LPAddress,LPPhoneNum,LPColour,LPHeight,LPDate,LPTime,LPLostPlace,LPGender,LPMentallyStrong,LPMissedBefore,LPStatus);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("Lost Person Details").setValue(userLostPerson);
    }

    public void notification()
    {
        NotificationCompat.Builder notificationBuilder=(NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                .setContentTitle("Notification From I-Search")
                .setContentText("You Have Successfully Registered");

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notificationBuilder.build());
    }

    public void rb1click(View v)
    {
        int Gender=rg1.getCheckedRadioButtonId();
        rb1=findViewById(Gender);

    }
    public void rb2click(View v)
    {
        int MentallyStrong=rg2.getCheckedRadioButtonId();
        rb2=findViewById(MentallyStrong);

    }
    public void rb3click(View v)
    {
        int MissedBeforeThis=rg3.getCheckedRadioButtonId();
        rb3=findViewById(MissedBeforeThis);

    }
    public void rb4click(View v)
    {
        int Status=rg4.getCheckedRadioButtonId();
        rb4=findViewById(Status);

    }
    private Boolean validate()
    {
        Boolean result=false;
        Name=lpName.getText().toString();
        Age=lpAge.getText().toString();
        Address=lpAddress.getText().toString();
        PhoneNum=lpPhoneNum.getText().toString();
        Colour=lpColour.getText().toString();
        Height=lpHeight.getText().toString();
        Date=lpDate.getText().toString();
        Time=lpTime.getText().toString();
        LostPlace=lpLostPlace.getText().toString();
        Gender=rb1.getText().toString();
        MentallyStrong=rb2.getText().toString();
        MissedBefore=rb3.getText().toString();
        Status=rb4.getText().toString();
        if (Name.equals("")&&Age.equals("")&&Address.equals("")&&PhoneNum.equals("")&&Colour.equals("")&&Height.equals("")&&Date.equals("")&&Time.equals("")
                &&LostPlace.equals(""))
                Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show();
        else if (Name.equals(""))
        {
            lpName.setError("Enter Name");
            lpName.requestFocus();
        }
        else if (Age.equals(""))
        {
            lpAge.setError("Enter Age");
            lpAge.requestFocus();
        }
        else if (Age.length()>3)
        {
            lpAge.setError("Enter Correct Age");
            lpAge.requestFocus();
        }
        else if (Address.equals(""))
        {
            lpAddress.setError("Enter Address");
            lpAddress.requestFocus();
        }
        else if (PhoneNum.equals(""))
        {
            lpPhoneNum.setError("Enter Phone Number");
            lpPhoneNum.requestFocus();
        }
        else if (PhoneNum.length()!=10)
        {
            lpPhoneNum.setError("Enter Correct Phone Number");
            lpPhoneNum.requestFocus();
        }
        else if (Colour.equals(""))
        {
            lpColour.setError("Enter Colour");
            lpColour.requestFocus();
        }
        else if (Height.equals(""))
        {
            lpHeight.setError("Enter Height");
            lpHeight.requestFocus();
        }
        else if (Height.length()>3)
        {
            lpHeight.setError("Enter Correct Height");
            lpHeight.requestFocus();
        }
        else if (Date.equals(""))
        {
            lpDate.setError("Enter Date");
            lpDate.requestFocus();
        }
        else if (Time.equals(""))
        {
            lpTime.setError("Enter Time");
            lpTime.requestFocus();
        }
        else if (LostPlace.equals(""))
        {
            lpLostPlace.setError("Enter Lost Place");
            lpLostPlace.requestFocus();
        }
        else
        {
            result=true;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(Registration2Activity.this,MainActivity.class));
            }
            case R.id.profileMenu:{
                startActivity(new Intent(Registration2Activity.this,ProfileActivity.class));
            }
            /*case R.id.aboutMenu:{
                startActivity(new Intent(Registration2Activity.this,InformationActivity.class));
            }*/
        }
        return super.onOptionsItemSelected(item);
    }
}
