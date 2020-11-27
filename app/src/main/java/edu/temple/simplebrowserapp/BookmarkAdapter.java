package edu.temple.simplebrowserapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bookmark> bookmarks;

    public BookmarkAdapter (Context context, ArrayList<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookmarks.size();
    }

    @Override
    public Object getItem(int position) {
        return bookmarks.get(position);
    }

    // Guess I'll just ignore this forever
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView textView = rowView.findViewById(R.id.bookmarkTitle);
        textView.setText(bookmarks.get(position).name);
        Button button = rowView.findViewById(R.id.deleteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarks.remove(position);
                notifyDataSetChanged();
            }
        });
        return rowView;

        /*
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(bookmarks.get(position).name);
        return textView;
         */
    }
}
