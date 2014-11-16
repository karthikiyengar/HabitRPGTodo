package com.android.habitrpgtodo.adapters;

/**
 * Created by karthik on 11/16/14.
 */
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.android.habitrpgtodo.R;
import com.android.habitrpgtodo.database.DBHandler;
import com.android.habitrpgtodo.database.Record;
import com.android.habitrpgtodo.provider.MyContentProvider;


/**
 * Created by karthik on 11/13/14.
 */
public class CompletedCursorAdapter extends CursorAdapter {
    private Context context;
    String id, title;
    Boolean completed;
    Button btnUndo;
    Record record;
    DBHandler dbH;

    public CompletedCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listviewcompleted, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        /* Get stuff from cursor */
        id = cursor.getString(0);
        title = cursor.getString(1);
        completed = Boolean.parseBoolean(cursor.getString(2));


        TextView textName = (TextView) view.findViewById(R.id.textViewCompleted);
        LayoutInflater customInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        textName.setText(title);


        btnUndo = (Button) view.findViewById(R.id.btnUndo);



        record = new Record(id, title, completed);

        btnUndo.setTag(record);

        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Record record = (Record) v.getTag();
                dbH = DBHandler.getInstance(context);
                dbH.deleteTask(record, MyContentProvider.COMPLETED_TABLE_VALUE);
                dbH.addTask(record, MyContentProvider.TODO_TABLE_VALUE);
            }
        });
    }
}

