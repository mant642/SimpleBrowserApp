package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Bookmark> bookmarks;
    BookmarkAdapter bookmarkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        listView = findViewById(R.id.bookmarkList);
        Intent intent = getIntent();
        bookmarks = intent.getParcelableArrayListExtra(BrowserActivity.EXTRA_ARRAY);
        bookmarkAdapter = new BookmarkAdapter(this, bookmarks);
        listView.setAdapter(bookmarkAdapter);
    }
}