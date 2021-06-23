package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, userAge,userRePassword;
    private Button userRegister,Visible1,Visible2,InVisible1,InVisible2;
    private TextView userLogin;
   //private ImageView userProfilePic;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    String name,email,password,age,repassword;
    //private static int PICK_IMAGE = 123;
    //Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userName = (EditText) findViewById(R.id.etRegName);
        userEmail = (EditText) findViewById(R.id.etRegEmail);
        userPassword = (EditText) findViewById(R.id.etRegPassword);
        userRegister = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvRegLogin);
        userAge = (EditText) findViewById(R.id.etRegAge);
        userRePassword=findViewById(R.id.etRegRePassword);
        Visible1=findViewById(R.id.btnRegPVisible);
        Visible2=findViewById(R.id.btnRegRPVisible);
        InVisible1=findViewById(R.id.btnRegPInVisible);
        InVisible2=findViewById(R.id.btnRegRPInVisible);
        //userProfilePic = (ImageView) findViewById(R.id.ivRegProfilePic);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        progressDialog=new ProgressDialog(this);

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    progressDialog.setMessage("Storing Data...!!!");
                    progressDialog.show();
                    String user_email = userEmail.getText().toString();
                    String user_password = userPassword.getText().toString();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();
                                progressDialog.dismiss();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });

        Visible1.setVisibility(View.VISIBLE);
        InVisible1.setVisibility(View.INVISIBLE);

        Visible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Visible1.setVisibility(View.INVISIBLE);
                InVisible1.setVisibility(View.VISIBLE);
            }
        });

        InVisible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                Visible1.setVisibility(View.VISIBLE);
                InVisible1.setVisibility(View.INVISIBLE);
            }
        });

        Visible2.setVisibility(View.VISIBLE);
        InVisible2.setVisibility(View.INVISIBLE);

        Visible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Visible2.setVisibility(View.INVISIBLE);
                InVisible2.setVisibility(View.VISIBLE);
            }
        });

        InVisible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                Visible2.setVisibility(View.VISIBLE);
                InVisible2.setVisibility(View.INVISIBLE);
            }
        });

        /*userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==PICK_IMAGE && requestCode==RESULT_OK && data.getData()!=null)
        {
            imagePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    private boolean validate() {
        Boolean Result = false;
        age = userAge.getText().toString();
        name = userName.getText().toString();
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        repassword=userRePassword.getText().toString();

        if (name.isEmpty() && email.isEmpty() && password.isEmpty() && age.isEmpty() && repassword.isEmpty()) {
            Toast.makeText(this, "Enter All The Details", Toast.LENGTH_SHORT).show();
        }
        else if (name.equalsIgnoreCase(""))
        {
            userName.setError("Enter Name");
            userName.requestFocus();
        }
        else if (age.equalsIgnoreCase(""))
        {
             userAge.setError("Enter Age");
             userAge.requestFocus();
        }
        else if(age.length()>3)
        {
            userAge.setError("Enter Correct Age");
            userAge.requestFocus();
        }
        else if (email.equalsIgnoreCase(""))
        {
            userEmail.setError("Enter Email");
            userEmail.requestFocus();
        }
        else if (password.equalsIgnoreCase(""))
        {
            userPassword.setError("Enter Password");
            userPassword.requestFocus();
        }
        else if (repassword.equalsIgnoreCase(""))
        {
            userRePassword.setError("Enter Re-Password");
            userRePassword.requestFocus();
        }
        else {
            if (password.equals(repassword))
            {
                Result = true;
            }
            else {
                Toast.makeText(this,"Password Not Matching...",Toast.LENGTH_SHORT).show();
            }
        }
        return Result;
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        //firebaseAuth.signOut();
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification Mail Sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Verification Mail Has'nt Been Sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference(firebaseAuth.getUid()).child("Profile");
        /*StorageReference imageReference=storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic");
        UploadTask uploadTask=imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            }
        });*/
        UserProfile userProfile=new UserProfile(age,email,name);
        myRef.setValue(userProfile);
    }
}