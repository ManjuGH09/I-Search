package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditLostPersonActivity extends AppCompatActivity {

    private EditText elpName,elpAge,elpAddress,elpPhoneNum,elpColour,elpHeight,elpDate,elpTime,elpLostPlace;
    private Button elpUpdate;
    private RadioButton rb1,rb2,rb3,rb4;
    private RadioGroup rg1,rg2,rg3,rg4;
    private String eName,eAge,eAddress,ePhoneNum,eColour,eHeight,eDate,eTime,eLostPlace,eGender,eMentallyStrong,eMissedBefore,eStatus,ampm;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int year,month,dateOfMonth,currentHour,currentMinute;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lost_person);

        elpName=findViewById(R.id.etELPName);
        elpAge=findViewById(R.id.etELPAge);
        elpAddress=findViewById(R.id.etELPAddress);
        elpPhoneNum=findViewById(R.id.etELPPhoneNum);
        elpColour=findViewById(R.id.etELPColour);
        elpHeight=findViewById(R.id.etELPHeight);
        elpDate=findViewById(R.id.etELPDate);
        elpTime=findViewById(R.id.etELPTime);
        elpLostPlace=findViewById(R.id.etELPLostAddress);
        elpUpdate=findViewById(R.id.btnELPUpdate);
        rg1=findViewById(R.id.rgEGender);
        rg2=findViewById(R.id.rgEMentallyStrong);
        rg3=findViewById(R.id.rgEMissedBefore);
        rg4=findViewById(R.id.rgEStatus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Back Arrow

        calendar=Calendar.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        DatabaseReference databaseReference1=databaseReference.child("Lost Person Details");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserLostPerson userLostPerson=dataSnapshot.getValue(UserLostPerson.class);
                elpName.setText(userLostPerson.getLpname());
                elpAge.setText(userLostPerson.getLpage());
                elpAddress.setText(userLostPerson.getLpaddress());
                elpPhoneNum.setText(userLostPerson.getLpphonenum());
                elpColour.setText(userLostPerson.getLpcolour());
                elpHeight.setText(userLostPerson.getLpheight());
                elpDate.setText(userLostPerson.getLpdate());
                elpTime.setText(userLostPerson.getLptime());
                elpLostPlace.setText(userLostPerson.getLplostplace());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditLostPersonActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        elpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dateOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(EditLostPersonActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                elpDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },year,month,dateOfMonth);
                datePickerDialog.show();
            }
        });

        elpUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
                if (Validate()==true)
                {
                    UserLostPerson userLostPerson = new UserLostPerson(eName,eAge,eAddress,ePhoneNum,eColour,eHeight,eDate,eTime,eLostPlace,eGender,eMentallyStrong,eMissedBefore,eStatus);
                    databaseReference.child("Lost Person Details").setValue(userLostPerson);
                    Toast.makeText(EditLostPersonActivity.this,"Update Completed",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EditLostPersonActivity.this,"Update Not Completed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        elpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute=calendar.get(Calendar.MINUTE);
                timePickerDialog=new TimePickerDialog(EditLostPersonActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay>=12)
                                    ampm="PM";
                                else
                                    ampm="AM";
                                elpTime.setText(String.format("%02d:%02d",hourOfDay,minute)+ampm);
                            }
                        },currentHour,currentMinute,false);
                timePickerDialog.show();
            }
        });
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

    public boolean Validate()
    {
        boolean result=false;

        eName=elpName.getText().toString();
        eAge=elpAge.getText().toString();
        eAddress=elpAddress.getText().toString();
        ePhoneNum=elpPhoneNum.getText().toString();
        eColour=elpColour.getText().toString();
        eHeight=elpHeight.getText().toString();
        eDate=elpDate.getText().toString();
        eTime=elpTime.getText().toString();
        eLostPlace=elpLostPlace.getText().toString();
        eGender=rb1.getText().toString();
        eMentallyStrong=rb2.getText().toString();
        eMissedBefore=rb3.getText().toString();
        eStatus=rb4.getText().toString();
        if (eName.equals("")&&eAge.equals("")&&eAddress.equals("")&&ePhoneNum.equals("")&&eColour.equals("")&&eHeight.equals("")&&eDate.equals("")&&eTime.equals("")
                &&eLostPlace.equals(""))
            Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show();
        else if (eName.equals(""))
        {
            elpName.setError("Enter Name");
            elpName.requestFocus();
        }
        else if (eAge.equals(""))
        {
            elpAge.setError("Enter Age");
            elpAge.requestFocus();
        }
        else if (eAge.length()>3)
        {
            elpAge.setError("Enter Correct Age");
            elpAge.requestFocus();
        }
        else if (eAddress.equals(""))
        {
            elpAddress.setError("Enter Address");
            elpAddress.requestFocus();
        }
        else if (ePhoneNum.equals(""))
        {
            elpPhoneNum.setError("Enter Phone Number");
            elpPhoneNum.requestFocus();
        }
        else if (ePhoneNum.length()!=10)
        {
            elpPhoneNum.setError("Enter Correct Phone Number");
            elpPhoneNum.requestFocus();
        }
        else if (eColour.equals(""))
        {
            elpColour.setError("Enter Colour");
            elpColour.requestFocus();
        }
        else if (eHeight.equals(""))
        {
            elpHeight.setError("Enter Height");
            elpHeight.requestFocus();
        }
        else if (eDate.equals(""))
        {
            elpDate.setError("Enter Date");
            elpDate.requestFocus();
        }
        else if (eHeight.length()>3)
        {
            elpHeight.setError("Enter Correct Height");
            elpHeight.requestFocus();
        }
        else if (eTime.equals(""))
        {
            elpTime.setError("Enter Time");
            elpTime.requestFocus();
        }
        else if (eLostPlace.equals(""))
        {
            elpLostPlace.setError("Enter Lost Place");
            elpLostPlace.requestFocus();
        }
        else
        {
            result=true;
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
