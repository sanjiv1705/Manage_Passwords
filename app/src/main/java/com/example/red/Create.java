package com.example.red;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Create extends AppCompatActivity {

    EditText name, email, country, password, pin, cp;
    Button signup, signfb, signgoogle;

    private static ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        country = findViewById(R.id.country);
        password = findViewById(R.id.password);
        pin = findViewById(R.id.pin);
        cp = findViewById(R.id.cp);
        signup=findViewById(R.id.signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().length()==0)
                    name.setError("Field must be fill");
                else  if(email.getText().toString().length()==0)
                    email.setError("Field must be fill");
                else  if(country.getText().toString().length()==0)
                    country.setError("Field must be fill");
                else  if(password.getText().toString().length()==0)
                    password.setError("Field must be fill");
                else  if(cp.getText().toString().length()==0)
                    cp.setError("Field must be fill");
                else  if(pin.getText().toString().length()==0)
                    pin.setError("Field must be fill");
                else if(password.getText().toString()!=cp.getText().toString())
                {cp.setError("Password not same");
                    Toast.makeText(Create.this, "Password is not same", Toast.LENGTH_SHORT).show();}
                else{
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(Create.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/register_user.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("data", s);
                        progressDialog.dismiss();
                        Toast.makeText(Create.this, "" + s, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getString("success").equals("1")) {
                                Toast.makeText(Create.this, "Account Created", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                finish();
                                startActivity(getIntent());

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Create.this, "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(Create.this, "" + volleyError, Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", name.getText().toString());
                        params.put("email", email.getText().toString());
                        params.put("password", country.getText().toString());
                        params.put("mobile", password.getText().toString());
                        params.put("address", cp.getText().toString());
                        params.put("college", pin.getText().toString());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);


            }}
        });

    }
}
