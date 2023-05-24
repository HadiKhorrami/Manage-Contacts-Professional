package com.real.hadi.addcontact;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SaveMultiContact extends AppCompatActivity {

    RecyclerView recyclerView;
    Cursor cursor1;
    ImageView imgBack;

    ArrayList<AllContactListLayout> sampleList;
    AllContactListLayoutRecyclerAdapter allContactListLayoutRecyclerAdapter;
    Button btnSelectContact, btnAddContact, btnAddTrack, btnContactList, btnExite;
    EditText edtSearch;
    String userId, token, fullname;
    ArrayList<String> name2 = new ArrayList<String>();
    ArrayList<String> phone2 = new ArrayList<String>();
    ArrayList<String> list;
    ArrayList<String> list1;
    ArrayList<String> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_multi_contact);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imgBack = (ImageView) findViewById(R.id.imgBack);

        btnContactList = (Button) findViewById(R.id.btnContactList);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        btnExite = (Button) findViewById(R.id.btnExite);
        sampleList = new ArrayList<>();
        recyclerView.setNestedScrollingEnabled(false);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        fullname = intent.getStringExtra("fullname");
        userId = intent.getStringExtra("userId");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnExite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        btnContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaveMultiContact.this, ContactList.class);
                intent.putExtra("userId", userId);
                intent.putExtra("token", token);
                intent.putExtra("fullname", fullname);
                intent.putExtra("fromAddMulti", "fromAddMulti");
                intent.putExtra("flag", "other");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
//        if (name2.get(i).substring(0,name2.get(i).length()).equals(charSequence)) {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sampleList.clear();
                for (i = 0; i < name2.size(); i++) {

//                          Toast.makeText(SaveMultiContact.this,name2+"",Toast.LENGTH_LONG).show();
                    AllContactListLayout allContactListLayout = new AllContactListLayout();
                    if (phone2.get(i).length() >= 2) {
                        if (phone2.get(i).contains(" ")) {
                            phone2.get(i).replace(" ", "");
                        }
                        if (phone2.get(i).substring(0, 2).equals("09") || phone2.get(i).substring(0, 2).equals("98") || phone2.get(i).substring(0, 2).equals("+9")) {

                            if (name2.get(i).toString().substring(0, name2.get(i).toString().length()).contains(edtSearch.getText().toString())) {

                                allContactListLayout.setName(name2.get(i));
                                allContactListLayout.setPhone(phone2.get(i));
                                sampleList.add(allContactListLayout);
                                allContactListLayoutRecyclerAdapter = new AllContactListLayoutRecyclerAdapter(sampleList);
                                recyclerView.setAdapter(allContactListLayoutRecyclerAdapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(SaveMultiContact.this, 1, LinearLayoutManager.VERTICAL, false));
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getAllContacts(this.getContentResolver());

        for (int i = 0; i < name2.size(); i++) {
            AllContactListLayout allContactListLayout = new AllContactListLayout();

            allContactListLayout.setName(name2.get(i));
            allContactListLayout.setPhone(phone2.get(i));
            sampleList.add(allContactListLayout);
            allContactListLayoutRecyclerAdapter = new AllContactListLayoutRecyclerAdapter(sampleList);
            recyclerView.setAdapter(allContactListLayoutRecyclerAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(SaveMultiContact.this, 1, LinearLayoutManager.VERTICAL, false));
            }
    }

    public void getAllContacts(ContentResolver cr) {
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name2.add(name);
            phone2.add(phone);
        }
        phones.close();
    }
}
