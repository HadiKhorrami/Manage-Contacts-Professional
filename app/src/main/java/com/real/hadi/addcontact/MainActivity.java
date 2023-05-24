package com.real.hadi.addcontact;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class MainActivity extends AppCompatActivity {
    Button btnAddContact, btnInbox, btnBook, btnContactList, btnHelp, btnExite;
    String userId, fullname, token;
    DrawerLayout drawer;
    NavigationView navigation;
    ImageView imgMenu,imgInstagram,imgTelegram,imgSoroush,imgEitaa;
    TextView txtLogIn;
    public String ranking, a, b, c, d, e;
    private int lastPosition = -1;

    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, LogIn.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInbox = (Button) findViewById(R.id.btnInbox);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnContactList = (Button) findViewById(R.id.btnContactList);
        btnBook = (Button) findViewById(R.id.btnBook);
        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnExite = (Button) findViewById(R.id.btnExite);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        imgInstagram = (ImageView) findViewById(R.id.imgInstagram);
        imgTelegram = (ImageView) findViewById(R.id.imgTelegram);
        imgSoroush = (ImageView) findViewById(R.id.imgSoroush);
        imgEitaa = (ImageView) findViewById(R.id.imgEitaa);
        navigation = (NavigationView) findViewById(R.id.navigation);
        drawer = (DrawerLayout) findViewById(R.id.drawer);

        final Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        fullname = intent.getStringExtra("fullname");
        token = intent.getStringExtra("token");

        View headerview = navigation.getHeaderView(0);
        txtLogIn = (TextView) headerview.findViewById(R.id.txtUserName);
        txtLogIn.setText(fullname + " " + "عزیز خوش آمدید");

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/mirtajaddini.ir"));
                startActivity(browse);
            }
        });
        imgTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("http://telegram.me/mirtajaddini_ir"));
                startActivity(browse);
            }
        });
        imgSoroush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sapp.ir/mirtajaddini_ir"));
                startActivity(browse);
            }
        });
        imgEitaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("http://eitaa.com/mirtajaddini_ir"));
                startActivity(browse);
            }
        });



        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getString(R.string.url) + "/api/messages/new?userid="+userId
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        Drawable top = getResources().getDrawable(R.drawable.ic_message_red_500_36dp);
                        btnInbox.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    }else  if (response.length() == 0){
                        Drawable top = getResources().getDrawable(R.drawable.ic_message_white_36dp);
                        btnInbox.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    }
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("content");
                        JSONArray reads = jsonObject.getJSONArray("reads");
                        JSONArray to = jsonObject.getJSONArray("to");
                        String id = jsonObject.getString("id");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }) {
            Map<String, String> params = new HashMap<String, String>();

            public Map<String, String> getParams() throws AuthFailureError {
//                params.put("userid", userId);
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
        request.setRetryPolicy(new DefaultRetryPolicy(1500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        StringRequest request1 = new StringRequest(Request.Method.GET, getString(R.string.url) +"/api/users/" + userId + "/progress", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = new JSONObject(response);
                        ranking = jsonObject.getString("ranking");
                        a = jsonObject.getString("a");
                        b = jsonObject.getString("b");
                        c = jsonObject.getString("c");
                        d = jsonObject.getString("d");
                        e = jsonObject.getString("e");
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
//                params.put("id", userId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", token);
                return params;
            }
        };
        ;

        request1.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue1 = Volley.newRequestQueue(MainActivity.this);
        requestQueue1.add(request1);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.mnuDetail) {
                    drawer.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, UserInfo.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    startActivity(intent);
                } else if (id == R.id.mnuChangePassword) {
                    drawer.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, ChangePassword.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("token", token);
                    startActivity(intent);
                } else if (id == R.id.mnuKarname) {
                    final PrettyDialog dialog = new PrettyDialog(MainActivity.this);
//            Typeface font = Typeface.createFromAsset( MainActivity.this.getAssets(), "font/iransans.ttf");
                    dialog
                            .setTitle("")
                            .setMessage("رتبه شما: شما با پیشبرد مراح ذیل در رتبه " + ranking + " قرار دارید.\n" +
                                    "ا - انتخاب : " + a + " نفر\n" +
                                    "ر - رایزنی : " + b + " %\n" +
                                    "ت - تداوم و تثبیت نظر : " + c + " %\n" +
                                    "ب - برنامه ریزی هفته و روز انتخابات : " + d + " نفر\n" +
                                    "ا - اقدام و اعلام آرا : " + e + " نفر\n")
                            .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                            .addButton("بستن", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                                @Override
                                public void onClick() {
                                    dialog.dismiss();
                                }
                            });
                    dialog.show();

                }
                return false;
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(navigation);

            }
        });
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                intent.putExtra("userId", userId);
                intent.putExtra("fullname", fullname);
                intent.putExtra("token", token);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        btnContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactList.class);
                intent.putExtra("userId", userId);
                intent.putExtra("fullname", fullname);
                intent.putExtra("token", token);
                intent.putExtra("flag", "other");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        btnExite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(MainActivity.this, LogIn.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("میخواهید خارج شوید؟").setPositiveButton("بله", dialogClickListener)
                        .setNegativeButton("خیر", dialogClickListener).show();

            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Help.class);
                intent.putExtra("userId", userId);
                intent.putExtra("fullname", fullname);
                intent.putExtra("token", token);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Inbox.class);
                intent.putExtra("userId", userId);
                intent.putExtra("fullname", fullname);
                intent.putExtra("token", token);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Book.class);
                intent.putExtra("userId", userId);
                intent.putExtra("fullname", fullname);
                intent.putExtra("token", token);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }

    private void setup_okCancelDialog() {


    }
}
