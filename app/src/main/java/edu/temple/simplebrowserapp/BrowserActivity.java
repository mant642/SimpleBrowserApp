package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ControlFragmentListener {
    PageViewerFragment f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        PageControlFragment f1 = new PageControlFragment();
        f2 = new PageViewerFragment();

        ft.replace(R.id.page_control, f1);
        ft.replace(R.id.page_viewer, f2);

        ft.commit();
    }

    @Override
    public void onUrlEntered(String input) {
        f2.updateUrl(input);
    }

    @Override
    public void onBackClicked() {
        f2.moveBack();
    }

    @Override
    public void onNextClicked() {
        f2.moveForward();
    }
}