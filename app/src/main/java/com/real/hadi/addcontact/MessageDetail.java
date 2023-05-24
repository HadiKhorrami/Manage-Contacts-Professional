package com.real.hadi.addcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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


public class MessageDetail extends AppCompatActivity {
    String detail, userId, fullname, token,messageId;
    TextView txtDetail;
    ImageView imgBack,imgShare;
    Button btnExite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        txtDetail = (TextView) findViewById(R.id.txtDetail);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgShare = (ImageView) findViewById(R.id.imgShare);

        btnExite = (Button) findViewById(R.id.btnExite);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MessageDetail.this,Inbox.class);
                intent.putExtra("flag","fromdetail");
                intent.putExtra("userId",userId);
                intent.putExtra("messageId",messageId);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        messageId = intent.getStringExtra("messageId");
        detail = intent.getStringExtra("detail");
        userId = intent.getStringExtra("userId");
        fullname = intent.getStringExtra("fullname");
        token = intent.getStringExtra("token");
        txtDetail.setText(detail);

        StringRequest request1 = new StringRequest(Request.Method.POST, getString(R.string.url)+"/api/messages/seen", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            Map<String, String> params = new HashMap<String, String>();

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                params.put("messageid", messageId);
                params.put("userid", userId);
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(MessageDetail.this);
        requestQueue1.add(request1);

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, detail);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, detail);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));            }
        });





    }
}
