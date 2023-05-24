package com.real.hadi.addcontact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;

public class Broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
//         an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        Constraints mConstraints =
                new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest mRequest =
                new OneTimeWorkRequest.Builder(BgWorker.class).setConstraints(mConstraints)
                        .build();
    }
}