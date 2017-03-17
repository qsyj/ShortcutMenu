package com.ww.wqlin.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/**
 * author: wqlin
 * time: 2016/8/4 10:51
 */
public class RoundBorderDrawable extends Drawable {
    public static final String TAG = "CircleDrawable";
    private final static int ALPHA_MAX = 255;

    protected final int margin;
    protected final BitmapShader bitmapShader;
    protected float radius;
    protected Bitmap oBitmap;//原图
    protected final Matrix matrix = new Matrix();
    private final RectF borderRect = new RectF();
    private final RectF imageRect = new RectF();
    protected int borderColor = Color.RED;
    protected int borderWidth = 0;
    private Paint borderPaint;
    private Paint imagePaint;
    private ImageAware imageAware;

    public RoundBorderDrawable(ImageAware imageAware, Bitmap bitmap){
        this(imageAware,bitmap,0,0,Color.RED,0);
    }

    public RoundBorderDrawable(ImageAware imageAware, Bitmap bitmap, int margin, int radius, int borderColor, int borderWidth) {
        this.imageAware = imageAware;
        this.margin = margin;
        this.oBitmap = bitmap;
        this.radius=radius;
        this.borderColor=borderColor;
        this.borderWidth=borderWidth;

        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        imagePaint = new Paint();
        imagePaint.setAntiAlias(true);
        imagePaint.setShader(bitmapShader);

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
//        borderPaint.setAlpha(Float.valueOf(borderAlpha * ALPHA_MAX).intValue());
        borderPaint.setStrokeWidth(borderWidth*2);

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        computeBitmapShaderSize();
        computeRadius();

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(borderRect, radius, radius, borderPaint);
        canvas.save();
//        canvas.concat(matrix);
        canvas.drawRoundRect(imageRect, radius, radius, imagePaint);
        canvas.restore();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        imagePaint.setAlpha(alpha);
        borderPaint.setAlpha(Float.valueOf(alpha * ALPHA_MAX).intValue());
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        imagePaint.setColorFilter(cf);
    }


    /**
     * 计算Bitmap shader 大小
     */
    public void computeBitmapShaderSize(){
        View view=imageAware.getWrappedView();

        Rect bounds = getBounds();
        if(bounds == null) return;
        //选择缩放比较多的缩放，这样图片就不会有图片拉伸失衡
        Matrix matrix = new Matrix();
        float scaleX = bounds.width() / (float)oBitmap.getWidth();
        float scaleY = bounds.height() / (float)oBitmap.getHeight();
        float scale = scaleX > scaleY ? scaleX : scaleY;
        matrix.postScale(scale,scale);
        if (view!=null &&view instanceof ImageView) {
            if (((ImageView) view).getScaleType() == ImageView.ScaleType.CENTER_CROP) {
                float imgWidth = oBitmap.getWidth() * scale;
                float boundsWidth = bounds.right - bounds.left;
                float imgHeight = oBitmap.getHeight() * scale;
                float boundsHeight = bounds.bottom - bounds.top;
                float translateX = 0;
                float translateY = 0;
                if (imgWidth > boundsWidth) {
                    translateX = (boundsWidth - imgWidth) / 2f;
                }
                if (imgHeight > boundsHeight) {
                    translateY = (boundsHeight - imgHeight) / 2f;
                }
                matrix.postTranslate(translateX, translateY);

            }
        }
        bitmapShader.setLocalMatrix(matrix);
    }

    /**
     * 计算半径的大小
     */
    public void computeRadius(){
        Rect bounds = getBounds();
        borderRect.set(borderWidth, borderWidth, bounds.right - bounds.left-borderWidth, bounds.bottom -bounds.top- borderWidth);
        imageRect.set(borderWidth, borderWidth, bounds.right - bounds.left-borderWidth, bounds.bottom -bounds.top- borderWidth);

    }
}
