package com.ww.wqlin.util.bitmap;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.ww.wqlin.R;
import com.ww.wqlin.util.ScreenUtil;
import com.ww.wqlin.view.CircleBitmapDisplayer;
import com.ww.wqlin.view.RoundBorderBitmapDisplayer;


/**
 * Created by Mr HU on 2016/7/4.
 */
public class BitmapOptions {

    public static DisplayImageOptions getSampleOpt() {
        return BitmapOptions.getDisplayImageOptions(R.drawable.image_default);
    }
    /**
     * ListView GridView RecyclerView时 ImageLoader使用该配置
     */
    public static DisplayImageOptions getRecyclerSampleOpt() {
        return BitmapOptions.getListDisplayImageOptions(R.drawable.image_default);
    }
    public static DisplayImageOptions getSampleOpt(int defaultImg) {
        return BitmapOptions.getDisplayImageOptions(defaultImg);
    }

    public static DisplayImageOptions getRoundOpt() {
        return BitmapOptions.getDisplayImageBuilder(R.drawable.image_default).displayer(new RoundedBitmapDisplayer(ScreenUtil.getScalePxValue(120))).build();
    }

    public static DisplayImageOptions getRoundOpt(int cornerRadiusPixels) {
        return BitmapOptions.getDisplayImageBuilder(R.drawable.image_default).displayer(new RoundedBitmapDisplayer(ScreenUtil.getScalePxValue(cornerRadiusPixels))).build();
    }

    public static DisplayImageOptions getRoundOpt(int defaultImg, int cornerRadiusPixels) {
        return BitmapOptions.getDisplayImageBuilder(defaultImg).displayer(new RoundedBitmapDisplayer(ScreenUtil.getScalePxValue(cornerRadiusPixels))).build();
    }
    public static DisplayImageOptions getRCircleOpt(int defaultImg) {
        return BitmapOptions.getDisplayImageBuilder(defaultImg).displayer(new CircleBitmapDisplayer()).build();
    }
    public static DisplayImageOptions getRoundBorderOpt(int radius,int borderColor,int borderWidth) {
        return BitmapOptions.getDisplayImageBuilder(R.drawable.image_default).displayer(new RoundBorderBitmapDisplayer(ScreenUtil.getScalePxValue(radius),borderColor,ScreenUtil.getScalePxValue(borderWidth))).build();
    }
    public static DisplayImageOptions getRoundBorderOpt(int defaultImg,int radius,int borderColor,int borderWidth) {
        return BitmapOptions.getDisplayImageBuilder(defaultImg).displayer(new RoundBorderBitmapDisplayer(ScreenUtil.getScalePxValue(radius),borderColor,ScreenUtil.getScalePxValue(borderWidth))).build();
    }

/*==================================DisplayImageOptions配置 start====================================================*/
    public static DisplayImageOptions getDisplayImageOptions(int drawableRes) {
        return getDisplayImageOptions(drawableRes, drawableRes, drawableRes);
    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder(
            int drawableRes) {
        return getDisplayImageBuilder(drawableRes, drawableRes, drawableRes);
    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder(
            int onLoading, int emptyUri, int onFail) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.resetViewBeforeLoading(false).cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .showImageOnLoading(onLoading).showImageForEmptyUri(emptyUri)
                .showImageOnFail(onFail).build();
        return builder;
    }

    public static DisplayImageOptions getDisplayImageOptions(int onLoading,
                                                             int emptyUri, int onFail) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.resetViewBeforeLoading(false).cacheInMemory(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(100))
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .showImageOnLoading(onLoading).showImageForEmptyUri(emptyUri)
                .showImageOnFail(onFail).build();
        return builder.build();
    }

    public static DisplayImageOptions justGetOptions() {
        DisplayImageOptions opt = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(null)
                .showImageOnFail(null).cacheInMemory(true).cacheOnDisc(true)
                .considerExifParams(true).build();
        return opt;
    }
    /**
     * ListView GridView RecyclerView时 ImageLoader使用该配置
     * @param image_default
     * @return
     */
    public static DisplayImageOptions getListDisplayImageOptions(int image_default) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        DisplayImageOptions options=builder
                //.showImageOnLoading(R.drawable.loading) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(image_default)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(image_default)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                //.decodingOptions(BitmapFactory.Options decodingOptions)//设置图片的解码配置
                .delayBeforeLoading(0)//int delayInMillis为你设置的下载前的延迟时间
                //设置图片加入缓存前，对bitmap进行设置
                //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
                //.displayer(new RoundedBitmapDisplayer(20))//不推荐用！！！！是否设置为圆角，弧度为多少
                //.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间，可能会出现闪动
                .build();//构建完成
        return options;
    }
/*==================================DisplayImageOptions配置 end====================================================*/
}



