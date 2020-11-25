package edu.temple.simplebrowserapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Bookmark implements Parcelable {
    String name;
    String URL;

    public Bookmark(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    protected Bookmark(Parcel in) {
        name = in.readString();
        URL = in.readString();
    }

    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        @Override
        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(URL);
    }
}
