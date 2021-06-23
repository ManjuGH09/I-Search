package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class InformationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        firebaseAuth=FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.information);
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
                    case R.id.information:
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
        switch (item.getItemId())
        {
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(InformationActivity.this,MainActivity.class));
            }
            case R.id.profileMenu:{
                startActivity(new Intent(InformationActivity.this,ProfileActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
