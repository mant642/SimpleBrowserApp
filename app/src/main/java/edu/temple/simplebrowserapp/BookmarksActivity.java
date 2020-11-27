package edu.temple.simplebrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Not sure if this is the proper way to access the URL ...
                String sendURL = bookmarks.get(position).URL;

                Intent resultIntent = new Intent ();
                // Again, replace with an actual key
                resultIntent.putExtra("result", sendURL);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        /*
        listView.findViewById(R.id.bookmarkTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
         */

        /*
        listView.findViewById(R.id.bookmarkTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String testString = v.toString();
                Toast.makeText(getApplicationContext(), "I'm here now.", Toast.LENGTH_SHORT).show();
            }
        });
         */
    }
}