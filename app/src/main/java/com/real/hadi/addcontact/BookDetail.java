package com.real.hadi.addcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class BookDetail extends AppCompatActivity {
    String title,detail,userId,fullname,token;
    TextView txtDetail;
    ImageView imgBack,imgShare;

    Button btnExite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        txtDetail=(TextView) findViewById(R.id.txtDetail);
        btnExite=(Button) findViewById(R.id.btnExite);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        detail=intent.getStringExtra("detail");
        userId=intent.getStringExtra("userId");
        fullname = intent.getStringExtra("fullname");
        token = intent.getStringExtra("token");
        txtDetail.setText(detail);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, detail);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));            }
        });


    }
}
