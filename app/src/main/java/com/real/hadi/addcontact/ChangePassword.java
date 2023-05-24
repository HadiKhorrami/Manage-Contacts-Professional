package com.real.hadi.addcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class ChangePassword extends AppCompatActivity {
    String userId,token,fullname;
Button btnExite,btnAdd;
    ImageView imgBack;

EditText edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btnExite = (Button) findViewById(R.id.btnExite);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        token = intent.getStringExtra("token");
        fullname = intent.getStringExtra("fullname");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPassword.getText().toString().equals("")) {
                    Toast.makeText(ChangePassword.this, "لطفا رمز جدید را وارد کنید", Toast.LENGTH_SHORT).show();
                } else if (!edtPassword.getText().toString().equals(null)) {
                    StringRequest request1 = new StringRequest(Request.Method.PATCH, getString(R.string.url)+"/api/users/" + userId, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            edtPassword.setText(null);
                            Toast.makeText(ChangePassword.this, "رمز عبور کاربر روز رسانی شد", Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            // do something
                        }
                    }) {
                        Map<String, String> params = new HashMap<String, String>();

                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            params.put("firstname", "");
                            params.put("lastname", "");
                            params.put("phone", "");
                            params.put("education", "");
                            params.put("job", "");
                            params.put("workAddress", "");
                            params.put("birthdate", "");
                            params.put("district", "");
                            params.put("fallowUp", "");
                            params.put("password", edtPassword.getText().toString());
                            params.put("userId", userId);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            // add headers <key,value>
                            params.put("access_token", token);
                            return params;
                        }
                    };
                    ;

                    request1.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    RequestQueue requestQueue1 = Volley.newRequestQueue(ChangePassword.this);
                    requestQueue1.add(request1);
                }
            }
        });
    }
}
