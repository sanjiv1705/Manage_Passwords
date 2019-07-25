package com.example.red;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSite extends AppCompatActivity {
    Button btn_add;
    private static final String TAG = "AddSite";

    DataBaseHelper mDatabaseHelper;
    EditText name , url , username , password , note ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);
        name = findViewById(R.id.name);
        url = findViewById(R.id.url);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        note = findViewById(R.id.note);
        mDatabaseHelper = new DataBaseHelper(this);

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = name.getText().toString();
                String newEntry1 = url.getText().toString();
                String newEntry2 = username.getText().toString();
                String newEntry3 = password.getText().toString();
                String newEntry4 = note.getText().toString();



                if (name.length() != 0 && url.length()!=0) {
                    AddData(newEntry , newEntry1 , newEntry2 ,newEntry3 , newEntry4);
                    name.setText("");
                } else {
                    toastMessage("You must put something in the text field!");
                }


            }
        });
    }

    private void toastMessage(String s) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

    private void AddData(String name , String url , String Username , String Password, String note) {
        boolean insertData = mDatabaseHelper.insertData(name , url , Username , Password , note);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
}
