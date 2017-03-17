package com.ww.wqlin.util.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Base64;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.ww.wqlin.R;
import com.ww.wqlin.util.ScreenUtil;


/**
 * Created by Mr HU on 2016/7/4.
 */
public class BitmapUtils {

    private static BitmapUtils mBitmapUtils;
    private static final int DEFAULT_BORDERCOLOR = Color.parseColor("#D2D2D2");
    private static final int DEFAULT_BORDERWIDTH = 2;
    private static final int DEFAULT_ROUND_RADIUS = 6;
    private Context mContext;

    private BitmapUtils(Context context) {
        this.mContext = context;
    }

    public static BitmapUtils getInstance(Context context) {
        if (mBitmapUtils == null) {
            mBitmapUtils = new BitmapUtils(context);
        }
        return mBitmapUtils;
    }

    public String resizeUrl(ImageView img, String url) {
        String type = "large";
        if (img.getLayoutParams() != null) {
            int width = img.getLayoutParams().width;
            int height = img.getLayoutParams().height;
            if (width < 400 && height < 400) {
                type = "thumb";
            } else if (width < 800 && height < 800) {
                type = "small";
            }
        }

        return url + "_" + type;
    }

    public void loadImage(String url, ImageLoaderListener listener) {
        ImageLoader.getInstance().loadImage(url, listener);
    }

    public void loadSampleImage(String url, ImageView pic) {
        Object tag = pic.getTag();
        url = resizeUrl(pic, url);
        if (tag == null || !url.equals(tag)) {
            pic.setTag(url);
            ImageLoader.getInstance().displayImage(url, pic, BitmapOptions.getSampleOpt());
        }
    }

    /**
     * ListView GridView RecyclerView时使用
     */
    public void loadSampleListImage(String url, ImageView pic) {
        Object tag = pic.getTag();
        url = resizeUrl(pic, url);
        if (tag == null || !url.equals(tag)) {
            pic.setTag(url);
            ImageLoader.getInstance().displayImage(url, pic, BitmapOptions.getRecyclerSampleOpt());
        }
    }

    public void loadSampleImage(String url, ImageView pic, int defaultImg) {
        Object tag = pic.getTag();
        url = resizeUrl(pic, url);
        if (tag == null || !url.equals(tag)) {
            pic.setTag(url);
            ImageLoader.getInstance().displayImage(url, pic, BitmapOptions.getSampleOpt(defaultImg));
        }
    }
    public void loadCircleImage(String url, ImageView pic) {
        loadCircleImage(url, R.drawable.image_default,pic);
    }
    public void loadCircleImage(String url,int defaultImageRes, ImageView pic) {
//        loadCircleImage(url, pic, R.drawable.image_default, 120);
        Object tag = pic.getTag();
        url = resizeUrl(pic, url);
        if (tag == null || !url.equals(tag)) {
            pic.setTag(url);
            ImageLoader.getInstance().displayImage(url, pic, BitmapOptions.getRCircleOpt(0),getCircleImageLoaderListener(defaultImageRes));
        }

    }
    /**
     *
     * @param url
     * @param pic
     * @param cornerRadiusPixels 不需要缩放
     */
    @Deprecated
    public void loadRoundImage(String url, ImageView pic, int cornerRadiusPixels) {
        loadRoundBorderImage(url,pic,cornerRadiusPixels);
    }

    public void loadRoundBorderImage(String url, ImageView pic) {
        loadRoundBorderImage(url,pic,DEFAULT_ROUND_RADIUS);
    }

    /**
     *
     * @param url
     * @param pic
     * @param radius 不需要缩放
     */
    public void loadRoundBorderImage(String url, ImageView pic, int radius) {
        loadRoundBorderImage(url,pic,radius,DEFAULT_BORDERCOLOR,DEFAULT_BORDERWIDTH);
    }
    /**
     *
     * @param url
     * @param pic
     * @param radius 不需要缩放
     */
    public void loadRoundBorderImage(String url, int defaultImg,ImageView pic, int radius) {
        loadRoundBorderImage(url,pic,defaultImg,radius,DEFAULT_BORDERCOLOR,DEFAULT_BORDERWIDTH);
    }
    /**
     *
     * @param url
     * @param pic
     * @param radius 不需要缩放
     * @param borderWidth 不需要缩放
     */
    public void loadRoundBorderImage(String url, ImageView pic, int radius, int borderWidth) {
        loadRoundBorderImage(url,pic,radius,DEFAULT_BORDERCOLOR,borderWidth);
    }

    /**
     *
     * @param url
     * @param pic
     * @param radius 不需要缩放
     * @param borderColor
     * @param borderWidth 不需要缩放
     */
    public void loadRoundBorderImage(String url, ImageView pic, int radius, int borderColor, int borderWidth) {
        loadRoundBorderImage(url,pic,R.drawable.image_default,radius,borderColor,borderWidth);
    }
    /**
     *
     * @param url
     * @param pic
     * @param radius 不需要缩放
     * @param borderColor
     * @param borderWidth 不需要缩放
     */
    public void loadRoundBorderImage(String url, ImageView pic,int defaultImg, int radius, int borderColor, int borderWidth) {
        Object tag = pic.getTag();
        url = resizeUrl(pic, url);
        if (tag == null || !url.equals(tag)) {
            pic.setTag(url);
            DisplayImageOptions options = BitmapOptions.getRoundBorderOpt(0, ScreenUtil.getScalePxValue(radius), borderColor, ScreenUtil.getScalePxValue(borderWidth));
            BitmapDisplayer displayer = options.getDisplayer();
            ImageLoader.getInstance().displayImage(url, pic,options,getRounderImageLoaderListener(displayer,defaultImg));
        }
    }
    /**
     * 获取裁剪后的圆形图片
     *
     * @param radius 半径
     */
    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;

        // 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        int squareWidth = 0, squareHeight = 0;
        int x = 0, y = 0;
        Bitmap squareBitmap;
        if (bmpHeight > bmpWidth) {// 高大于宽
            squareWidth = squareHeight = bmpWidth;
            x = 0;
            y = (bmpHeight - bmpWidth) / 2;
            // 截取正方形图片
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else if (bmpHeight < bmpWidth) {// 宽大于高
            squareWidth = squareHeight = bmpHeight;
            x = (bmpWidth - bmpHeight) / 2;
            y = 0;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else {
            squareBitmap = bmp;
        }

        if (squareBitmap.getWidth() != diameter
                || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
                    diameter, true);

        } else {
            scaledSrcBmp = squareBitmap;
        }
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
                scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
                paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        bmp = null;
        squareBitmap = null;
        scaledSrcBmp = null;
        return output;
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private  XImageLoaderListener getCircleImageLoaderListener(int defaultImageRes) {
        return XImageLoaderListener.getNewInstance(1,XBitmapOptions.getNewInstance(defaultImageRes));
    }
    private  XImageLoaderListener getRounderImageLoaderListener(BitmapDisplayer displayer,int defaultImageRes) {
        return XImageLoaderListener.getNewInstance(2,XBitmapOptions.getNewInstance(defaultImageRes,displayer));
    }
}
