package com.ww.wqlin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.ww.wqlin.util.bitmap.BitmapUtils;

import java.util.ArrayList;


public class BaseApplication extends Application {
    private static BaseApplication instance;

    private boolean isMainActivityFinish = true;

    private ArrayList<Activity> runActivity = new ArrayList<>();

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageLoader(getApplicationContext());
        BitmapUtils.getInstance(getApplicationContext());


    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public void addRunActivity(Activity _value) {
        if (this.runActivity == null) {
            this.runActivity = new ArrayList<Activity>();
        }
        if (!this.runActivity.contains(_value)) {
            this.runActivity.add(_value);
        }
    }

    public void removeRunActivity(Activity _value) {
        if (this.runActivity != null) {
            this.runActivity.remove(_value);
        }
    }

    public void exitApp() {
        if (this.runActivity != null) {
            for (Activity act : this.runActivity) {
                act.finish();
            }
        }
    }

    public ArrayList<Activity> getRunActivity() {
        if (this.runActivity == null) {
            this.runActivity = new ArrayList<Activity>();
        }
        return this.runActivity;
    }

    public void setIsMainActivityFinish(boolean b) {
        isMainActivityFinish = b;
    }

    public boolean getIsMainActivityFinish() {
        return isMainActivityFinish;
    }

    public void setMainActivityFinish(boolean isMainActivityFinish) {
        this.isMainActivityFinish = isMainActivityFinish;
    }

    public void setRunActivity(ArrayList<Activity> runActivity) {
        this.runActivity = runActivity;
    }

    public void releaseData() {
        if (this.runActivity != null) {
            this.runActivity.clear();
            this.runActivity = null;
        }

    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
//                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }



}
