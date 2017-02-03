package org.team2.unithon.openbook.items;

/**
 * Created by HunJin on 2017-02-04.
 */

public class MainFragmentItem {
    private int mNumber;
    private String mImgURL;
    private String mTitle;

    public MainFragmentItem(int number, String imgURL, String title) {
        this.mNumber = number;
        this.mImgURL = imgURL;
        this.mTitle = title;
    }

    public int getmNumber() {
        return mNumber;
    }

    public String getmImgURL() {
        return mImgURL;
    }

    public String getmTitle() {
        return mTitle;
    }
}
