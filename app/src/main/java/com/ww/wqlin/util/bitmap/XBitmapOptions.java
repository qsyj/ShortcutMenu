package com.ww.wqlin.util.bitmap;

import com.nostra13.universalimageloader.core.display.BitmapDisplayer;

/**
 * Created by wqlin on 2017/1/19.
 */

public class XBitmapOptions {
    private int imageResOnLoading;
    private int imageResForEmptyUri;
    private int imageResOnFail;
    private int iamgeDefault;
    private BitmapDisplayer bitmapDisplayer;

    private XBitmapOptions() {
    }

    private XBitmapOptions(int iamgeDefault) {
        this(iamgeDefault, null);
    }
    private XBitmapOptions(int iamgeDefault,BitmapDisplayer bitmapDisplayer) {
        this.iamgeDefault = iamgeDefault;
        this.imageResOnLoading = iamgeDefault;
        this.imageResForEmptyUri = iamgeDefault;
        this.imageResOnFail = iamgeDefault;
        this.bitmapDisplayer = bitmapDisplayer;
    }
    public static XBitmapOptions getNewInstance() {
        return new XBitmapOptions();
    }

    public static XBitmapOptions getNewInstance(int iamgeDefault) {
        return new XBitmapOptions(iamgeDefault);
    }
    public static XBitmapOptions getNewInstance(int iamgeDefault,BitmapDisplayer bitmapDisplayer) {
        return new XBitmapOptions(iamgeDefault,bitmapDisplayer);
    }
    public int getImageResOnLoading() {
        return imageResOnLoading;
    }

    public int getImageResForEmptyUri() {
        return imageResForEmptyUri;
    }

    public int getImageResOnFail() {
        return imageResOnFail;
    }

    public boolean checkImageResOnFail() {
        if (imageResOnFail > 0) {
            return true;
        }
        return false;
    }

    public boolean checkImageResOnLoading() {
        if (imageResOnLoading > 0) {
            return true;
        }
        return false;
    }

    public BitmapDisplayer getBitmapDisplayer() {
        return bitmapDisplayer;
    }

}
