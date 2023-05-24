package com.real.hadi.addcontact;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class PushNotification extends FirebaseMessagingService {
    BroadcastReceiver broadcastReceiver;
public String token1;
    public PushNotification() {
    }

    @Override
    public void onNewToken(final String s) {
        super.onNewToken(s);

    }

    //    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        final String fcmtoken = task.getResult().getToken();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String userId = prefs.getString("userId", "");
                        final String token = prefs.getString("token", "");
                        StringRequest request1 = new StringRequest(Request.Method.POST, getString(R.string.url) + "/api/users/update?where=%7B%22id%22%3A%22" + userId + "%22%7D", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getApplicationContext(), error + "", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            Map<String, String> params = new HashMap<String, String>();

                            @Override
                            public Map<String, String> getParams() throws AuthFailureError {
                                params.put("firebase_token", fcmtoken);
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
                        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                        requestQueue1.add(request1);
//                        Toast.makeText(getApplicationContext(), userId +"****"+token+"****"+fcmtoken+ "", Toast.LENGTH_LONG).show();

                    }

                });



    }



    ;

}


//    startService(new Intent(this, YourService.class));


//}
