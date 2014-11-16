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
import android.widget.Toast;
import com.android.habitrpgtodo.R;
import com.android.habitrpgtodo.database.DBHandler;
import com.android.habitrpgtodo.database.Record;
import com.android.habitrpgtodo.provider.MyContentProvider;


/**
 * Created by karthik on 11/13/14.
 */
public class TodoCursorAdapter extends CursorAdapter {
    private Context context;
    String id, title;
    Boolean completed;
    Button btnDelete, btnComplete;
    Record record;
    DBHandler dbH;

    public TodoCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listviewtodo, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        /* Get stuff from cursor */
        id = cursor.getString(0);
        title = cursor.getString(1);
        completed = Boolean.parseBoolean(cursor.getString(2));


        TextView textName = (TextView) view.findViewById(R.id.textViewMain);
        LayoutInflater customInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        textName.setText(title);

        btnDelete = (Button) view.findViewById(R.id.btnTaskDelete);
        btnComplete = (Button) view.findViewById(R.id.btnTaskCompleted);



        record = new Record(id, title, completed);
        btnDelete.setTag(record);
        btnComplete.setTag(record);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Record record = (Record) v.getTag();
                dbH = DBHandler.getInstance(context);
                dbH.deleteTask(record, MyContentProvider.TODO_TABLE_VALUE);
                Toast.makeText(context, record.id, Toast.LENGTH_SHORT).show();
            }
        });


        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Record record = (Record) v.getTag();
                dbH = DBHandler.getInstance(context);
                dbH.addTask(record, MyContentProvider.COMPLETED_TABLE_VALUE);
                dbH.deleteTask(record, MyContentProvider.TODO_TABLE_VALUE);
            }
        });
    }
}
