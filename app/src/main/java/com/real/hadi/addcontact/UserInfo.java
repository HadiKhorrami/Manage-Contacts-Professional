package com.real.hadi.addcontact;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class UserInfo extends AppCompatActivity {
    ArrayList<String> jobList, educationList, districtList;
    AppCompatSpinner spnJob, spnEducation, spnDistrict;
    EditText edtBirthDay;
    ImageView imgBack;

    PersianDatePickerDialog picker;
    Button btnAddDetail, btnExite;
    String userId, token, fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        educationList = new ArrayList<>();
        districtList = new ArrayList<>();
        jobList = new ArrayList<>();
        imgBack = (ImageView) findViewById(R.id.imgBack);

        spnDistrict = (AppCompatSpinner) findViewById(R.id.spnDistrict);
        spnEducation = (AppCompatSpinner) findViewById(R.id.spnEducation);
        spnJob = (AppCompatSpinner) findViewById(R.id.spnJob);
        edtBirthDay = (EditText) findViewById(R.id.edtBirthDay);
        btnAddDetail = (Button) findViewById(R.id.btnAddDetail);
        btnExite = (Button) findViewById(R.id.btnExite);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        token = intent.getStringExtra("token");
        fullname = intent.getStringExtra("fullname");


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        educationList.add("تحصیلات");
        educationList.add("زیر دیپلم");
        educationList.add("دیپلم");
        educationList.add("فوق دیپلم");
        educationList.add("لیسانس");
        educationList.add("فوق لیسانس");
        educationList.add("دکترا");
        educationList.add("حوزوی");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner, educationList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner);
        spnEducation.setAdapter(arrayAdapter);


        jobList.add("شغل");
        jobList.add("آزاد");
        jobList.add("دولتی");
        jobList.add("بیکار");
        jobList.add("دانشجو");
        jobList.add("خصوصی");

        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner, jobList);
        arrayAdapter1.setDropDownViewResource(R.layout.spinner);
        spnJob.setAdapter(arrayAdapter1);

        districtList.add("محله");
        districtList.add("ولیعصر");
        districtList.add("یاغچیان");
        districtList.add("آبرسان");
        districtList.add("منصور");
        districtList.add("راه آهن");
        districtList.add("دامپزشکی");
        districtList.add("عباسی");
        districtList.add("منظریه");
        districtList.add("زعفرانیه");
        districtList.add("قره آغاج");
        districtList.add("نصف راه");
        districtList.add("فجر");
        districtList.add("رسالت");
        districtList.add("باغمیشه");
        districtList.add("گلکار");
        districtList.add("حیدر آباد");
        districtList.add("کوچه باغ");
        districtList.add("آذرشهر");
        districtList.add("اسکو");
        districtList.add("بستان آباد");
        districtList.add("مراغه");
        districtList.add("میانه");
        districtList.add("شبستر");
        districtList.add("هشترود");
        districtList.add("کلیبر");
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner, districtList);
        arrayAdapter2.setDropDownViewResource(R.layout.spinner);
        spnDistrict.setAdapter(arrayAdapter2);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        StringRequest request1 = new StringRequest(Request.Method.GET, getString(R.string.url) + "/api/users/" + userId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String education = jsonObject.getString("education");
                    String job = jsonObject.getString("job");
                    String birthdate = jsonObject.getString("birthdate");
                    String district = jsonObject.getString("district");
//                    String fullname = jsonObject.getString("fullname");
//                    String phone = jsonObject.getString("phone");
//                    String a = jsonObject.getString("a");
//                    String b = jsonObject.getString("b");
//                    String c = jsonObject.getString("c");
//                    String d = jsonObject.getString("d");
//                    String e = jsonObject.getString("e");
//                    String ranking = jsonObject.getString("ranking");
//                    String realm = jsonObject.getString("realm");
//                    String username = jsonObject.getString("username");
//                    String email = jsonObject.getString("email");
//                    boolean emailVerified = jsonObject.getBoolean("emailVerified");
//                    String id = jsonObject.getString("id");
//                    String userId = jsonObject.getString("userId");
//                    String workAddress = jsonObject.getString("workAddress");
//                    String lastname = jsonObject.getString("lastname");
//                    String firstname = jsonObject.getString("firstname");
//                    String fallowUp = jsonObject.getString("fallowUp");
//                    String address = jsonObject.getString("address");
//                    String last_update = jsonObject.getString("last_update");
                    spnEducation.setSelection(arrayAdapter.getPosition(education));
                    spnJob.setSelection(arrayAdapter1.getPosition(job));
                    spnDistrict.setSelection(arrayAdapter2.getPosition(district));
                    edtBirthDay.setText(birthdate);
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(UserInfo.this);
        requestQueue1.add(request1);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        btnAddDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spnEducation.getSelectedItem().toString().equals("تحصیلات") || spnJob.getSelectedItem().toString().equals("شغل") || spnDistrict.getSelectedItem().toString().equals("محله")) {
                    Toast.makeText(UserInfo.this, "تمامی فیلد ها را انتخاب کنید", Toast.LENGTH_SHORT).show();

                } else if (!spnEducation.getSelectedItem().toString().equals("تحصیلات") && !spnJob.getSelectedItem().toString().equals("شغل") && !spnDistrict.getSelectedItem().toString().equals("محله")) {

                    StringRequest request1 = new StringRequest(Request.Method.PATCH, getString(R.string.url) + "/api/users/" + userId,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(UserInfo.this, "مشخصات ثبت شد", Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(UserInfo.this, error+"", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        Map<String, String> params = new HashMap<String, String>();

                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
//                            params.put("firstname", " ");
//                            params.put("lastname", " ");
//                            params.put("fullname", "");
//                            params.put("phone", "");
                            params.put("education", spnEducation.getSelectedItem().toString());
                            params.put("job", spnJob.getSelectedItem().toString());
//                            params.put("workAddress", "");
                            params.put("birthdate", edtBirthDay.getText().toString());
                            params.put("district", spnDistrict.getSelectedItem().toString());
//                            params.put("fallowUp", "");
//                            params.put("userId", "");
//                            params.put("a", "");
//                            params.put("b", "");
//                            params.put("c", "");
//                            params.put("d", "");
//                            params.put("e", "");
//                            params.put("ranking", "");
//                            params.put("realm", "");
//                            params.put("username", "");
//                            params.put("email", "");
//                            params.put("emailVerified", "");
//                            params.put("id", "");
//                            params.put("last_update", "");
//                            params.put("address", "");
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

                    request1.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    RequestQueue requestQueue1 = Volley.newRequestQueue(UserInfo.this);
                    requestQueue1.add(request1);
                }
            }
        });

        edtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/yekan.ttf");
                PersianCalendar initDate = new PersianCalendar();
                initDate.setPersianDate(1370, 3, 13);

                picker = new PersianDatePickerDialog(UserInfo.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1300)
                        .setMaxYear(1500)
                        .setInitDate(initDate)
                        .setActionTextColor(Color.GRAY)
//                            .setTypeFace(typeface)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                edtBirthDay.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                            }

                            @Override
                            public void onDismissed() {

                            }


                        });

                picker.show();
            }
        });
    }
}
