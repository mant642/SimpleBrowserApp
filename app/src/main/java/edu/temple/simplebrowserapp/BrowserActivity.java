package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ControlFragmentListener, PageViewerFragment.ViewFragmentListener, PagerFragment.PagerFragmentListener, BrowserControlFragment.BrowserControlFragmentListener, PageListFragment.ListFragmentListener {
    FragmentManager fm;

    PageControlFragment f1;
    PageViewerFragment f2;
    BrowserControlFragment f3;
    PageListFragment f4;
    PagerFragment f5;

    boolean isLandscape;

    // ArrayList of fragments
    ArrayList<PageViewerFragment> fragments;

    private static final String KEY_ARRAY_VALUE = "fragmentArray";

    // int orientation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = (findViewById(R.id.page_list) != null);

        // Not sure this thing will be preserved when the activity is recreated; should probably find a way to preserve it (Bundle?)
        // fragments = new ArrayList<>();
        // fragments.add(new PageViewerFragment());

        if (savedInstanceState != null) {
            fragments = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(KEY_ARRAY_VALUE);
        } else {
            fragments = new ArrayList<>();
        }

        fm = getSupportFragmentManager();

        Fragment tmpFragment;

        if ((tmpFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment) {
            f1 = (PageControlFragment) tmpFragment;
        } else {
            f1 = new PageControlFragment();
            fm.beginTransaction().add(R.id.page_control, f1).commit();
        }


        /*
        if ((tmpFragment = fm.findFragmentById(R.id.page_display)) instanceof PageViewerFragment) {
            f2 = (PageViewerFragment) tmpFragment;
        } else {
            f2 = new PageViewerFragment();
            fm.beginTransaction().add(R.id.page_display, f2).commit();
        }
         */



        if ((tmpFragment = fm.findFragmentById(R.id.page_display)) instanceof PagerFragment) {
            f5 = (PagerFragment) tmpFragment;
        } else {
            f5 = new PagerFragment();
            fm.beginTransaction().add(R.id.page_display, f5).commit();
        }


        if ((tmpFragment = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment) {
            f3 = (BrowserControlFragment) tmpFragment;
        } else {
            f3 = new BrowserControlFragment();
            fm.beginTransaction().add(R.id.browser_control, f3).commit();
        }

        if (isLandscape) {
            if ((tmpFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment) {
                f4 = (PageListFragment) tmpFragment;
            } else {
                f4 = new PageListFragment();
                fm.beginTransaction().add(R.id.page_list, f4).commit();
            }
        }

    }

    @Override
    public void onUrlEntered(String input) {
        // Perhaps check for https:// here?

        // No longer have reference to individual PageViewerFragments

        /*
        if (!input.startsWith("https://")) {
            f2.updateUrl("https://" + input);
        } else {
            f2.updateUrl(input);
        }
         */


        // Okay, so this works, but I'm not sure if it works well ...
        // f5.fragments.get(f5.returnCurrentPage()).updateUrl(input);
        // fragments.get(f5.returnCurrentPage()).updateUrl(input);

        // Final implementation:
        // fragments.get(f5.viewPager.getCurrentItem()).updateUrl(input);



        if (!input.startsWith("https://")) {
            // f5.fragments.get(f5.returnCurrentPage()).updateUrl("https://" + input);
            fragments.get(f5.viewPager.getCurrentItem()).updateUrl("https://" + input);
        } else {
            // f5.fragments.get(f5.returnCurrentPage()).updateUrl(input);
            fragments.get(f5.viewPager.getCurrentItem()).updateUrl(input);
        }

    }

    @Override
    public void onBackClicked() {
        // f2.moveBack();
        fragments.get(f5.viewPager.getCurrentItem()).moveBack();
    }

    @Override
    public void onNextClicked() {
        // f2.moveForward();
        fragments.get(f5.viewPager.getCurrentItem()).moveForward();
    }

    @Override
    public void onLinkClicked(String url) {
        f1.refreshUrl(url);
    }

    @Override
    public ArrayList<PageViewerFragment> getArrayList() {
        return fragments;
    }

    @Override
    public ViewPager getFragmentViewPager() {
        return f5.getViewPager();
    }

    @Override
    public void addNewPage() {
        fragments.add(new PageViewerFragment());
        // This is bad, assumes existence of fragment; change later
        f5.viewPager.getAdapter().notifyDataSetChanged();
        // For some reason, this doesn't work. No matter, I'll just implement it in the PageListFragment.
        // f4.listView.getAdapter().notifyDataSetChanged();

        if (f4 != null) {
            // But this does ... sometimes ...
            f4.pageViewerFragmentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_ARRAY_VALUE, fragments);
    }
}