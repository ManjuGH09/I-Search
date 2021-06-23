package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName,profileAge,profileEmail;
    private Button profileUpdate,profileChangePass;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileAge=findViewById(R.id.tvProAge);
        profileEmail=findViewById(R.id.tvProEmail);
        profileName=findViewById(R.id.tvProName);
        profileUpdate=findViewById(R.id.btnProUpdate);
        profileChangePass=findViewById(R.id.btnProChangePassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Back Arrow Button

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid()).child("Profile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                profileName.setText("Name : "+userProfile.getUserPName());
                profileAge.setText("Age : "+userProfile.getUserPAge());
                profileEmail.setText("Email : "+userProfile.getUserPEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdateProfile.class));
            }
        });

        profileChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,PasswordActivity.class));
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
