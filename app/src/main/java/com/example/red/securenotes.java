package com.example.red;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class securenotes extends AppCompatActivity {
    TextView Gen, BC, EA, PP, DL, SK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_notes);
        BC = findViewById(R.id.BC);
        EA = findViewById(R.id.EA);
        PP = findViewById(R.id.PP);
        DL = findViewById(R.id.DL);
        SK = findViewById(R.id.SK);
        Gen=findViewById(R.id.gen);

        Gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(securenotes.this,General.class);
                startActivity(intent);

            }
        });

        BC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(securenotes.this,Business_Card.class);
                startActivity(intent);

            }
        });
        EA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(securenotes.this,EmailAcc.class);
                startActivity(intent);
            }
        });
        PP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(securenotes.this,Passport.class);
                startActivity(intent);
            }
        });
        DL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(securenotes.this,Drivinglicence.class);
                startActivity(intent);
            }
        });
        SK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(securenotes.this,Softwarekey.class);
                startActivity(intent);

            }
        });


    }
}
