package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private Button PassReset;
    private EditText PassEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        PassEmail=(EditText)findViewById(R.id.etPasswdEmail);
        PassReset=(Button)findViewById(R.id.btnPasswdReset);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Back Arrow Button

        firebaseAuth=FirebaseAuth.getInstance();

        PassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPEmail=PassEmail.getText().toString().trim();
                if (userPEmail.equals(""))
                {
                    PassEmail.setError("Enter Email");
                    PassEmail.requestFocus();
                    Toast.makeText(PasswordActivity.this,"Enter The Registered Email Id",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(userPEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(PasswordActivity.this,"Password Reset Email Sent",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(PasswordActivity.this,"Error In Sending Password Reset Email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
