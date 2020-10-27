package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        PageControlFragment f1 = new PageControlFragment();
        PageViewerFragment f2 = new PageViewerFragment();

        ft.replace(R.id.page_control, f1);
        ft.replace(R.id.page_viewer, f2);

        ft.commit();
    }
}