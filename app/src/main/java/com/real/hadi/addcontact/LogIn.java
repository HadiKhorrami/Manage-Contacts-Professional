package com.real.hadi.addcontact;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class LogIn extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnLogIn;
    private CheckBox checkbox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    String url;
    private String username, password;
    ProgressBar progressbar;
    TextView txtVersion;
    BroadcastReceiver mSmsBroadcastReceiver;

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        mSmsBroadcastReceiver = new Broadcast();


        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        OneTimeWorkRequest onetimeJob = new OneTimeWorkRequest.Builder(BgWorker.class)
                .setConstraints(constraints).build(); // or PeriodicWorkRequest
        WorkManager.getInstance(getApplicationContext()).enqueue(onetimeJob);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        checkbox.setTypeface(ResourcesCompat.getFont(LogIn.this, R.font.yekan));
        url = getString(R.string.url) + "/api/users/login";
        Toast.makeText(LogIn.this, "لطفا کیبورد را به حالت انگلیسی قرار بدهید", Toast.LENGTH_SHORT).show();

//        if (ActivityCompat.shouldShowRequestPermissionRationale(LogIn.this,
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                1);

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            edtEmail.setText(loginPreferences.getString("username", ""));
            edtPassword.setText(loginPreferences.getString("password", ""));
            checkbox.setChecked(true);
        }
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressbar.setVisibility(View.VISIBLE);
//
//                android.net.ConnectivityManager cm = (ConnectivityManager) getSystemService(LogIn.this.CONNECTIVITY_SERVICE);
//
//                android.net.NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
//                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
//                    if (edtEmail.getText().toString().equals("") && edtPassword.getText().toString().equals("")) {
//                        Toast.makeText(LogIn.this, "لطفا فیلدهای مربوط را پر کنید", Toast.LENGTH_SHORT).show();
//                    } else if (!edtEmail.getText().toString().equals(null) && !edtPassword.getText().toString().equals(null)) {
//                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
////                                Animation fadein = AnimationUtils.loadAnimation(LogIn.this, R.anim.fade_in);
////                                imgCheck.startAnimation(fadein);
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    String id = jsonObject.getString("id");
//                                    String token = jsonObject.getString("token");
//                                    JSONObject data = jsonObject.getJSONObject("data");
//                                    String firstname = null;
//                                    String lastname = null;
////                                        for (int j = 0; j < data.length(); j++) {
////                                            JSONObject jsonObject1 = data.getJSONObject(0);
//                                    firstname = data.getString("firstname");
//                                    lastname = data.getString("lastname");
//                                    InputMethodManager imm = (InputMethodManager) getSystemService(LogIn.this.INPUT_METHOD_SERVICE);
//                                    imm.hideSoftInputFromWindow(edtEmail.getWindowToken(), 0);
//
////                                        }
//
//                                    username = edtEmail.getText().toString();
//                                    password = edtPassword.getText().toString();
//
//                                    if (checkbox.isChecked()) {
//                                        loginPrefsEditor.putBoolean("saveLogin", true);
//                                        loginPrefsEditor.putString("userId", id);
//                                        loginPrefsEditor.putString("username", username);
//                                        loginPrefsEditor.putString("password", password);
//                                        loginPrefsEditor.commit();
//                                    } else {
//                                        loginPrefsEditor.clear();
//                                        loginPrefsEditor.commit();
//                                    }
////                                        doSomethingElse();
//
//
                                    Intent intent = new Intent(LogIn.this, MainActivity.class);
//                                    intent.putExtra("userId", id);
//                                    intent.putExtra("fullname", firstname + " " + lastname);
//                                    intent.putExtra("token", token);
//                                    intent.putExtra("fromLogIn", "fromLogIn");
                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LogIn.this);
//                                    SharedPreferences.Editor loginPrefsEditor = prefs.edit();
//                                    loginPrefsEditor.putString("userId", id);
//                                    loginPrefsEditor.putString("token", token);
//                                    loginPrefsEditor.commit();
//                                    edtEmail.setText(null);
//                                    edtPassword.setText(null);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(LogIn.this, "خطا در ورود. دوباره سعی کنید", Toast.LENGTH_SHORT).show();
//
//                                // do something
//                            }
//                        }) {
//                            Map<String, String> params = new HashMap<String, String>();
//
//                            @Override
//                            public Map<String, String> getParams() throws AuthFailureError {
//                                params.put("username", edtEmail.getText().toString());
//                                params.put("password", edtPassword.getText().toString());
//                                return params;
//                            }
//                        };
//                        ;
//
//                        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                        RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);
//                        requestQueue.add(request);
//                    }
//                } else if (activeNetworkInfo == null) {
//                    Toast.makeText(LogIn.this, "دستگاه شما به اینترنت متصل نیست", Toast.LENGTH_SHORT).show();
//
//                }
//
//
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        String version = txtVersion.getText().toString().replace("نسخه ", "");
        StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.url) + "/check_version/" + version, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Integer result = jsonObject.getInt("result");

                    if (result == 0) {

                    } else if (result == 1) {
                        String new_version = jsonObject.getString("new_version");
                        final String link = jsonObject.getString("link");
                        String message = jsonObject.getString("message");
                        SpannableString ss = new SpannableString(link);

                        ClickableSpan span1 = new ClickableSpan() {
                            @Override
                            public void onClick(View textView) {
                                // do some thing
                            }
                        };
                        ss.setSpan(span1, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        final PrettyDialog dialog = new PrettyDialog(LogIn.this);
//            Typeface font = Typeface.createFromAsset( MainActivity.this.getAssets(), "font/iransans.ttf");
                        dialog
                                .setTitle("")
                                .setMessage("نسخه ی " + new_version + " موجود است. لطفا از لینک زیر دریافت نمایید.\n"
                                )
                                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                                .addButton("لینک دانلود", R.color.pdlg_color_white, R.color.pdlg_color_blue, new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        dialog.dismiss();
                                        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                        startActivity(browse);
                                    }
                                });
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LogIn.this, error + "", Toast.LENGTH_LONG).show();

            }
        }) {
            Map<String, String> params = new HashMap<String, String>();

            public Map<String, String> getParams() throws AuthFailureError {
//                params.put("userId", userId);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);
        requestQueue.add(request);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(mSmsBroadcastReceiver, filter);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(mSmsBroadcastReceiver, filter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(mSmsBroadcastReceiver, filter);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(mSmsBroadcastReceiver, filter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(mSmsBroadcastReceiver, filter);
//    }
}
