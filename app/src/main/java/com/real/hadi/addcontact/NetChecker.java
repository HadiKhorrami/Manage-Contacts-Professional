package com.real.hadi.addcontact;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.net.InetAddress;


public class NetChecker extends IntentService {
    public static String TAG = "NetChecker";
    public NetChecker() {
        super("NetChecker");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Started");
        run();
    }
    private void run() {
        try {
            while(true)
            {
                //Some expensive Internet & SQL querying stuff
                Thread.sleep(10000);
                Log.i(TAG, "Service Running...");
                if (isInternetAvailable()) {
                    Log.i(TAG, "We are online");
                    getApplicationContext().startService(new Intent(getApplicationContext(), PushNotification.class));
                }
                else
                    Log.i(TAG, "No Internet :(");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
