package com.android.habitrpgtodo.provider;

/**
 * Created by karthik on 11/16/14.
 */


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.android.habitrpgtodo.database.DBHandler;


/**
 * Created by karthik on 11/14/14.
 */

public class MyContentProvider extends ContentProvider {

    private static final DBHandler myDB = null;

    private static final String AUTHORITY = "com.android.habitrpgtodo.provider.MyContentProvider";


    private static final String TODO_TABLE = "Test";
    private static final String COMPLETED_TABLE = "Test_Completed";


    public static final Uri TODO_URI = Uri.parse("content://" + AUTHORITY + "/" + TODO_TABLE);
    public static final Uri COMPLETED_URI = Uri.parse("content://" + AUTHORITY + "/" + COMPLETED_TABLE);


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int TODO_TABLE_VALUE = 1;
    public static final int COMPLETED_TABLE_VALUE = 2;
    public static final int TODO_TABLE_ROW_VALUE = 3;
    public static final int COMPLETED_TABLE_ROW_VALUE = 4;

    static {
        sUriMatcher.addURI(AUTHORITY, TODO_TABLE, TODO_TABLE_VALUE);
        sUriMatcher.addURI(AUTHORITY, TODO_TABLE + "/#", TODO_TABLE_ROW_VALUE);
        sUriMatcher.addURI(AUTHORITY, COMPLETED_TABLE, COMPLETED_TABLE_VALUE);
        sUriMatcher.addURI(AUTHORITY, COMPLETED_TABLE + "/#", COMPLETED_TABLE_ROW_VALUE);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase sqlDB = myDB.getInstance(getContext()).getReadableDatabase();
        int uriType = sUriMatcher.match(uri);
        Cursor cursor;

        switch (uriType) {
            case TODO_TABLE_VALUE:
                cursor = sqlDB.query(TODO_TABLE, null,null,null,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

            case COMPLETED_TABLE_VALUE:
                cursor = sqlDB.query(COMPLETED_TABLE, null,null,null,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

            default:
                throw new IllegalArgumentException("Unknown URI");
        }
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        Log.d("App", "Uri @ Insert" + uri);
        int uriType = sUriMatcher.match(uri);

        SQLiteDatabase sqlDB = myDB.getInstance(getContext()).getWritableDatabase();

        long id = 0;
        String TABLE;
        switch (uriType) {
            case TODO_TABLE_VALUE:
                TABLE = TODO_TABLE;
                id = sqlDB.insert(TODO_TABLE, null, values);
                break;
            case COMPLETED_TABLE_VALUE:
                TABLE = COMPLETED_TABLE;
                Log.d("App", "IDHAR CONTENTPROV ME!" + values);
                id = sqlDB.insert(COMPLETED_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(TABLE + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        Log.d("App", uri + " " + uriType);
        SQLiteDatabase sqlDB = myDB.getInstance(getContext()).getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case TODO_TABLE_ROW_VALUE:
                String id = uri.getLastPathSegment();
                rowsDeleted = sqlDB.delete(TODO_TABLE, "_id =" + id, null);
                break;

            case TODO_TABLE_VALUE:
                rowsDeleted = sqlDB.delete(TODO_TABLE, null, null);
                break;

            case COMPLETED_TABLE_VALUE:
                rowsDeleted = sqlDB.delete(COMPLETED_TABLE, null, null);
                break;

            case COMPLETED_TABLE_ROW_VALUE:
                String id2 = uri.getLastPathSegment();
                rowsDeleted = sqlDB.delete(COMPLETED_TABLE, "_id =" + id2, null);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
