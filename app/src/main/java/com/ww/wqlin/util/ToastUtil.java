package com.ww.wqlin.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ww.wqlin.R;

/**
 * @author hxd
 * @version : 1.0
 * @ClassName: ToastUtil
 * @Description: 避免Toast消息提示按照队列来重复提示
 * @date 2015-6-8 - 下午4:50:52
 */
public class ToastUtil {
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    private static Object synObj = new Object();

    /**
     * @param ctx
     * @param msg
     * @param isCenter 是否居中
     */
    public static void showShortMessage(final Context ctx, final String msg, final boolean isCenter) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                setTextMsg(toast,msg);
                                toast.setDuration(Toast.LENGTH_SHORT);
                            } else {
                                toast = createToast(ctx, msg, Toast.LENGTH_SHORT);
                            }
                            if (isCenter) {
                                toast.setGravity(Gravity.CENTER, 0, 0);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * @param ctx 使用时的上下文
     * @param msg 提示文字
     */
    public static void showShortMessage(final Context ctx, final String msg) {
        showShortMessage(ctx, msg, false);
    }

    public static void showShortMessage(String msg) {
        showShortMessage(null, msg);
    }

    public static void showShortCenterMessage(String msg) {
        showShortMessage(null, msg, true);
    }

    /**
     * @param ctx 使用时的上下文
     * @param msg 提示文字
     */
    public static void showLongMessage(final Context ctx, final String msg) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                setTextMsg(toast,msg);
                                toast.setDuration(Toast.LENGTH_LONG);
                            } else {
                                toast = createToast(ctx, msg, Toast.LENGTH_LONG);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    private static void setTextMsg(Toast toast,String msg) {
        if (toast != null) {
            TextView tv = getTextView(toast);
            tv.setText(msg);
        }
    }
    private static Toast createToast(Context context,CharSequence msg,int duration) {
       return createToast(context,msg, -1, duration);
    }
    private static Toast createToast(Context context,CharSequence msg,int gravity,int duration) {
//        if (context == null) {
//            context = BaseApplication.getInstance();
//        }
        Toast toast =Toast.makeText(context, msg, duration);
        if (gravity > 0) {
            toast.setGravity(gravity,0,0);
        }
        setTextSize(toast);
        return toast;
    }

    private static void setTextSize(Toast toast) {
        TextView view = getTextView(toast);
        Context context=view.getContext();
        int size = context.getResources().getDimensionPixelSize(R.dimen.text_size_middle2);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,ScreenUtil.getScalePxValue(size));
    }
    private static TextView getTextView(Toast toast) {
        LinearLayout layout = (LinearLayout) toast.getView();
        TextView tv = (TextView) layout.getChildAt(0);
        return tv;
    }
    public static void showLongMessage(String msg) {
        showLongMessage(null, msg);
    }

    public static void showLongMessage2(final String msg) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                LinearLayout layout = (LinearLayout) toast.getView();
                                TextView tv = (TextView) layout.getChildAt(0);
                                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                setTextMsg(toast,msg);
                                toast.setDuration(Toast.LENGTH_LONG);
                            } else {
                                toast = createToast(null, msg, Toast.LENGTH_LONG);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 吐出指定的视图，使其显示较长的时间
     *
     * @param context
     * @param view
     */
    public static final void toastL(Context context, View view) { // NO_UCD
        // (unused
        // code)
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 吐出指定的视图，使其显示较短的时间
     *
     * @param context
     * @param view
     */
    public static final void toastS(Context context, View view) { // NO_UCD
        // (unused
        // code)
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 吐出一个显示时间较长的提示
     *
     * @param context 上下文对象
     * @param resId   显示内容资源ID
     */
    public static final void toastL(Context context, int resId) { // NO_UCD
        // (unused
        // code)
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context 上下文对象
     * @param resId   显示内容资源ID
     */
    public static final void toastS(Context context, int resId) { // NO_UCD
        // (unused
        // code)
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐出一个显示时间较长的提示
     *
     * @param context 上下文对象
     * @param content 显示内容
     */
    public static final void toastL(Context context, String content) { // NO_UCD
        // (unused
        // code)
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context 上下文对象
     * @param content 显示内容
     */
    public static final void toastS(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐出一个显示时间较长的提示
     *
     * @param context     上下文对象
     * @param formatResId 被格式化的字符串资源的ID
     * @param args        参数数组
     */
    public static final void toastL(Context context, int formatResId, Object... args) { // NO_UCD
        // (unused
        // code)
        Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_LONG).show();
    }

    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context     上下文对象
     * @param formatResId 被格式化的字符串资源的ID
     * @param args        参数数组
     */
    public static final void toastS(Context context, int formatResId, Object... args) { // NO_UCD
        // (unused
        // code)
        Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐出一个显示时间较长的提示
     *
     * @param context 上下文对象
     * @param format  被格式化的字符串
     * @param args    参数数组
     */
    public static final void toastL(Context context, String format, Object... args) { // NO_UCD
        // (unused
        // code)
        Toast.makeText(context, String.format(format, args), Toast.LENGTH_LONG).show();
    }

    /**
     * 吐出一个显示时间较短的提示
     *
     * @param context 上下文对象
     * @param format  被格式化的字符串
     * @param args    参数数组
     */
    public static final void toastS(Context context, String format, Object... args) { // NO_UCD
        // (unused
        // code)
        Toast.makeText(context, String.format(format, args), Toast.LENGTH_SHORT).show();
    }

}
