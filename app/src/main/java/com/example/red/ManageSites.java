package com.example.red;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageSites extends AppCompatActivity {
    ListView mlistView;
    private String TAG = "ManageSites";
    DataBaseHelper dbh;
    String name;
    int itemID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sites);
        mlistView = findViewById(R.id.list_view);
        dbh = new DataBaseHelper(this);
        populateListView();
    }

    private void populateListView() {
        Cursor data = dbh.getData();
        ArrayList<String> al = new ArrayList<>();
        while(data.moveToNext()){
            al.add(data.getString(1));
        }
        final ListAdapter listAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , al);
        mlistView.setAdapter(listAdapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 name = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = dbh.getItemID(name); //get the id associated with that name
                 itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
//                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
//                    Intent editScreenIntent = new Intent(ManageSites.this, MainActivity.class);
//                    editScreenIntent.putExtra("id",itemID);
//                    editScreenIntent.putExtra("name",name);
//                    startActivity(editScreenIntent);
                    registerForContextMenu(mlistView);

                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }



        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.context_manage , menu);
        menu.setHeaderTitle("Are you sure !!");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.delete:
                dbh.deleteName(itemID , name);
                Toast.makeText(this, "Id deleted permanently", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.c2:
//                Toast.makeText(this, "Message Him", Toast.LENGTH_SHORT).show();
//                break;


        }
        return true;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
