package com.example.red;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;

    FloatingActionButton fab;
    DataBaseHelper mDatabaseHelper;
    private static final String TAG = "MainActivity";

    private ListView mListView;
    WebView webview;
    TextView tv_header_name, tv_header_email,tv_name,tv_email;
    Button btn , btn1 , btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
       // mListView = (ListView) findViewById(R.id.list_view);
        mDatabaseHelper = new DataBaseHelper(this);
        context = MainActivity.this;
        btn = findViewById(R.id.btn2);
        btn1 = findViewById(R.id.btn3);
        btn2 = findViewById(R.id.btn4);


        webview = (WebView) findViewById(R.id.webView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , ManageSites.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteAllData();
                Cursor resn = mDatabaseHelper.getData() ;
                if(resn.getCount()==0)
                {
                    Toast.makeText(MainActivity.this, "Nothing left", Toast.LENGTH_SHORT).show();
                    showMessage("Data", "Zero Data Left" );
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data still Remaing", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateListView();

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSite.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Create a site here", Toast.LENGTH_SHORT).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        tv_header_name = headerview.findViewById(R.id.tv_header_name);

        tv_header_name.setText(LocalSharedPreferences.getname(this));

        tv_header_email = headerview.findViewById(R.id.tv_header_email);
        tv_header_email.setText(LocalSharedPreferences.getemail(this));
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while(data.moveToNext()){

            buffer.append("Id: " + data.getString(0)+"\n");
            buffer.append("name: " + data.getString(1)+"\n");
            buffer.append(("url: " + data.getString(2) +"\n"));
            buffer.append(("username: " + data.getString(3)+"\n"));
            buffer.append("password: " +data.getString(4)+"\n");
            buffer.append("note: " +data.getString(5)+ "\n");
            //get the value from the database in column 1
            //then add it to the ArrayList
           // listData.add(data.getString(1));
//            listData.add(data.getString(2));
//            listData.add(data.getString(3));
//            listData.add(data.getString(4));
//            listData.add(data.getString(5));
        }
        showMessage("Data" , buffer.toString());
        //create the list adapter and set the adapter
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        mListView.setAdapter(adapter);
//
//        //set an onItemClickListener to the ListView
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = adapterView.getItemAtPosition(i).toString();
//                Log.d(TAG, "onItemClick: You Clicked on " + name);
//
//                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name
//                int itemID = -1;
//                while(data.moveToNext()){
//                    itemID = data.getInt(0);
//                }
//                if(itemID > -1){
//                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
//                   // Intent editScreenIntent = new Intent(MainActivity.this, EditData.class);
//                    //editScreenIntent.putExtra("id",itemID);
//                    //editScreenIntent.putExtra("name",name);
//                    //startActivity(editScreenIntent);
//                }
//                else{
//                    toastMessage("No ID associated with that name");
//                }
//            }
//        });
    }
    public void showMessage(String title , String Message){
        AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
        a.setCancelable(true);
        a.setTitle(title);
        a.setMessage(Message);
        a.show();
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sites) {
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_notes) {
            Intent intent = new Intent(MainActivity.this,securenotes.class);
            startActivity(intent);

        } else if (id == R.id.nav_card) {
            Intent intent = new Intent(MainActivity.this,card.class);
            startActivity(intent);

        } else if (id == R.id.nav_browser) {

            webview.loadUrl("https://www.google.com/");

        }else if (id == R.id.nav_editaccount) {
            Intent intent = new Intent(MainActivity.this,Edit.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
            LocalSharedPreferences.saveIsLogin( context , false );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
