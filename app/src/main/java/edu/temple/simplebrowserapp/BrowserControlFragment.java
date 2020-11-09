package edu.temple.simplebrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

// Also probably doesn't need a factory method
public class BrowserControlFragment extends Fragment {
    private ImageButton imageButton;
    private BrowserControlFragmentListener listener;

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    public interface BrowserControlFragmentListener {
        void addNewPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_browser_control, container, false);
        imageButton = l.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addNewPage();
                Log.e("BrowserActivity","This works successfully");
            }
        });
        return l;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BrowserControlFragmentListener) {
            listener = (BrowserControlFragmentListener) context;
        } else {
            throw new RuntimeException("Forgot to implement BrowserControlFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}