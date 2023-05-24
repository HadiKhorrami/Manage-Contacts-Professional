package com.real.hadi.addcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Book extends AppCompatActivity {
    String userId, fullname, token;
    Button btnExite;
    RecyclerView recyclerView;
    ImageView imgBack;
    ProgressBar progressbar;
    ArrayList<BookListLayout> sampleList;
    ArrayList<String> spnTitleList, spnIDList;
    Spinner spinner;
    BookListLayoutRecyclerAdapter bookListLayoutRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        btnExite = (Button) findViewById(R.id.btnExite);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        spinner = (Spinner) findViewById(R.id.spinner);
        sampleList = new ArrayList<>();
        spnTitleList = new ArrayList<>();
        spnIDList = new ArrayList<>();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        fullname = intent.getStringExtra("fullname");
        token = intent.getStringExtra("token");

        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, getString(R.string.url) +"/api/notebookCategories"
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        spnIDList.add(id);
                        spnTitleList.add(title);
//                        Toast.makeText(Book.this,spnIDList +"",Toast.LENGTH_LONG).show();
//                        Toast.makeText(Book.this,spnTitleList +"",Toast.LENGTH_LONG).show();
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                if (position > 0) {
                                    String sortId = spnIDList.get(position - 1);
                                    sampleList.clear();
                                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getString(R.string.url) +"/api/notebookCategories/" + sortId + "/notebooks"
                                            , null, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            try {
                                                for (int i = 0; i < response.length(); i++) {

                                                    JSONObject jsonObject = response.getJSONObject(i);

                                                    BookListLayout bookListLayout = new BookListLayout();
                                                    String title = jsonObject.getString("title");
                                                    bookListLayout.setTitle(title);
                                                    String detail = jsonObject.getString("content");
                                                    bookListLayout.setDetail(detail);

                                                    String id = jsonObject.getString("id");
                                                    bookListLayout.setId(id);

                                                    String cover = jsonObject.getString("cover");
                                                    bookListLayout.setCoverId(cover);

                                                    bookListLayout.setUserId(userId);
                                                    bookListLayout.setFullname(fullname);
                                                    bookListLayout.setToken(token);

                                                    sampleList.add(bookListLayout);
                                                    bookListLayoutRecyclerAdapter = new BookListLayoutRecyclerAdapter(sampleList);
                                                    recyclerView.setAdapter(bookListLayoutRecyclerAdapter);

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(Book.this, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                                        }
                                    }) {
                                        Map<String, String> params = new HashMap<String, String>();

                                        public Map<String, String> getParams() throws AuthFailureError {
//                params.put("userId", userId);
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
                                    request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    RequestQueue requestQueue = Volley.newRequestQueue(Book.this);
                                    requestQueue.add(request);
                                    recyclerView.setLayoutManager(new GridLayoutManager(Book.this, 2, LinearLayoutManager.VERTICAL, false));
                                }else if(position==0){
                                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getString(R.string.url) + "/api/notebooks"
                                            , null, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            try {
                                                progressbar.setVisibility(View.INVISIBLE);

                                                for (int i = 0; i < response.length(); i++) {

                                                    JSONObject jsonObject = response.getJSONObject(i);

                                                    BookListLayout bookListLayout = new BookListLayout();
                                                    String title = jsonObject.getString("title");
                                                    bookListLayout.setTitle(title);
                                                    String detail = jsonObject.getString("content");
                                                    bookListLayout.setDetail(detail);

                                                    String id = jsonObject.getString("id");
                                                    bookListLayout.setId(id);

                                                    String cover = jsonObject.getString("cover");
                                                    bookListLayout.setCoverId(cover);

                                                    bookListLayout.setUserId(userId);
                                                    bookListLayout.setFullname(fullname);
                                                    bookListLayout.setToken(token);

                                                    sampleList.add(bookListLayout);
                                                    bookListLayoutRecyclerAdapter = new BookListLayoutRecyclerAdapter(sampleList);
                                                    recyclerView.setAdapter(bookListLayoutRecyclerAdapter);

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(Book.this, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                                        }
                                    }) {
                                        Map<String, String> params = new HashMap<String, String>();

                                        public Map<String, String> getParams() throws AuthFailureError {
//                params.put("userId", userId);
                                            return params;
                                        }
                                    };
                                    request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    RequestQueue requestQueue = Volley.newRequestQueue(Book.this);
                                    requestQueue.add(request);
                                    recyclerView.setLayoutManager(new GridLayoutManager(Book.this, 2, LinearLayoutManager.VERTICAL, false));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Book.this, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
            }
        }) {
            Map<String, String> params = new HashMap<String, String>();

            public Map<String, String> getParams() throws AuthFailureError {
//                params.put("userId", userId);
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
        request1.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue1 = Volley.newRequestQueue(Book.this);
        requestQueue1.add(request1);
        spnTitleList.add("همه");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner, spnTitleList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(arrayAdapter);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getString(R.string.url) + "/api/notebooks"
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    progressbar.setVisibility(View.INVISIBLE);

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        BookListLayout bookListLayout = new BookListLayout();
                        String title = jsonObject.getString("title");
                        bookListLayout.setTitle(title);
                        String detail = jsonObject.getString("content");
                        bookListLayout.setDetail(detail);

                        String id = jsonObject.getString("id");
                        bookListLayout.setId(id);

                        String cover = jsonObject.getString("cover");
                        bookListLayout.setCoverId(cover);

                        bookListLayout.setUserId(userId);
                        bookListLayout.setFullname(fullname);
                        bookListLayout.setToken(token);

                        sampleList.add(bookListLayout);
                        bookListLayoutRecyclerAdapter = new BookListLayoutRecyclerAdapter(sampleList);
                        recyclerView.setAdapter(bookListLayoutRecyclerAdapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Book.this, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
            }
        }) {
            Map<String, String> params = new HashMap<String, String>();

            public Map<String, String> getParams() throws AuthFailureError {
//                params.put("userId", userId);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Book.this);
        requestQueue.add(request);
        recyclerView.setLayoutManager(new GridLayoutManager(Book.this, 2, LinearLayoutManager.VERTICAL, false));

    }
}
