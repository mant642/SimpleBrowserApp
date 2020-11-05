package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ControlFragmentListener, PageViewerFragment.ViewFragmentListener {
    PageControlFragment f1;
    PageViewerFragment f2;
    BrowserControlFragment f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            f1 = new PageControlFragment();
            f2 = new PageViewerFragment();
            f3 = new BrowserControlFragment();

            ft.replace(R.id.page_control, f1, "F1");
            ft.replace(R.id.page_display, f2, "F2");
            ft.replace(R.id.browser_control, f3, "F3");

            ft.commit();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            f1 = (PageControlFragment) fm.findFragmentByTag("F1");
            f2 = (PageViewerFragment) fm.findFragmentByTag("F2");
            f3 = (BrowserControlFragment) fm.findFragmentByTag("F3");

            ft.replace(R.id.page_control, f1);
            ft.replace(R.id.page_display, f2);
            ft.replace(R.id.browser_control, f3);

            ft.commit();

        }

        // Original implementation

        /*
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        f1 = new PageControlFragment();
        f2 = new PageViewerFragment();

        ft.replace(R.id.page_control, f1);
        ft.replace(R.id.page_viewer, f2);

        ft.commit();
         */
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