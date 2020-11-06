package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ControlFragmentListener, PageViewerFragment.ViewFragmentListener {
    FragmentManager fm;

    PageControlFragment f1;
    PageViewerFragment f2;
    BrowserControlFragment f3;
    PageListFragment f4;

    boolean isLandscape;

    // int orientation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = (findViewById(R.id.page_list) != null);

        fm = getSupportFragmentManager();

        Fragment tmpFragment;

        if ((tmpFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment) {
            f1 = (PageControlFragment) tmpFragment;
        } else {
            f1 = new PageControlFragment();
            fm.beginTransaction().add(R.id.page_control, f1).commit();
        }

        if ((tmpFragment = fm.findFragmentById(R.id.page_display)) instanceof PageViewerFragment) {
            f2 = (PageViewerFragment) tmpFragment;
        } else {
            f2 = new PageViewerFragment();
            fm.beginTransaction().add(R.id.page_display, f2).commit();
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
        if (!input.startsWith("https://")) {
            f2.updateUrl("https://" + input);
        } else {
            f2.updateUrl(input);
        }
    }

    @Override
    public void onBackClicked() {
        f2.moveBack();
    }

    @Override
    public void onNextClicked() {
        f2.moveForward();
    }

    @Override
    public void onLinkClicked(String url) {
        f1.refreshUrl(url);
    }
}