    package edu.temple.simplebrowserapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ControlFragmentListener, PageViewerFragment.ViewFragmentListener, PagerFragment.PagerFragmentListener, BrowserControlFragment.BrowserControlFragmentListener, PageListFragment.ListFragmentListener {
    // Part of potential fix for nonserializable error
    // Yeah, this doesn't work
    // transient Context context;
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
    public static final String EXTRA_ARRAY = "edu.temple.simplebrowserapp.EXTRA_ARRAY";

    ArrayList<Bookmark> bookmarks;

    // int orientation;

    // Add a new PageViewerFragment to the fragments array and update the ViewPager so a URL can be loaded as soon as the app is ready
    @Override
    protected void onStart() {
        super.onStart();
        fragments.add(new PageViewerFragment());
        f5.viewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = (findViewById(R.id.page_list) != null);

        // Not sure this thing will be preserved when the activity is recreated; should probably find a way to preserve it (Bundle?)
        // fragments = new ArrayList<>();
        // fragments.add(new PageViewerFragment());

        if (savedInstanceState != null) {
            // fragments = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(KEY_ARRAY_VALUE);
            // So you can't resolve the error by simply removing the Serializable interface, need to implement Parcelable
            // fragments = (ArrayList) savedInstanceState.getSerializable(KEY_ARRAY_VALUE);
            // Not quite
            // fragments = (ArrayList) savedInstanceState.getParcelable(KEY_ARRAY_VALUE);
            fragments = (ArrayList) savedInstanceState.getParcelableArrayList(KEY_ARRAY_VALUE);
        } else {
            fragments = new ArrayList<>();
        }

        // Initial version of Bookmarks Array; isn't preserved across activity reset or end
        // bookmarks = new ArrayList<>();
        // This new implementation should be preserved across activity destruction


        SharedPreferences sharedPreferences = getSharedPreferences("sharedpref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookmarks_array", null);
        Type type = new TypeToken<ArrayList<Bookmark>>() {}.getType();
        bookmarks = gson.fromJson(json, type);

        if (bookmarks == null) {
            bookmarks = new ArrayList<>();
        }

        // context = this;

        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

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

        // Handling the implicit intent
        Intent receivedIntent = getIntent();
        String action = receivedIntent.getAction();
        Uri uri = receivedIntent.getData();

        if (Intent.ACTION_VIEW.equals(action)) {
            Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                // Yeah, this doesn't work
                /*
                String urlTitle = fragments.get(f5.viewPager.getCurrentItem()).webView.getTitle();
                String urlText = fragments.get(f5.viewPager.getCurrentItem()).webView.getUrl();
                // Toast.makeText(getApplicationContext(), urlTitle + " " + urlText, Toast.LENGTH_SHORT).show();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_HTML_TEXT, urlText);
                shareIntent.putExtra(Intent.EXTRA_TEXT, urlTitle);

                // Yeah, what's our type?
                // shareIntent.setType()

                startActivity(Intent.createChooser(shareIntent, "How will you share the URL?"));
                 */

                String urlText = fragments.get(f5.viewPager.getCurrentItem()).webView.getUrl();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, urlText);
                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent, "How will you share the URL?"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
            // Maybe this works?
            if (f4 != null) {
                f4.pageViewerFragmentAdapter.notifyDataSetChanged();
            }
        } else {
            // f5.fragments.get(f5.returnCurrentPage()).updateUrl(input);
            fragments.get(f5.viewPager.getCurrentItem()).updateUrl(input);
            // Maybe this works?
            if (f4 != null) {
                f4.pageViewerFragmentAdapter.notifyDataSetChanged();
            }
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
        f5.viewPager.getAdapter().notifyDataSetChanged();
        // For some reason, this doesn't work. No matter, I'll just implement it in the PageListFragment.
        // f4.listView.getAdapter().notifyDataSetChanged();

        if (f4 != null) {
            // But this does ...
            f4.pageViewerFragmentAdapter.notifyDataSetChanged();
        }

        // Should display new page automatically
        f5.viewPager.setCurrentItem(fragments.size() - 1);
    }

    @Override
    public void addNewBookmark() {
        // I could do the ugliest thing and call ArrayList's add method with Bookmark's constructor, while using the PageViewerFragment's WebView references
        // for arguments
        // Which I'm totally going to do
        // Not sure about getting the current URL with webView.getUrl()
        bookmarks.add(new Bookmark(fragments.get(f5.viewPager.getCurrentItem()).webView.getTitle(), fragments.get(f5.viewPager.getCurrentItem()).webView.getUrl()));
        // Can confirm, logs title and URL successfully
        //Log.e("BrowserActivity", bookmarks.get(0).URL);
    }

    @Override
    public void launchBookmarkManager() {
            Intent intent = new Intent(this, BookmarksActivity.class);
            intent.putParcelableArrayListExtra(EXTRA_ARRAY, bookmarks);
            // startActivity(intent);
            // Make the request code a constant later for better design
            startActivityForResult(intent, 1);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putSerializable(KEY_ARRAY_VALUE, fragments);
        // Perhaps not quite the right method?
        // outState.putParcelable(KEY_ARRAY_VALUE, fragments);
        outState.putParcelableArrayList(KEY_ARRAY_VALUE, fragments);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String receivedURL = data.getStringExtra("result");
                bookmarks = data.getParcelableArrayListExtra("modified_array");
                fragments.get(f5.viewPager.getCurrentItem()).updateUrl(receivedURL);
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Operation aborted.", Toast.LENGTH_SHORT).show();
            }

            /*
            // This doesn't consider if the user hits back ...
            String receivedURL = data.getStringExtra("result");
            // This hopefully allows modifications made in BookmarksActivity to persist ...
            bookmarks = data.getParcelableArrayListExtra("modified_array");
            // Seems like the best bet to launch the webpage, yanked straight from onUrlEntered()
            fragments.get(f5.viewPager.getCurrentItem()).updateUrl(receivedURL);
            // Log.e("BrowserActivity", " " + receivedURL);
             */

            //Todo And that's how you do it in one line
            // Toast.makeText(getApplicationContext(), receivedURL, Toast.LENGTH_SHORT).show();
        }
    }

    // Well, this doesn't work. Maybe onStop()?
    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Again, should probably be a key
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarks);
        // Yep
        editor.putString("bookmarks_array", json);
        editor.commit();
    }
     */

    @Override
    protected void onStop() {
        super.onStop();
        // Again, should probably be a key
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarks);
        // Yep
        editor.putString("bookmarks_array", json);
        editor.apply();
    }
}