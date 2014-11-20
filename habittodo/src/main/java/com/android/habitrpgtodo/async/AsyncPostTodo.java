package com.android.habitrpgtodo.async;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by karthik on 11/20/14.
 */

public class AsyncPostTodo extends AsyncTask<String, Void, Cursor> {

    private String result;
    private HttpResponse httpResponse;
    private JSONObject json, jsonResult;
    private MatrixCursor cursor;

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
    }

    @Override
    protected Cursor doInBackground(String... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://habitrpg.com/api/v2/user/");
        httpPost.addHeader("x-api-key","");
        httpPost.addHeader("x-api-user","");
        httpPost.addHeader("Content-Type", "application/json");
        cursor = new MatrixCursor(new String[] {"_id", "uuid", "name", "completed"});
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
            Log.d("App", result);
            try {
                jsonResult = new JSONObject(result);
                JSONArray jsonArray = jsonResult.getJSONArray("todos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("App", "Adding: " + jsonObject.getString("id") + " " + jsonObject.getString("text") + " " + jsonObject.getString("completed"));
                    cursor.newRow().add(i+1).add(jsonObject.getString("id")).add(jsonObject.getString("text")).add(jsonObject.getString("completed"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cursor;
    }
}