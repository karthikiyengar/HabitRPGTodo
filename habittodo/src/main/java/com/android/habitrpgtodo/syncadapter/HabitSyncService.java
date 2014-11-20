package com.android.habitrpgtodo.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by karthik on 11/20/14.
 */
public class HabitSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static HabitSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new HabitSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
