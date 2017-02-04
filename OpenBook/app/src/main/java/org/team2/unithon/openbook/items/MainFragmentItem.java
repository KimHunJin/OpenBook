package org.team2.unithon.openbook.items;

/**
 * Created by HunJin on 2017-02-04.
 */

public class MainFragmentItem {
    private int mNumber;
    private String mImgURL;
    private String mTitle;
    private String mSubTitle;

    public MainFragmentItem(int number, String imgURL, String title, String subTitle) {
        this.mNumber = number;
        this.mImgURL = imgURL;
        this.mTitle = title;
        this.mSubTitle = subTitle;
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

    public String getmSubTitle() {
        return mSubTitle;
    }
}
