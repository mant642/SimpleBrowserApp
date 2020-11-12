package edu.temple.simplebrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class PageListFragment extends Fragment {

    ListView listView;
    ArrayList<PageViewerFragment> fragments;
    private ListFragmentListener listener;
    PageViewerFragmentAdapter pageViewerFragmentAdapter;

    public PageListFragment() {
        // Required empty public constructor
    }

    public interface ListFragmentListener {
        ArrayList<PageViewerFragment> getArrayList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_list, container, false);
        listView = l.findViewById(R.id.websiteList);
        fragments = listener.getArrayList();
        // Putting the adapter reference outside so other methods can access it
        // PageViewerFragmentAdapter pageViewerFragmentAdapter = new PageViewerFragmentAdapter(getActivity(), fragments);
        pageViewerFragmentAdapter = new PageViewerFragmentAdapter(getActivity(), fragments);
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

    // Er, nevermind?
    /*
    public void updateCustomAdapter() {
        pageViewerFragmentAdapter.notifyDataSetChanged();
    }
     */
}