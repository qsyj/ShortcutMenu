package com.ww.wqlin.util.bitmap;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by wql on 2016/9/28.
 */

public abstract class ImageLoaderListener implements ImageLoadingListener{
    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }

    @Override
    public abstract void onLoadingComplete(String s, View view, Bitmap bitmap);
}
