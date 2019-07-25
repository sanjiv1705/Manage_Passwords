package com.example.red;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edit_email, edit_password;
    Button btn_login;
    TextView create;
    Context context;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = Login.this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);


        btn_login = findViewById(R.id.btn_login);
        create = findViewById(R.id.create);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);

        if (LocalSharedPreferences.getIsLogin(context) == false) {
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CallApi();
                    progressDialog.show();

                }
            });
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, Create.class);
                    startActivity(intent);
                }
            });

        } else {
            Intent i = new Intent(Login.this , MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    private void CallApi() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://wanted.today/wanted_demo/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //   Toast.makeText(Login.this, "" + s, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("success").equals("1")) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                        progressDialog.dismiss();

                        LocalSharedPreferences.saveIsLogin(Login.this, true);

                        String usertype = jsonObject1.getString("name");
                        LocalSharedPreferences.savename(context, usertype);
                        Toast.makeText(context, "" + LocalSharedPreferences.getname(context), Toast.LENGTH_SHORT).show();

                        String email = jsonObject1.getString("email");
                        LocalSharedPreferences.saveemail(context, email);
                        //  Toast.makeText(context, ""+LocalSharedPreferences.getemail(context), Toast.LENGTH_SHORT).show();

                        String user__id = jsonObject1.getString("id");
                        LocalSharedPreferences.saveUserid(context, user__id);
                        //    Toast.makeText(context, ""+LocalSharedPreferences.getUserid(context), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Please Check email & password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Login.this, "" + volleyError, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", edit_email.getText().toString());
                params.put("password", edit_password.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

