package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {

    ListView listView;
    Intent intent;
    ArrayList<Bookmark> bookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        listView = findViewById(R.id.websiteList);
        intent = getIntent();
        bookmarks = intent.getParcelableArrayListExtra(BrowserActivity.)

    }
}