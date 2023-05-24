package com.real.hadi.addcontact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContactList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ListLayout> sampleList;
    ListLayoutRecyclerAdapter listLayoutRecyclerAdapter;
    ImageView imgBack;
    EditText edtSearch;
    ProgressBar progressBar;
    Button btnCount, btnAddContact, btnExite, btnSms, btnCheckAll;
    String message = null, fromLogIn = null, fromAddMulti = null, fromMain = null, sms, sms1 = "", sms2 = "";
    String userId, fullname, token, flag;
    ScrollView scrollView;

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnCount = (Button) findViewById(R.id.btnCount);
        btnSms = (Button) findViewById(R.id.btnSms);
        btnCheckAll = (Button) findViewById(R.id.btnCheckAll);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        btnExite = (Button) findViewById(R.id.btnExite);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactList.this));
        sampleList = new ArrayList<>();
//        scrollView.fullScroll(View.FOCUS_DOWN);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ContactList.this);
        SharedPreferences.Editor loginPrefsEditor = prefs.edit();
        loginPrefsEditor.putString("checkall", "");
        loginPrefsEditor.commit();


        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms2="";
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getString(R.string.url) + "/api/people?filter=%7B%22where%22%3A%7B%22userId%22%3A%22" + userId + "%22%7D%2C%22order%22%3A%22firstname%20ASC%22%7D"
                        , null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                ListLayout listLayout = new ListLayout();
                                String firstname = jsonObject.getString("firstname");
                                listLayout.setName(firstname);
                                String lastname = jsonObject.getString("lastname");
                                listLayout.setLastname(lastname);
                                String phone = jsonObject.getString("phone");
                                listLayout.setPhone(phone);
                                sms1 += phone + ";";
                                Boolean is_sms = jsonObject.getBoolean("is_sms");
                                if (is_sms == true) {
                                    sms2 += phone + ";";

                                }

                                String education = jsonObject.getString("education");
                                listLayout.setEducation(education);

                                String job = jsonObject.getString("job");
                                listLayout.setJob(job);

                                String workAddress = jsonObject.getString("workAddress");
                                listLayout.setWorkAddress(workAddress);

                                String birthdate = jsonObject.getString("birthdate");
                                listLayout.setBirthday(birthdate);

                                String district = jsonObject.getString("district");
                                listLayout.setDistrict(district);

                                String id = jsonObject.getString("id");
                                listLayout.setId(id);
                                listLayout.setId(id);
                                listLayout.setFullname(fullname);
                                listLayout.setToken(token);

                                JSONArray fallowUp = jsonObject.getJSONArray("fallowUp");
                                ArrayList<String> dateArr = new ArrayList<>(fallowUp.length());
                                ArrayList<String> descArr = new ArrayList<>(fallowUp.length());
                                listLayout.setFallowUplength(String.valueOf(fallowUp.length()));
                                for (int j = 0; j < fallowUp.length(); j++) {
                                    JSONObject jsonObject1 = fallowUp.getJSONObject(j);
                                    dateArr.add(jsonObject1.getString("trackDate"));
                                    descArr.add(jsonObject1.getString("description"));

//                            String trackdate = jsonObject1.getString("trackDate");
                                    listLayout.setTrackdate(dateArr);
//                            String description = jsonObject1.getString("description");
                                    listLayout.setDescription(descArr);
                                }
                                String userId = jsonObject.getString("userId");
                                listLayout.setUserId(userId);
                                listLayout.setToken(token);
                                listLayout.setFullname(fullname);
                                Integer field2 = jsonObject.getInt("field2");
                                listLayout.setField2(field2);
                                Integer field3 = jsonObject.getInt("field3");
                                listLayout.setField3(field3);
                                Integer field4 = jsonObject.getInt("field4");
                                listLayout.setField4(field4);
                                Integer field5 = jsonObject.getInt("field5");
                                listLayout.setField5(field5);
                                sampleList.add(listLayout);
                                listLayoutRecyclerAdapter = new ListLayoutRecyclerAdapter(sampleList);
                                recyclerView.setAdapter(listLayoutRecyclerAdapter);

                            }
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sms2, null)));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactList.this, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    Map<String, String> params = new HashMap<String, String>();

                    public Map<String, String> getParams() throws AuthFailureError {
//                params.put("userId", userId);
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(ContactList.this);
                requestQueue.add(request);

                /////////////////////////////////////////////////////////////////////////////////////
                StringRequest request1 = new StringRequest(Request.Method.POST, "http://171.22.25.90/api/people/update?where=%7B%22is_sms%22%3A%22true%22%7D", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ContactList.this, error+"", Toast.LENGTH_SHORT).show();

                        // do something
                    }
                }) {
                    Map<String, String> params = new HashMap<String, String>();

                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        params.put("is_sms", "false");
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
                RequestQueue requestQueue1 = Volley.newRequestQueue(ContactList.this);
                requestQueue1.add(request1);

//                Intent intent = new Intent(ContactList.this, ContactList.class);
//                intent.putExtra("userId", userId);
//                intent.putExtra("fullname", fullname);
//                intent.putExtra("token", token);
//                intent.putExtra("flag", "own");
//                startActivity(intent);
/////////////////////////////////////////////////////////////////////////////////////////////////
            }
        });
        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sms1, null)));
            }
        });
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        fromLogIn = intent.getStringExtra("fromLogIn");
        fromAddMulti = intent.getStringExtra("fromAddMulti");
        userId = intent.getStringExtra("userId");
        token = intent.getStringExtra("token");
        fullname = intent.getStringExtra("fullname");
        flag = intent.getStringExtra("flag");

        if (flag.equals("other")) {
            Toast.makeText(ContactList.this, "برای ارسال پیامک، روی مخاطب کلیک کرده و نگه دارید", Toast.LENGTH_LONG).show();

        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equals("own")) {
                    Intent intent = new Intent(ContactList.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    startActivity(intent);
                } else if (flag.equals("other")) {
                    onBackPressed();
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sampleList.clear();
                String string = null;
                try {
                    string = URLEncoder.encode(edtSearch.getText().toString(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://171.22.25.90/api/people?filter=%7B%22where%22%3A%7B%22lastname%22%3A%7B%22regexp%22%3A%22" + string + "%22%7D%2C%22userId%22%3A%22" + userId + "%22%7D%7D"
                        , null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                ListLayout listLayout = new ListLayout();
                                String firstname = jsonObject.getString("firstname");
                                listLayout.setName(firstname);
                                String lastname = jsonObject.getString("lastname");
                                listLayout.setLastname(lastname);
                                String phone = jsonObject.getString("phone");
                                listLayout.setPhone(phone);

                                String education = jsonObject.getString("education");
                                listLayout.setEducation(education);

                                String job = jsonObject.getString("job");
                                listLayout.setJob(job);

                                String workAddress = jsonObject.getString("workAddress");
                                listLayout.setWorkAddress(workAddress);

                                String birthdate = jsonObject.getString("birthdate");
                                listLayout.setBirthday(birthdate);

                                String district = jsonObject.getString("district");
                                listLayout.setDistrict(district);

                                String id = jsonObject.getString("id");
                                listLayout.setId(id);

                                JSONArray fallowUp = jsonObject.getJSONArray("fallowUp");
                                ArrayList<String> dateArr = new ArrayList<>(fallowUp.length());
                                ArrayList<String> descArr = new ArrayList<>(fallowUp.length());
                                listLayout.setFallowUplength(String.valueOf(fallowUp.length()));
                                for (int j = 0; j < fallowUp.length(); j++) {
                                    JSONObject jsonObject1 = fallowUp.getJSONObject(j);
                                    dateArr.add(jsonObject1.getString("trackDate"));
                                    descArr.add(jsonObject1.getString("description"));

//                            String trackdate = jsonObject1.getString("trackDate");
                                    listLayout.setTrackdate(dateArr);
//                            String description = jsonObject1.getString("description");
                                    listLayout.setDescription(descArr);
                                }
                                String userId = jsonObject.getString("userId");
                                listLayout.setUserId(userId);

                                Integer field2 = jsonObject.getInt("field2");
                                listLayout.setField2(field2);
                                Integer field3 = jsonObject.getInt("field3");
                                listLayout.setField3(field3);
                                Integer field4 = jsonObject.getInt("field4");
                                listLayout.setField4(field4);
                                Integer field5 = jsonObject.getInt("field5");
                                listLayout.setField5(field5);

                                sampleList.add(listLayout);
                                listLayoutRecyclerAdapter = new ListLayoutRecyclerAdapter(sampleList);
                                recyclerView.setAdapter(listLayoutRecyclerAdapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // do something
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
                RequestQueue requestQueue = Volley.newRequestQueue(ContactList.this);
                requestQueue.add(request);
                recyclerView.setLayoutManager(new GridLayoutManager(ContactList.this, 1, LinearLayoutManager.VERTICAL, false));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        StringRequest request1 = new StringRequest(Request.Method.GET, getString(R.string.url) + "/api/people/count?where=%7B%22userId%22%3A%22" + userId + "%22%7D", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String count = jsonObject.getString("count");
                    btnCount.setText(count + " نفر");
//                    Toast.makeText(ContactList.this,count+"",Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                return params;
            }
        };
        ;

        request1.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue1 = Volley.newRequestQueue(ContactList.this);
        requestQueue1.add(request1);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(ContactList.this, AddContact.class);
                                                 intent.putExtra("userId", userId);
                                                 intent.putExtra("fullname", fullname);
                                                 intent.putExtra("token", token);
                                                 intent.putExtra("fromLogIn", "fromLogIn");
                                                 startActivity(intent);
                                                 overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                             }
                                         }
        );


//        if (message.equals("Delete")||message==null) {
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getString(R.string.url) + "/api/people?filter=%7B%22where%22%3A%7B%22userId%22%3A%22" + userId + "%22%7D%2C%22order%22%3A%22firstname%20ASC%22%7D"
//                , null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    progressBar.setVisibility(View.INVISIBLE);

                    for (int i = 0; i < 5; i++) {

//                        JSONObject jsonObject = response.getJSONObject(i);

                        ListLayout listLayout = new ListLayout();
                        String firstname = ("هادی");
                        listLayout.setName(firstname);
                        String lastname =("خرمی فرد");
                        listLayout.setLastname(lastname);
                        String phone =("09147599665");
                        listLayout.setPhone(phone);
                        sms1 += phone + ";";
//                        Boolean is_sms =("is_sms");
//                        if (is_sms == true) {
//                            sms2 += phone + ";";
//                        }
//                        String education = jsonObject.getString("education");
//                        listLayout.setEducation(education);
//
//                        String job = jsonObject.getString("job");
//                        listLayout.setJob(job);
//
//                        String workAddress = jsonObject.getString("workAddress");
//                        listLayout.setWorkAddress(workAddress);
//
//                        String birthdate = jsonObject.getString("birthdate");
//                        listLayout.setBirthday(birthdate);
//
//                        String district = jsonObject.getString("district");
//                        listLayout.setDistrict(district);
//
//                        String id = jsonObject.getString("id");
//                        listLayout.setId(id);
//                        listLayout.setId(id);
//                        listLayout.setFullname(fullname);
//                        listLayout.setToken(token);
//
//                        JSONArray fallowUp = jsonObject.getJSONArray("fallowUp");
//                        ArrayList<String> dateArr = new ArrayList<>(fallowUp.length());
//                        ArrayList<String> descArr = new ArrayList<>(fallowUp.length());
//                        listLayout.setFallowUplength(String.valueOf(fallowUp.length()));
//                        for (int j = 0; j < fallowUp.length(); j++) {
//                            JSONObject jsonObject1 = fallowUp.getJSONObject(j);
//                            dateArr.add(jsonObject1.getString("trackDate"));
//                            descArr.add(jsonObject1.getString("description"));
//
////                            String trackdate = jsonObject1.getString("trackDate");
//                            listLayout.setTrackdate(dateArr);
////                            String description = jsonObject1.getString("description");
//                            listLayout.setDescription(descArr);
//                        }
//                        String userId = jsonObject.getString("userId");
//                        listLayout.setUserId(userId);
//                        listLayout.setToken(token);
//                        listLayout.setFullname(fullname);
//                        Integer field2 = jsonObject.getInt("field2");
//                        listLayout.setField2(field2);
//                        Integer field3 = jsonObject.getInt("field3");
//                        listLayout.setField3(field3);
//                        Integer field4 = jsonObject.getInt("field4");
//                        listLayout.setField4(field4);
//                        Integer field5 = jsonObject.getInt("field5");
//                        listLayout.setField5(field5);
                        sampleList.add(listLayout);
                        listLayoutRecyclerAdapter = new ListLayoutRecyclerAdapter(sampleList);
                        recyclerView.setAdapter(listLayoutRecyclerAdapter);

                    }

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ContactList.this, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            Map<String, String> params = new HashMap<String, String>();
//
//            public Map<String, String> getParams() throws AuthFailureError {
////                params.put("userId", userId);
//                return params;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(ContactList.this);
//        requestQueue.add(request);
        recyclerView.setLayoutManager(new GridLayoutManager(ContactList.this, 1, LinearLayoutManager.VERTICAL, false));


    }


}
//}

