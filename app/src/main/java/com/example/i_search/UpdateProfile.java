package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    private EditText newUserName,newUserAge,newUserEmail;
    private Button Save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        newUserAge=findViewById(R.id.etUPAge);
        newUserEmail=findViewById(R.id.etUPEmail);
        newUserName=findViewById(R.id.etUPName);
        Save=findViewById(R.id.btnUPUpdate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Back Arrow Button

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        DatabaseReference databaseReference1=databaseReference.child("Profile");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                newUserName.setText(userProfile.getUserPName());
                newUserAge.setText(userProfile.getUserPAge());
                newUserEmail.setText(userProfile.getUserPEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=newUserName.getText().toString();
                String age=newUserAge.getText().toString();
                String email=newUserEmail.getText().toString();

                if (name.equals("") && age.equals("") && email.equals(""))
                {
                    Toast.makeText(UpdateProfile.this,"Enter All Data",Toast.LENGTH_SHORT).show();
                }
                else if (name.equalsIgnoreCase(""))
                {
                    newUserName.setError("Enter Name");
                    newUserName.requestFocus();
                }
                else if (age.equalsIgnoreCase(""))
                {
                    newUserAge.setError("Enter Age");
                    newUserAge.requestFocus();
                }
                else if(age.length()>3)
                {
                    newUserAge.setError("Enter Correct Age");
                    newUserAge.requestFocus();
                }
                else if (email.equalsIgnoreCase(""))
                {
                    newUserEmail.setError("Enter Email");
                    newUserEmail.requestFocus();
                }
                else {
                    UserProfile userProfile = new UserProfile(age, email, name);
                    databaseReference.child("Profile").setValue(userProfile);
                    finish();
                }
            }
        });
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
