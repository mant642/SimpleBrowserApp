package edu.temple.simplebrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

// I don't think this class requires a factory method ...
public class PagerFragment extends Fragment {

    ViewPager viewPager;
    private PagerFragmentListener listener;
    // Actually, maybe don't use the variable? Just pass in the function itself?
    ArrayList<PageViewerFragment> fragments;


    public PagerFragment() {
        // Required empty public constructor
    }

    // This hopefully gives the child fragment the ability to access its parent as per usual
    public interface PagerFragmentListener {
        ArrayList<PageViewerFragment> getArrayList();
        void onLinkClicked(String url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View l = inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = l.findViewById(R.id.ViewPager);

        // This should let you access the Activity's ArrayList
        // But would it be updated when the Activity is updated?
        fragments = listener.getArrayList();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
                // return listener.getArrayList().get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
                // return listener.getArrayList().size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (fragments.get(position).webView.getTitle() != null) {
                    getActivity().setTitle(fragments.get(position).webView.getTitle());
                }
                // Do I need to null check this too?
                /*
                if (fragments.get(position).webView.getUrl() != null) {
                    listener.onLinkClicked(fragments.get(position).webView.getUrl());
                }
                 */
                // Better?
                if (fragments.get(position).webView.getUrl() == null) {
                    listener.onLinkClicked("about:blank");
                } else {
                    listener.onLinkClicked(fragments.get(position).webView.getUrl());
                }
            }
        });
        return l;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PagerFragmentListener) {
            listener = (PagerFragmentListener) context;
        } else {
            throw new RuntimeException("Forgot to implement ViewFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /*
    public void setPageDisplayed(int item) {
        viewPager.setCurrentItem(item);
    }
     */

    public ViewPager getViewPager() {
        return viewPager;
    }

    /*
    public int returnCurrentPage () {
        return viewPager.getCurrentItem();
    }
     */
}