package org.team2.unithon.openbook.items;

/**
 * Created by HunJin on 2017-02-05.
 */

public class MainMapFragmentItem {
    int mNumber;
    String mTitle;
    String mLocation;

    public MainMapFragmentItem(int number, String title, String location) {
        this.mNumber = number;
        this.mTitle = title;
        this.mLocation = location;
    }

    public int getmNumber() {
        return mNumber;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmLocation() {
        return mLocation;
    }
}
