package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText UserLogin,UserPassword;
    private TextView AttemptLeft,Register,ForgotPassword;
    private Button Login,PassVisible,PassInVisible;
    private int count=5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserLogin=(EditText)findViewById(R.id.etLoginId);
        UserPassword=(EditText)findViewById(R.id.etPassword);
        AttemptLeft=(TextView)findViewById(R.id.tvAttempt);
        Login=(Button)findViewById(R.id.btnLogin);
        Register=(TextView)findViewById(R.id.tvRegister);
        ForgotPassword=(TextView)findViewById(R.id.tvForgotPassword);
        PassVisible=findViewById(R.id.btnVisible);
        PassInVisible=findViewById(R.id.btnInVisible);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        AttemptLeft.setText("Number Of Attempts Remaining: 5 ");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();

        if (user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }

        PassVisible.setVisibility(View.VISIBLE);
        PassInVisible.setVisibility(View.INVISIBLE);

        PassVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                PassVisible.setVisibility(View.INVISIBLE);
                PassInVisible.setVisibility(View.VISIBLE);
            }
        });

        PassInVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                PassVisible.setVisibility(View.VISIBLE);
                PassInVisible.setVisibility(View.INVISIBLE);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserLogin.getText().toString().trim().equalsIgnoreCase(""))
                {
                    UserLogin.setError("Enter Email Id");
                    UserLogin.requestFocus();
                }
                else if(UserPassword.getText().toString().trim().equalsIgnoreCase(""))
                {
                    UserPassword.setError("Enter Password");
                    UserPassword.requestFocus();
                }
                else {
                    Validate(UserLogin.getText().toString(), UserPassword.getText().toString());
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PasswordActivity.class));
            }
        });
    }

    private void Validate(String Name,String Password)
    {
        progressDialog.setMessage("Fetching Data...!!!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Name,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    checkEmailVerification();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    count--;
                    AttemptLeft.setText("Number Of Attempts Remaining:" +count);
                    progressDialog.dismiss();
                    if (count==0)
                    {
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }

    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if (emailflag)
        {
            finish();
            startActivity(new Intent(MainActivity.this,InformationActivity.class));
            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Verify Your Email...",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
