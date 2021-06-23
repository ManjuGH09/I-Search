package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView homePage1/*,homePage2,homePage3,homePage4,homePage5,homePage6,homePage7*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePage1=findViewById(R.id.tvHomePage1);
        /*homePage2=findViewById(R.id.tvHomePage2);
        homePage3=findViewById(R.id.tvHomePage3);
        homePage4=findViewById(R.id.tvHomePage4);
        homePage5=findViewById(R.id.tvHomePage5);
        homePage6=findViewById(R.id.tvHomePage6);
        homePage7=findViewById(R.id.tvHomePage7);*/

        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid()).child("Lost Person Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserLostPerson userLostPerson=dataSnapshot.getValue(UserLostPerson.class);
                homePage1.setText("     Name :      "+userLostPerson.getLpname()+
                                "\n     Age :          "+userLostPerson.getLpage()+
                                "\n     Gender :   "+userLostPerson.getLpgender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this,"Failed To Get Data",Toast.LENGTH_SHORT).show();
            }
        });

        homePage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LostPersonDetailsActivity.class));
            }
        });

        /*DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid()).child("Lost Person Details1");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserLostPerson userLostPerson=dataSnapshot.getValue(UserLostPerson.class);
                homePage2.setText("Name : "+userLostPerson.getLpname()+"\nAge : "+userLostPerson.getLpage()+"\tGender : "+userLostPerson.getLpgender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this,"Failed To Get Data",Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid()).child("Lost Person Details2");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserLostPerson userLostPerson=dataSnapshot.getValue(UserLostPerson.class);
                homePage3.setText("Name : "+userLostPerson.getLpname()+"\nAge : "+userLostPerson.getLpage()+"\tGender : "+userLostPerson.getLpgender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this,"Failed To Get Data",Toast.LENGTH_SHORT).show();
            }
        });*/
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.information:
                    {
                        startActivity(new Intent(getApplicationContext(),InformationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.home:
                    {
                        return true;
                    }
                    case R.id.register:
                    {
                        startActivity(new Intent(getApplicationContext(),Registration2Activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                }
                return false;
            }
        });
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
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
            case R.id.profileMenu:{
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
            /*case R.id.aboutMenu:{
                startActivity(new Intent(HomeActivity.this,InformationActivity.class));
            }*/
        }
        return super.onOptionsItemSelected(item);
    }
}
