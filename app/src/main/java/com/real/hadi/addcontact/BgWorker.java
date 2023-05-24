package com.real.hadi.addcontact;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BgWorker extends Worker {

    private Context context;

    public BgWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @Override
    public Result doWork() {
        try {
            this.context.startService(new Intent(this.context, NetChecker.class));
            Log.i("WORKER", "Service called to run");
        } catch (Exception e) {
            Log.d("WORKER", e.getLocalizedMessage());
        }
        // Indicate whether the task finished successfully with the Result
        return Result.success();
    }
}