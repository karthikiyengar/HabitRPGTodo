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
import android.widget.ListView;
import com.android.habitrpgtodo.R;
import com.android.habitrpgtodo.adapters.CompletedCursorAdapter;
import com.android.habitrpgtodo.provider.MyContentProvider;


/**
 * Created by karthik on 11/14/14.
 */
public class FragmentCompleted extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    CompletedCursorAdapter mAdapter;
    private static final int URL_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_completed, container, false);
        getLoaderManager().initLoader(URL_LOADER, null, this);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewCompleted);
        mAdapter = new CompletedCursorAdapter(getActivity(), null, 0);
        listView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),   // Parent activity context
                MyContentProvider.COMPLETED_URI,        // Table to query
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
