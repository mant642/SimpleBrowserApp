package edu.temple.simplebrowserapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class PageViewerFragmentAdapter extends BaseAdapter {
    final Context context;
    ArrayList<PageViewerFragment> fragments;
    ViewPager viewPager;

    public PageViewerFragmentAdapter (Context context, ArrayList<PageViewerFragment> fragments, ViewPager viewPager) {
        this.context = context;
        this.fragments = fragments;
        this.viewPager = viewPager;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
        } else {
            textView = (TextView) convertView;
        }
        // Will this work? Causes Landscape to explode when getTitle == null
        if (fragments.get(position).webView.getTitle() != null) {
            textView.setText(fragments.get(position).webView.getTitle());
        }
        // textView.setText(fragments.get(position).webView.getTitle());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(position);
            }
        });
        return textView;
    }
}
