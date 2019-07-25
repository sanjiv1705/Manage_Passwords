package com.example.red;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit extends AppCompatActivity {
    Button savechng;
    EditText name,email,country,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        savechng=findViewById(R.id.savechng);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        country=findViewById(R.id.country);
        password=findViewById(R.id.password);

        savechng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit.this,MainActivity.class);
                Toast.makeText(Edit.this, "Changes done successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
    }
}
