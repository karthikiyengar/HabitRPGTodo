package com.android.habitrpgtodo.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import com.android.habitrpgtodo.provider.MyContentProvider;

/**
 * Created by karthik on 11/20/14.
 */
public class HabitSyncAdapter extends AbstractThreadedSyncAdapter {
    private final AccountManager mAccountManager;

    public HabitSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {

            Log.d("App", "Performing Sync");

            // TODO Get the auth token for the current account


            // Get shows from the remote server

            Cursor cursorRemote = provider.query(MyContentProvider.TODO_REMOTE_URI, null,null,null,null);
            while (cursorRemote.moveToNext()) {
                Log.d("Add", "CursorRem: " + cursorRemote.getString(2));
            }

            //List remoteTvShows = parseComService.getShows(authToken);

            // Get shows from the local storage


            Cursor cursorLocal = provider.query(MyContentProvider.TODO_URI, null,null,null,null);
            while (cursorLocal.moveToNext()) {
                Log.d("Add", "CursorLocal: " + cursorLocal.getString(1));
            }

            Log.d("App", "This is the next statement");

            /*ArrayList localTvShows = new ArrayList();
            Cursor curTvShows = provider.query(TvShowsContract.CONTENT_URI, null, null, null, null);
            if (curTvShows != null) {
                while (curTvShows.moveToNext()) {
                    localTvShows.add(TvShow.fromCursor(curTvShows));
                }
                curTvShows.close();
            }*/

            // TODO See what Local shows are missing on Remote

            // TODO See what Remote shows are missing on Local

            // TODO Updating remote tv shows

            // TODO Updating local tv shows

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}