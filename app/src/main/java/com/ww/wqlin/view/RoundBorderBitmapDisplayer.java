package com.ww.wqlin.view;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * author: wqlin
 * time: 2016/8/12 13:41
 */
public class RoundBorderBitmapDisplayer implements BitmapDisplayer {
    protected  int margin, radius, borderColor, borderWidth;
    public RoundBorderBitmapDisplayer() {
        borderColor= Color.RED;
    }

    public RoundBorderBitmapDisplayer(int radius, int borderColor, int borderWidth) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    public RoundBorderBitmapDisplayer(int margin, int radius, int borderColor, int borderWidth) {
        this.margin = margin;
        this.radius = radius;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        imageAware.setImageDrawable(getImageDrawable(imageAware,bitmap));
    }

    public RoundBorderDrawable getImageDrawable(ImageAware imageAware, Bitmap bitmap) {
        return new RoundBorderDrawable(imageAware, bitmap, margin, radius, borderColor, borderWidth);
    }
}
