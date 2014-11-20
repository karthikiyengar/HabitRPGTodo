package com.android.habitrpgtodo.database;

/**
 * Created by karthik on 11/16/14.
 */

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import com.android.habitrpgtodo.provider.MyContentProvider;

/**
 * Created by karthik on 11/13/14.
 */
public class DBHandler extends SQLiteOpenHelper {


    private ContentResolver myCR;
    private SQLiteDatabase db;
    private static DBHandler sInstance;
    private static final String DATABASE_NAME = "TestDB";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public static DBHandler getInstance(Context context) {


        if (sInstance == null) {
            sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("APP", "THIS IS CALLED");
        db.execSQL("CREATE TABLE IF NOT EXISTS TEST (_id TEXT PRIMARY KEY, NAME STRING, COMPLETED BOOLEAN)");
        db.execSQL("CREATE TABLE IF NOT EXISTS TEST_COMPLETED (_id TEXT PRIMARY KEY, NAME STRING, COMPLETED BOOLEAN)");
    }


    public void addTask(Record record, int TABLE) {
        ContentValues values = new ContentValues();
        values.put("_id", record.id);
        values.put("NAME", record.name);
        values.put("COMPLETED", record.completed);

        switch (TABLE) {
            case MyContentProvider.TODO_TABLE_VALUE:
                myCR.insert(MyContentProvider.TODO_URI, values);
                break;
            case MyContentProvider.COMPLETED_TABLE_VALUE:
                Log.d("App", "IDHAR!");
                myCR.insert(MyContentProvider.COMPLETED_URI, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown Table");
        }

    }

    public void deleteTask(Record record, int TABLE) {
        Uri uri;
        switch (TABLE) {
            case(MyContentProvider.TODO_TABLE_VALUE):
                uri = Uri.parse(MyContentProvider.TODO_URI + "/" + record.id);
                break;
            case(MyContentProvider.COMPLETED_TABLE_VALUE):
                uri = Uri.parse(MyContentProvider.COMPLETED_URI + "/" + record.id);
                break;
            default:
                throw new IllegalArgumentException("Unknown Table");
        }
        myCR.delete(uri, null, null);
    }


    /* Write Undo and Complete Functions */

    public void completeTask(Record record) {

    }

    public void undoTask (Record record) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void sync() {

    }
}
