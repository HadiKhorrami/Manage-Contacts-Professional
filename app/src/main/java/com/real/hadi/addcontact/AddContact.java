package com.real.hadi.addcontact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.content.ContentValues.TAG;

public class AddContact extends AppCompatActivity {
    EditText edtBirthDay, edtName, edtFamily, edtPhone, edtTrackDate, edtTrackDesc, edtTrackNumber;
    AppCompatSpinner spnJob, spnEducation, spnDistrict;
    Spinner spnA, spnB, spnC, spnD, spnE;
    PersianDatePickerDialog picker;
    Button btnSelectContact, btnAddContact, btnAddTrack, btnContactList, btnAddMultiContact, btnExite;
    ImageView imgBack;
    TextView txtTitle;
    private static final int REQUEST_CODE = 1;
    static final int PICK_CONTACT = 1;
    LinearLayout constTrack;
    LinearLayout linear;
    String url;
    JSONArray jsonArray;
    List<EditText> list;
    List<EditText> list1;
    ArrayList<String> dateArr;
    ArrayList<String> descArr;
    String id, firstname, lastname, phone, education, job, birthday, district, trackdate, description, userId, fallowUplength, flag;
    Integer size, field2, field3, field4, field5;
    String message = null, fullname, token;
    Boolean NumberExist = false;
    ArrayList<String> jobList, educationList, districtList, spnAList, spnBList, spnCList, spnDList, spnEList;

    public void onBackPressed() {
        super.onBackPressed();

    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        spnDistrict = (AppCompatSpinner) findViewById(R.id.spnDistrict);
        spnEducation = (AppCompatSpinner) findViewById(R.id.spnEducation);
        spnJob = (AppCompatSpinner) findViewById(R.id.spnJob);
        spnA = (Spinner) findViewById(R.id.spnA);
        spnB = (Spinner) findViewById(R.id.spnB);
        spnC = (Spinner) findViewById(R.id.spnC);
        spnD = (Spinner) findViewById(R.id.spnD);
        spnE = (Spinner) findViewById(R.id.spnE);
        edtBirthDay = (EditText) findViewById(R.id.edtBirthDay);
        edtName = (EditText) findViewById(R.id.edtName);
        edtFamily = (EditText) findViewById(R.id.edtFamily);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnContactList = (Button) findViewById(R.id.btnContactList);
        btnAddTrack = (Button) findViewById(R.id.btnAddTrack);
        btnAddMultiContact = (Button) findViewById(R.id.btnAddMultiContact);
        edtTrackNumber = (EditText) findViewById(R.id.edtTrackNumber);
        btnExite = (Button) findViewById(R.id.btnExite);
        linear = (LinearLayout) findViewById(R.id.linear);
        txtTitle = (TextView) findViewById(R.id.txtTitle);

        final Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        token = intent.getStringExtra("token");
        fullname = intent.getStringExtra("fullname");
        userId = intent.getStringExtra("userId");
        id = intent.getStringExtra("id");
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        phone = intent.getStringExtra("phone");
        education = intent.getStringExtra("education");
        job = intent.getStringExtra("job");
        birthday = intent.getStringExtra("birthday");
        district = intent.getStringExtra("district");
        field2 = intent.getIntExtra("field2", 0);
        field3 = intent.getIntExtra("field3", 0);
        field4 = intent.getIntExtra("field4", 0);
        field5 = intent.getIntExtra("field5", 0);

        educationList = new ArrayList<>();
        districtList = new ArrayList<>();
        jobList = new ArrayList<>();
        spnAList = new ArrayList<>();
        spnBList = new ArrayList<>();
        spnCList = new ArrayList<>();
        spnDList = new ArrayList<>();
        spnEList = new ArrayList<>();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        edtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/yekan.ttf");
                PersianCalendar initDate = new PersianCalendar();
                initDate.setPersianDate(1370, 3, 13);

                picker = new PersianDatePickerDialog(AddContact.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1330)
                        .setMaxYear(1405)
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
        spnA.setBackgroundResource(R.drawable.greenbutton);


        spnAList.add("ا");
        ArrayAdapter<String> arrayAdapterA = new ArrayAdapter<String>(this, R.layout.spinnerabc, spnAList);
        arrayAdapterA.setDropDownViewResource(R.layout.spinner);
        spnA.setAdapter(arrayAdapterA);

        spnBList.add("ر");
        spnBList.add("سطح 0");
        spnBList.add("سطح 1");
        spnBList.add("سطح 2");
        ArrayAdapter<String> arrayAdapterB = new ArrayAdapter<String>(this, R.layout.spinnerabc, spnBList);
        arrayAdapterB.setDropDownViewResource(R.layout.spinner);
        spnB.setAdapter(arrayAdapterB);
        spnB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1:
                        spnB.setBackgroundResource(R.drawable.redbutton);
                        break;
                    case 2:
                        spnB.setBackgroundResource(R.drawable.yellowbutton);
                        break;
                    case 3:
                        spnB.setBackgroundResource(R.drawable.greenbutton);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnCList.add("ت");
        spnCList.add("سطح 0");
        spnCList.add("سطح 1");
        spnCList.add("سطح 2");
        ArrayAdapter<String> arrayAdapterC = new ArrayAdapter<String>(this, R.layout.spinnerabc, spnCList);
        arrayAdapterC.setDropDownViewResource(R.layout.spinner);
        spnC.setAdapter(arrayAdapterC);
        spnC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1:
                        spnC.setBackgroundResource(R.drawable.redbutton);
                        break;
                    case 2:
                        spnC.setBackgroundResource(R.drawable.yellowbutton);
                        break;
                    case 3:
                        spnC.setBackgroundResource(R.drawable.greenbutton);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnDList.add("ب");
        spnDList.add("خیر");
        spnDList.add("بله");
        ArrayAdapter<String> arrayAdapterD = new ArrayAdapter<String>(this, R.layout.spinnerabc, spnDList);
        arrayAdapterD.setDropDownViewResource(R.layout.spinner);
        spnD.setAdapter(arrayAdapterD);
        spnD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1:
                        spnD.setBackgroundResource(R.drawable.redbutton);
                        break;
                    case 2:
                        spnD.setBackgroundResource(R.drawable.greenbutton);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnEList.add("ا");
        spnEList.add("خیر");
        spnEList.add("بله");
        ArrayAdapter<String> arrayAdapterE = new ArrayAdapter<String>(this, R.layout.spinnerabc, spnEList);
        arrayAdapterE.setDropDownViewResource(R.layout.spinner);
        spnE.setAdapter(arrayAdapterE);
        spnE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1:
                        spnE.setBackgroundResource(R.drawable.redbutton);
                        break;
                    case 2:
                        spnE.setBackgroundResource(R.drawable.greenbutton);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner, educationList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner);
        spnEducation.setAdapter(arrayAdapter);


        jobList.add("شغل");
        jobList.add("آزاد");
        jobList.add("دولتی");
        jobList.add("بیکار");
        jobList.add("دانشجو");
        jobList.add("خصوصی");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner, jobList);
        arrayAdapter1.setDropDownViewResource(R.layout.spinner);
        spnJob.setAdapter(arrayAdapter1);

        districtList.add("محله");
        districtList.add("آبرسان");
        districtList.add("آخماقیه");
        districtList.add("آخونی");
        districtList.add("ابوریحان");
        districtList.add("احمد آباد");
        districtList.add("اسلام آباد ");
        districtList.add("اسماعیل بقال");
        districtList.add("امامیه");
        districtList.add("امیرخیز");
        districtList.add("انگج");
        districtList.add("اهراب");
        districtList.add("ایل گلی");
        districtList.add("بارنج");
        districtList.add("بازار");
        districtList.add("باغ شمال");
        districtList.add("باغمیشه");
        districtList.add("بهار");
        districtList.add("بیلانکوه");
        districtList.add("پارک ");
        districtList.add("پل سنگی ");
        districtList.add("تپلی باغ ");
        districtList.add("جمشیدآباد ");
        districtList.add("چرنداب ");
        districtList.add("چوست دوزان ");
        districtList.add("چهارمنار ");
        districtList.add("حکم آباد  ");
        districtList.add("حیدر آباد  ");
        districtList.add("خطیب  ");
        districtList.add("خلعت پوشان ");
        districtList.add("خلیل آباد ");
        districtList.add("خیابان  ");
        districtList.add("خیام  ");
        districtList.add("داداش آباد  ");
        districtList.add("داش ساختمان  ");
        districtList.add("راسته کوچه  ");
        districtList.add("رضوان شهر  ");
        districtList.add("رواسان ");
        districtList.add("زنگوله باغ  ");
        districtList.add("ساری زمین ");
        districtList.add("سرخاب   ");
        districtList.add("سنجران ");
        districtList.add("سیلاب  ");
        districtList.add("شاه آباد ");
        districtList.add("شتربان ");
        districtList.add("شربت زاده ");
        districtList.add("ششگلان ");
        districtList.add("شنب غازان ");
        districtList.add("صالح آباد ");
        districtList.add("طالقانی  ");
        districtList.add("عباسی ");
        districtList.add("عموزین الدین ");
        districtList.add("قراملک  ");
        districtList.add("قره آغاج ");
        districtList.add("قورخانه ");
        districtList.add("کوجوار ");
        districtList.add("کوچه باغ ");
        districtList.add("گرو  ");
        districtList.add("گجیل   ");
        districtList.add("لاله   ");
        districtList.add("لیل آباد   ");
        districtList.add("مارالان   ");
        districtList.add("محمدآباد   ");
        districtList.add("مصلی   ");
        districtList.add("مقصودیه   ");
        districtList.add("ملازینال   ");
        districtList.add("منبع    ");
        districtList.add("منجم    ");
        districtList.add("منظریه    ");
        districtList.add("مهادمهین    ");
        districtList.add("نصف راه    ");
        districtList.add("نوبر    ");
        districtList.add("ولیعصر   ");
        districtList.add("ویجویه    ");
        districtList.add("ویلاشهر    ");
        districtList.add("یانیق    ");
        districtList.add("یکه دکان   ");
        districtList.add("یوسف آباد   ");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner, districtList);
        arrayAdapter2.setDropDownViewResource(R.layout.spinner);
        spnDistrict.setAdapter(arrayAdapter2);

        jsonArray = new JSONArray();
        list = new ArrayList<EditText>();
        list1 = new ArrayList<EditText>();



        btnAddMultiContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = AddContact.this.getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Id", userId); // value to store
                editor.commit();
                Intent intent = new Intent(AddContact.this, SaveMultiContact.class);
                intent.putExtra("userId", userId);
                intent.putExtra("fullname", fullname);
                intent.putExtra("token", token);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        String fromList = intent.getStringExtra("fromList");


        if (fromList != null) {

            btnAddMultiContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences pref = AddContact.this.getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Id", userId); // value to store
                    editor.commit();
                    Intent intent = new Intent(AddContact.this, SaveMultiContact.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            fallowUplength = intent.getStringExtra("fallowUplength");
            size = Integer.parseInt(fallowUplength);
            dateArr = new ArrayList<>(Integer.parseInt(fallowUplength));
            descArr = new ArrayList<>(Integer.parseInt(fallowUplength));
            dateArr = intent.getStringArrayListExtra("trackDate");
            descArr = intent.getStringArrayListExtra("description");
            edtName.setText(firstname);
            edtFamily.setText(lastname);

            if (field2 == 0) {
                int spinnerPosition = arrayAdapterB.getPosition("سطح 0");
                spnB.setSelection(spinnerPosition);
            } else if (field2 == 1) {
                int spinnerPosition = arrayAdapterB.getPosition("سطح 1");
                spnB.setSelection(spinnerPosition);
            } else if (field2 == 2) {
                int spinnerPosition = arrayAdapterB.getPosition("سطح 2");
                spnB.setSelection(spinnerPosition);
            }
            //////////////////////////////////////////////////////////////////////////////
            if (field3 == 0) {
                int spinnerPosition = arrayAdapterC.getPosition("سطح 0");
                spnC.setSelection(spinnerPosition);
            } else if (field3 == 1) {
                int spinnerPosition = arrayAdapterC.getPosition("سطح 1");
                spnC.setSelection(spinnerPosition);
            } else if (field3 == 2) {
                int spinnerPosition = arrayAdapterC.getPosition("سطح 2");
                spnC.setSelection(spinnerPosition);
            }
            //////////////////////////////////////////////////////////////////////////////
            if (field4 == 0) {
                int spinnerPosition = arrayAdapterD.getPosition("خیر");
                spnD.setSelection(spinnerPosition);
            } else if (field4 == 1) {
                int spinnerPosition = arrayAdapterD.getPosition("بله");
                spnD.setSelection(spinnerPosition);
            }
            //////////////////////////////////////////////////////////////////////////////
            if (field5 == 0) {
                int spinnerPosition = arrayAdapterE.getPosition("خیر");
                spnE.setSelection(spinnerPosition);
            } else if (field5 == 1) {
                int spinnerPosition = arrayAdapterE.getPosition("بله");
                spnE.setSelection(spinnerPosition);
            }

            //////////////////////////////////////////////////////////////////////////////
            if (education != null) {
                int spinnerPosition = arrayAdapter.getPosition(education);
                spnEducation.setSelection(spinnerPosition);
            }

            if (education != null) {
                int spinnerPosition = arrayAdapter.getPosition(education);
                spnEducation.setSelection(spinnerPosition);
            }
            if (job != null) {
                int spinnerPosition = arrayAdapter1.getPosition(job);
                spnJob.setSelection(spinnerPosition);
            }
            if (district != null) {
                int spinnerPosition = arrayAdapter2.getPosition(district);
                spnDistrict.setSelection(spinnerPosition);
            }
            edtPhone.setText(phone);
            edtBirthDay.setText(birthday);
//            btnAddContact.setText("ویرایش");
            txtTitle.setText("ویرایش مخاطب");
            edtTrackNumber.setFocusable(false);
            btnAddContact.setText("ویرایش مخاطب");

            btnContactList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AddContact.this, ContactList.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    intent.putExtra("fromMain", "fromMain");
                    intent.putExtra("flag", "other");
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            });


            for (int i = 0; i < size + 5; i++) {
                final int finalI = i;

                LayoutInflater inflater = LayoutInflater.from(AddContact.this);

                View view1 = inflater.inflate(R.layout.tracklayout, null);
                edtTrackDate = (EditText) view1.findViewById(R.id.edtTrackDate);
                edtTrackDesc = (EditText) view1.findViewById(R.id.edtTrackDesc);
                constTrack = (LinearLayout) view1.findViewById(R.id.constTrack);


                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) edtTrackDate.getLayoutParams();
                lp.setMargins(0, 20, 0, 20);
                edtTrackDate.setLayoutParams(lp);
                edtTrackDate.setId(i);
                edtTrackDesc.setId(i);


                edtTrackDate.setHint("تاریخ" + i);
                edtTrackDesc.setHint("توضیحات" + i);
                constTrack.removeView(edtTrackDate);
                constTrack.removeView(edtTrackDesc);
                list.add(edtTrackDate);
                list1.add(edtTrackDesc);
                linear.addView(list.get(i));
                linear.addView(list1.get(i));
                if (i < size) {
                    list.get(i).setText(dateArr.get(i));
                    list1.get(i).setText(descArr.get(i));
                }

                list1.get(i).setSingleLine(false);
                list1.get(i).setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                list.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //   Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/yekan.ttf");
                        PersianCalendar initDate = new PersianCalendar();
                        initDate.setPersianDate(1370, 3, 13);

                        picker = new PersianDatePickerDialog(AddContact.this)
                                .setPositiveButtonString("انتخاب")
                                .setNegativeButton("لغو")
                                .setTodayButton("امروز")
                                .setTodayButtonVisible(true)
                                .setMinYear(1300)
                                .setMaxYear(1405)
                                .setInitDate(initDate)
                                .setActionTextColor(Color.GRAY)
//                            .setTypeFace(typeface)
                                .setListener(new Listener() {
                                    @Override
                                    public void onDateSelected(PersianCalendar persianCalendar) {
                                        list.get(finalI).setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());

                                    }

                                    @Override
                                    public void onDismissed() {

                                    }

                                });
                        picker.show();

                    }

                });
            }


            btnAddContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    jsonArray = new JSONArray(new ArrayList<String>());
                    try {
                        for (int i = 0; i < list.size(); i++) {
                            if (!list.get(i).getText().toString().equals("") && !list1.get(i).getText().toString().equals("")) {

                                JSONObject json1 = new JSONObject();
                                json1.put("trackDate", list.get(i).getText().toString());
                                json1.put("description", list1.get(i).getText().toString());
                                jsonArray.put(json1);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    StringRequest request1 = new StringRequest(Request.Method.PATCH, getString(R.string.url) + "/api/people/" + id, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            edtName.setText(null);
                            edtFamily.setText(null);
                            edtPhone.setText(null);
                            edtBirthDay.setText(null);
                            edtTrackNumber.setText(null);
                            linear.removeAllViews();
                            btnAddContact.setText("اضافه");
                            Intent intent1 = new Intent(AddContact.this, ContactList.class);
                            intent1.putExtra("userId", userId);
                            intent1.putExtra("fullname", fullname);
                            intent1.putExtra("token", token);
                            intent1.putExtra("flag", "other");
                            startActivity(intent1);
                            Toast.makeText(AddContact.this, "مخاطب با موفقیت ویرایش شد", Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddContact.this, "خطا در عملیات", Toast.LENGTH_SHORT).show();

                            // do something
                        }
                    }) {
                        Map<String, String> params = new HashMap<String, String>();

                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            params.put("firstname", edtName.getText().toString());
                            params.put("lastname", edtFamily.getText().toString());
                            params.put("phone", edtPhone.getText().toString());
                            params.put("education", spnEducation.getSelectedItem().toString());
                            params.put("job", spnJob.getSelectedItem().toString());
                            params.put("workAddress", "");
                            params.put("birthdate", edtBirthDay.getText().toString());
                            params.put("district", spnDistrict.getSelectedItem().toString());
                            params.put("fallowUp", jsonArray.toString());
                            params.put("userId", userId);
                            params.put("id", id);
                            if (spnB.getSelectedItem().toString().equals("سطح 0")) {
                                params.put("field2", "0");
                            } else if (spnB.getSelectedItem().toString().equals("سطح 1")) {
                                params.put("field2", "1");
                            } else if (spnB.getSelectedItem().toString().equals("سطح 2")) {
                                params.put("field2", "2");
                            }
                            //////////////////////////////////////////////////////////////
                            if (spnC.getSelectedItem().toString().equals("سطح 0")) {
                                params.put("field3", "0");
                            } else if (spnC.getSelectedItem().toString().equals("سطح 1")) {
                                params.put("field3", "1");
                                params.put("field3", "1");
                            } else if (spnC.getSelectedItem().toString().equals("سطح 2")) {
                                params.put("field3", "2");
                            }
                            /////////////////////////////////////////////////////////////////
                            if (spnD.getSelectedItem().toString().equals("خیر")) {
                                params.put("field4", "0");
                            } else if (spnD.getSelectedItem().toString().equals("بله")) {
                                params.put("field4", "1");
                            }
                            /////////////////////////////////////////////////////////////////
                            if (spnE.getSelectedItem().toString().equals("خیر")) {
                                params.put("field5", "0");
                            } else if (spnE.getSelectedItem().toString().equals("بله")) {
                                params.put("field5", "1");
                            }
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
                    RequestQueue requestQueue1 = Volley.newRequestQueue(AddContact.this);
                    requestQueue1.add(request1);

                }

            });
            btnExite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(AddContact.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        } else if (message == null) {

            btnAddTrack.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
//                EditText[] editTexts = new EditText[3];
//                EditText[] editTexts1 = new EditText[3];
                    if (edtTrackNumber.getText().toString().equals("")) {
                        Toast.makeText(AddContact.this, "لطفا تعداد فیلد پیگیری را وارد نمایید", Toast.LENGTH_SHORT).show();

                    } else if (!edtTrackNumber.getText().toString().equals("")) {
                        for (int i = 0; i < Integer.parseInt(edtTrackNumber.getText().toString()); i++) {
                            final int finalI = i;

                            LayoutInflater inflater = LayoutInflater.from(AddContact.this);

                            View view1 = inflater.inflate(R.layout.tracklayout, null);
                            edtTrackDate = (EditText) view1.findViewById(R.id.edtTrackDate);
                            edtTrackDesc = (EditText) view1.findViewById(R.id.edtTrackDesc);
                            constTrack = (LinearLayout) view1.findViewById(R.id.constTrack);


                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) edtTrackDate.getLayoutParams();
                            lp.setMargins(0, 20, 0, 20);
                            edtTrackDate.setLayoutParams(lp);
                            edtTrackDate.setId(i);
                            edtTrackDesc.setId(i);

                            edtTrackDate.setHint("تاریخ" + i);
                            edtTrackDesc.setHint("توضیحات" + i);
                            constTrack.removeView(edtTrackDate);
                            constTrack.removeView(edtTrackDesc);
                            linear.addView(edtTrackDate);
                            linear.addView(edtTrackDesc);
                            list.add(edtTrackDate);
                            list1.add(edtTrackDesc);
                            list1.get(i).setSingleLine(false);
                            list1.get(i).setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                            list.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //   Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/yekan.ttf");
                                    PersianCalendar initDate = new PersianCalendar();
                                    initDate.setPersianDate(1370, 3, 13);

                                    picker = new PersianDatePickerDialog(AddContact.this)
                                            .setPositiveButtonString("انتخاب")
                                            .setNegativeButton("لغو")
                                            .setTodayButton("امروز")
                                            .setTodayButtonVisible(true)
                                            .setMinYear(1300)
                                            .setMaxYear(1405)
                                            .setInitDate(initDate)
                                            .setActionTextColor(Color.GRAY)
//                            .setTypeFace(typeface)
                                            .setListener(new Listener() {
                                                @Override
                                                public void onDateSelected(PersianCalendar persianCalendar) {
                                                    list.get(finalI).setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());

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
                }
            });
            btnContactList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AddContact.this, ContactList.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    intent.putExtra("flag", "other");
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            });

            btnAddContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (edtName.getText().toString().equals("") || edtFamily.getText().toString().equals("") || edtPhone.getText().toString().equals("")) {
                        Toast.makeText(AddContact.this, "پر کردن سه مورد اول الزامی است", Toast.LENGTH_SHORT).show();
                    } else if (!edtName.getText().toString().equals("") && !edtFamily.getText().toString().equals("") && !edtPhone.getText().toString().equals("")) {


                        StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.url) + "/api/people?filter=%7B%22where%22%3A%7B%22phone%22%3A%22" + edtPhone.getText().toString() + "%22%7D%7D", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String phone = jsonObject.getString("phone");
                                        if (phone.equals(edtPhone.getText().toString())) {
                                            NumberExist = true;
                                            Toast.makeText(AddContact.this, "شماره قبلا ثبت شده است", Toast.LENGTH_SHORT).show();
                                        } else {
                                            NumberExist = false;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                            Map<String, String> params = new HashMap<String, String>();

                            @Override
                            public Map<String, String> getParams() throws AuthFailureError {

                                return params;
                            }
                        };
                        ;

                        request.setRetryPolicy(new DefaultRetryPolicy(500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        RequestQueue requestQueue = Volley.newRequestQueue(AddContact.this);
                        requestQueue.add(request);
                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        if (NumberExist == false) {

                            jsonArray = new JSONArray(new ArrayList<String>());
                            try {
                                for (int i = 0; i < list.size(); i++) {
                                    JSONObject json1 = new JSONObject();
                                    json1.put("trackDate", list.get(i).getText().toString());
                                    json1.put("description", list1.get(i).getText().toString());
                                    jsonArray.put(json1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            StringRequest request1 = new StringRequest(Request.Method.POST, getString(R.string.url) +"/api/people", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    edtName.setText(null);
                                    edtFamily.setText(null);
                                    edtPhone.setText(null);
                                    edtBirthDay.setText(null);
                                    edtTrackNumber.setText(null);
                                    linear.removeAllViews();
                                    Toast.makeText(AddContact.this, "مخاطب با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(AddContact.this, error + "", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                Map<String, String> params = new HashMap<String, String>();

                                @Override
                                public Map<String, String> getParams() throws AuthFailureError {
                                    params.put("firstname", edtName.getText().toString());
                                    params.put("lastname", edtFamily.getText().toString());
                                    params.put("phone", edtPhone.getText().toString());
                                    params.put("education", spnEducation.getSelectedItem().toString());
                                    params.put("job", spnJob.getSelectedItem().toString());
                                    params.put("workAddress", "");
                                    params.put("birthdate", edtBirthDay.getText().toString());
                                    params.put("district", spnDistrict.getSelectedItem().toString());
                                    params.put("fallowUp", jsonArray.toString());
                                    params.put("userId", userId);
                                    if (spnB.getSelectedItem().toString().equals("سطح 0")) {
                                        params.put("field2", "0");
                                    } else if (spnB.getSelectedItem().toString().equals("سطح 1")) {
                                        params.put("field2", "1");
                                    } else if (spnB.getSelectedItem().toString().equals("سطح 2")) {
                                        params.put("field2", "2");
                                    }
                                    //////////////////////////////////////////////////////////////
                                    if (spnC.getSelectedItem().toString().equals("سطح 0")) {
                                        params.put("field3", "0");
                                    } else if (spnC.getSelectedItem().toString().equals("سطح 1")) {
                                        params.put("field3", "1");
                                    } else if (spnC.getSelectedItem().toString().equals("سطح 2")) {
                                        params.put("field3", "2");
                                    }
                                    /////////////////////////////////////////////////////////////////
                                    if (spnD.getSelectedItem().toString().equals("خیر")) {
                                        params.put("field4", "0");
                                    } else if (spnD.getSelectedItem().toString().equals("بله")) {
                                        params.put("field4", "1");
                                    }
                                    /////////////////////////////////////////////////////////////////
                                    if (spnE.getSelectedItem().toString().equals("خیر")) {
                                        params.put("field5", "0");
                                    } else if (spnE.getSelectedItem().toString().equals("بله")) {
                                        params.put("field5", "1");
                                    }

                                    return params;
                                }
                            };
                            ;

                            request1.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            RequestQueue requestQueue1 = Volley.newRequestQueue(AddContact.this);
                            requestQueue1.add(request1);
                        } else if (NumberExist == true) {
                        }
                    }
                }
            });
            btnExite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddContact.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = intent.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = AddContact.this.getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);
                edtPhone.setText(number);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);
                String[] name1 = name.split(" ");
                if (name1.length == 1) {
                    edtName.setText(name);
                } else {
                    if (name1.length == 2) {
                        edtName.setText(name1[0]);
                        edtFamily.setText(name1[1]);
                    } else if (name1.length == 3) {
                        edtName.setText(name1[0]);
                        edtFamily.setText(name1[1] + " " + name1[2]);
                    } else {
                        if (name1.length == 4) {
                            edtName.setText(name1[0] + " " + name1[1]);
                            edtFamily.setText(name1[2] + " " + name1[3]);
                        }

                        Log.d(TAG, "ZZZ number : " + number + " , name : " + name);

                    }
                }
            }
        }
    }

    ;
}

