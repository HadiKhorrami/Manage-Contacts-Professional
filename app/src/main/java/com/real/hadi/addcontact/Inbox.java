package com.real.hadi.addcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Inbox extends AppCompatActivity {
    String userId,fullname,token,flag;
    Button btnExite;
    ImageView imgBack;
    RecyclerView recyclerView;
    ArrayList<MessageListLayout> sampleList;
    ProgressBar progressbar;
    MessageListLayoutRecyclerAdapter messageListLayoutRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        btnExite = (Button) findViewById(R.id.btnExite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sampleList = new ArrayList<>();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        fullname = intent.getStringExtra("fullname");
        token = intent.getStringExtra("token");
        flag = intent.getStringExtra("flag");

            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Inbox.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
            });
//            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getString(R.string.url)+"/api/messages/my?user_id="+userId+"&access_token="+token
//                    , null, new Response.Listener<JSONArray>() {
//                @Override
//                public void onResponse(JSONArray response) {
//                    try {
//
//                        progressbar.setVisibility(View.INVISIBLE);
                        for (int i = 0; i <5; i++) {
//                            JSONObject jsonObject = response.getJSONObject(i);

                            MessageListLayout messageListLayout = new MessageListLayout();
                            ArrayList<String> read=new ArrayList<String>();
                            String title =("هشدار");
                            messageListLayout.setTitle(title);
                            String detail = ("جلسه فردا بسیار مهم است فراموش نشود");
                            messageListLayout.setDetail(detail);
                            String priority =("1");
                            messageListLayout.setPriority(priority);
                            String id =("id");
                            messageListLayout.setId(id);
//                            JSONArray to = jsonObject.getJSONArray("to");
//                            JSONArray reads = jsonObject.getJSONArray("reads");
//                            for (int j = 0; j < reads.length(); j++) {
//                               read.add(reads.getString(j));
//                            }
//                            messageListLayout.setReads(read);
//                            messageListLayout.setUserId(userId);
//                            messageListLayout.setFullname(fullname);
//                            messageListLayout.setToken(token);
                            sampleList.add(messageListLayout);
                            messageListLayoutRecyclerAdapter = new MessageListLayoutRecyclerAdapter(sampleList);
                            recyclerView.setAdapter(messageListLayoutRecyclerAdapter);
                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(Inbox.this,"خطا در دریافت اطلاعات",Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                Map<String, String> params = new HashMap<String, String>();
//
//                public Map<String, String> getParams() throws AuthFailureError {
////                params.put("userId", userId);
//                    return params;
//                }
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    // add headers <key,value>
//                    params.put("access_token", token);
//                    return params;
//                }
//            };
//            request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            RequestQueue requestQueue = Volley.newRequestQueue(Inbox.this);
//            requestQueue.add(request);
            recyclerView.setLayoutManager(new GridLayoutManager(Inbox.this, 1, LinearLayoutManager.VERTICAL, false));

        }
    }

