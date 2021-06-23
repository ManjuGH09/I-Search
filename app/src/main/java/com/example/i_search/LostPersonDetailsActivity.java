package com.example.i_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;

public class LostPersonDetailsActivity extends AppCompatActivity {

    private TextView lpdDetails;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_person_details);

        lpdDetails=findViewById(R.id.tvLPDDetails);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Back Arrow

        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid()).child("Lost Person Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserLostPerson userLostPerson=dataSnapshot.getValue(UserLostPerson.class);
                lpdDetails.setText("\tLost Person Details\n\n\nName : "+userLostPerson.getLpname()+"\nAge : "+userLostPerson.getLpage()+"\nPhone Number : "
                        +userLostPerson.getLpphonenum()+"\nAddress : "+userLostPerson.getLpaddress()+ "\nGender : "+userLostPerson.getLpgender()
                        +"\nColour : "+userLostPerson.getLpcolour()+"\nHeight : "+userLostPerson.getLpheight()+"\nLost Place : "+userLostPerson.getLplostplace()
                        +"\nMentally Strong : "+userLostPerson.getLpmentallystrong()+"\nDate : "+userLostPerson.getLpdate()+"\nTime : "+userLostPerson.getLptime()
                        +"\nMissed Before This : "+userLostPerson.getLpmissedbefore()+"\nStatus : "+userLostPerson.getLpstatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LostPersonDetailsActivity.this,"Failed To Get Data",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void moveToEdit(View view)
    {
        startActivity(new Intent(LostPersonDetailsActivity.this,EditLostPersonActivity.class));
    }

    public void createMyPDF(View view)
    {
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(200,400,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String myString = lpdDetails.getText().toString();
        int x= 10,y=25;
        for (String line:myString.split("\n"))
        {
            myPage.getCanvas().drawText(line,x,y,myPaint);
            y+=myPaint.descent()-myPaint.ascent();
        }
        myPdfDocument.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath()+"/Lost Person Details.pdf";
        File myfile = new File(myFilePath);

        try {
            myPdfDocument.writeTo(new FileOutputStream(myfile));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(LostPersonDetailsActivity.this,"Error In Downloading PDF...",Toast.LENGTH_SHORT).show();
        }
        myPdfDocument.close();
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
