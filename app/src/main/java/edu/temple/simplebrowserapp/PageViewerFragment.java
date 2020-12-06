package edu.temple.simplebrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;

public class PageViewerFragment extends Fragment implements Parcelable {
    WebView webView;
    View v;
    private ViewFragmentListener listener;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     */

    /*
    private String mParam1;
    private String mParam2;
     */

    public PageViewerFragment() {
        // Required empty public constructor
    }

    protected PageViewerFragment(Parcel in) {
    }

    public static final Creator<PageViewerFragment> CREATOR = new Creator<PageViewerFragment>() {
        @Override
        public PageViewerFragment createFromParcel(Parcel in) {
            return new PageViewerFragment(in);
        }

        @Override
        public PageViewerFragment[] newArray(int size) {
            return new PageViewerFragment[size];
        }
    };

    // These two methods are part of the Parcelable interface
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    /*
    public static PageViewerFragment newInstance(String param1, String param2) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
     */

    public interface ViewFragmentListener {
        void onLinkClicked (String url);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            v = inflater.inflate(R.layout.fragment_page_viewer, container, false);
            webView = v.findViewById(R.id.WebView);

            // WebView documentation doesn't specify how to create a new default WebViewClient ...
            // WebViewClient myWebViewClient = new WebViewClient();

            MyWebViewClient myWebViewClient = new MyWebViewClient();
            webView.setWebViewClient(myWebViewClient);
            // webView.loadUrl("https://www.temple.edu");

        } else {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_page_viewer, container, false);
            webView = v.findViewById(R.id.WebView);

            // WebView documentation doesn't specify how to create a new default WebViewClient ...
            // WebViewClient myWebViewClient = new WebViewClient();

            MyWebViewClient myWebViewClient = new MyWebViewClient();
            webView.setWebViewClient(myWebViewClient);
            // webView.loadUrl("https://www.temple.edu");
        }
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ViewFragmentListener) {
            listener = (ViewFragmentListener) context;
        } else {
            throw new RuntimeException("Forgot to implement ViewFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void updateUrl (String newUrl) {
        webView.loadUrl(newUrl);
        // Maybe set PageListFragment's TextViews here?

    }

    public void moveBack () {
        webView.goBack();
    }

    public void moveForward () {
        webView.goForward();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished (WebView webView, String url) {
            listener.onLinkClicked(url);
            super.onPageFinished(webView, url);
        }

    }


}