package com.ww.wqlin.util.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ww.wqlin.view.CircleDrawable;
import com.ww.wqlin.view.RoundBorderBitmapDisplayer;

/**
 * Created by wqlin on 2017/1/19.
 */

public class XImageLoaderListener implements ImageLoadingListener {
    private XBitmapOptions options;
    /**
     * 1-->圆  2-->圆角
     */
    private int type;

    private XImageLoaderListener(int type, XBitmapOptions options) {
        this.type = type;
        this.options = options;
    }

    /**
     * @param type    1-->圆  2-->圆角
     * @param options
     * @return
     */
    public static XImageLoaderListener getNewInstance(int type, XBitmapOptions options) {
        return new XImageLoaderListener(type, options);
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        showImage(view, 1);
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        showImage(view, 2);
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {

    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }

    /**
     * @param view
     * @param status 1-->loading 2-->failed
     */
    private void showImage(View view, int status) {
        try {
            if (view != null && view instanceof ImageView) {
                boolean isShow = false;
                switch (status) {
                    case 1:
                        isShow = options.checkImageResOnLoading();
                        break;
                    case 2:
                        isShow = options.checkImageResOnFail();
                        break;
                }
                if (isShow) {
                    Context context = view.getContext();
                    //得到Resources对象
                    Resources r = context.getResources();
                    Bitmap bmp = null;
                    if (status == 1) {
                        bmp = BitmapFactory.decodeResource(r, options.getImageResOnLoading());
                    } else if (status == 2){
                        bmp = BitmapFactory.decodeResource(r, options.getImageResOnFail());
                    }
                    Drawable drawable = null;
                    if (bmp != null) {
                        switch (type) {
                            case 1:
                                drawable = new CircleDrawable(bmp);
                                break;
                            case 2:
                                BitmapDisplayer bitmapDisplayer = options.getBitmapDisplayer();
                                if (bitmapDisplayer!=null&& bitmapDisplayer instanceof RoundBorderBitmapDisplayer)
                                    drawable = ((RoundBorderBitmapDisplayer) bitmapDisplayer).getImageDrawable(new ImageViewAware((ImageView) view), bmp);
                                break;
                        }
                    }
                    if (drawable != null) {
                        ((ImageView) view).setImageDrawable(drawable);
                    } else {
                        if (bmp != null) {
                            ((ImageView) view).setImageBitmap(bmp);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
