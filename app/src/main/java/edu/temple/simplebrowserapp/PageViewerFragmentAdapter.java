package edu.temple.simplebrowserapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PageViewerFragmentAdapter extends BaseAdapter {
    final Context context;
    ArrayList<PageViewerFragment> fragments;

    public PageViewerFragmentAdapter (Context context, ArrayList<PageViewerFragment> fragments) {
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object getItem(int position) {
        return fragments.get(position);
    }

    // Might actually need this this time
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
        } else {
            textView = (TextView) convertView;
        }
        return textView;
    }
}
