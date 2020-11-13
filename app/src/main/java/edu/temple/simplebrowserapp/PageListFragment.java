package edu.temple.simplebrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PageListFragment extends Fragment {

    ListView listView;
    ViewPager viewPager;
    ArrayList<PageViewerFragment> fragments;
    private ListFragmentListener listener;
    PageViewerFragmentAdapter pageViewerFragmentAdapter;

    public PageListFragment() {
        // Required empty public constructor
    }

    public interface ListFragmentListener {
        ArrayList<PageViewerFragment> getArrayList();
        ViewPager getFragmentViewPager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_list, container, false);
        listView = l.findViewById(R.id.websiteList);
        fragments = listener.getArrayList();
        viewPager = listener.getFragmentViewPager();
        // Putting the adapter reference outside so other methods can access it
        // PageViewerFragmentAdapter pageViewerFragmentAdapter = new PageViewerFragmentAdapter(getActivity(), fragments);
        pageViewerFragmentAdapter = new PageViewerFragmentAdapter(getActivity(), fragments, viewPager);
        listView.setAdapter(pageViewerFragmentAdapter);
        return l;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragmentListener) {
            listener = (ListFragmentListener) context;
        } else {
            throw new RuntimeException("Forgot to implement ListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void refreshTextView () {

    }

    // Er, nevermind?
    /*
    public void updateCustomAdapter() {
        pageViewerFragmentAdapter.notifyDataSetChanged();
    }
     */
}