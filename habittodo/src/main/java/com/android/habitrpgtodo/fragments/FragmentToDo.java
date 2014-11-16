package com.android.habitrpgtodo.fragments;

/**
 * Created by karthik on 11/16/14.
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.habitrpgtodo.R;
import com.android.habitrpgtodo.adapters.TodoCursorAdapter;
import com.android.habitrpgtodo.database.DBHandler;
import com.android.habitrpgtodo.database.Record;
import com.android.habitrpgtodo.provider.MyContentProvider;


import java.util.Random;

/**
 * Created by karthik on 11/14/14.
 */
public class FragmentToDo extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    View rootView;
    private static final int URL_LOADER = 0;
    DBHandler dbH;
    TodoCursorAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        Button btnAdd = (Button) rootView.findViewById(R.id.btnAdd);
        getLoaderManager().initLoader(URL_LOADER, null, this);

        //ADD BUTTON!

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbH = DBHandler.getInstance(getActivity());
                TextView taskName = (TextView) rootView.findViewById(R.id.txtTask);
                Random t = new Random();
                Integer num = t.nextInt(100);
                Record record = new Record(num.toString(), taskName.getText().toString(), false);
                dbH.addTask(record, MyContentProvider.TODO_TABLE_VALUE);
                dbH.select(getActivity());
            }
        });




        ListView listView = (ListView) rootView.findViewById(R.id.listViewTodo);

        mAdapter = new TodoCursorAdapter(getActivity(), null, 0);
        listView.setAdapter(mAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Clicked " + position + " " + id, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    /* LOADER METHODS */

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        // Returns a new CursorLoader
        return new CursorLoader(
                getActivity(),   // Parent activity context
                MyContentProvider.TODO_URI,        // Table to query
                null,     // Projection to return
                null,            // No selection clause
                null,            // No selection arguments
                null             // Default sort order
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

}
