package com.ww.wqlin.widget.ChannelRecyclerView.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StrongGridLayoutManager;
import android.view.View;
import android.view.WindowManager;

import com.ww.wqlin.R;
import com.ww.wqlin.util.ScreenUtil;
import com.ww.wqlin.widget.ChannelRecyclerView.adapter.ChannelAdapter;


/**
 * Created by wqlin on 2016/12/15.
 */

public class ShortMenuItemDecoration extends RecyclerView.ItemDecoration{
    private  int item_w;
    private Context context;
    private final int padding_left;
    private final int padding_right;
    private final int padding_top;
    private final int padding_bottom;
    private Drawable mDivider;
    private final int screenWidth;

    public int getPadding_left() {
        return padding_left;
    }

    public int getPadding_right() {
        return padding_right;
    }

    public int getItem_w() {
        return item_w;
    }

    public ShortMenuItemDecoration(Context context) {
        this.context = context;
        mDivider = new ColorDrawable(Color.WHITE);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        Resources res = context.getResources();
        padding_left = ScreenUtil.getScalePxValue(res.getDimensionPixelOffset(R.dimen.short_menu_p_l));
        padding_right = ScreenUtil.getScalePxValue(res.getDimensionPixelOffset(R.dimen.short_menu_p_r));
        padding_top= ScreenUtil.getScalePxValue(res.getDimensionPixelOffset(R.dimen.short_menu_p_t));
        padding_bottom= ScreenUtil.getScalePxValue(res.getDimensionPixelOffset(R.dimen.short_menu_p_b));
        item_w = ScreenUtil.getScalePxValue(res.getDimensionPixelOffset(R.dimen.short_menu_item_w));
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawBackbackground(c,parent);
    }

    private void drawBackbackground(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int left=0;
        int right=screenWidth;
        int top=0;
        int bottom=parent.getBottom();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            Object tagObj=child.getTag(R.id.short_menu_bottom);
            if (tagObj != null) {
                boolean tag= (boolean) tagObj;
                if (tag) {
                    bottom=child.getBottom()+Math.round(ViewCompat.getTranslationY(child));
                }
            }
        }
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
    }
    private int getTop(Object firstTagObj,View child,int defaultValue) {
        int top = defaultValue;
        if (firstTagObj != null) {
            boolean tag= (boolean) firstTagObj;
            if (tag) {
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                top = child.getTop() - params.topMargin;
            }
        }
        return top;
    }

    private int getBottom(Object lastTagObj,View child,int defaultValue) {
        int bottom = defaultValue;
        if (lastTagObj != null) {
            boolean tag= (boolean) lastTagObj;
            if (tag) {
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                bottom = child.getBottom() + params.bottomMargin;
            }
        }
        return bottom;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemAdapterPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        ChannelAdapter adapter= (ChannelAdapter) parent.getAdapter();
        int type=adapter.getItemViewType(itemAdapterPosition);
        view.setTag(R.id.short_menu_bottom, false);
        RecyclerView.LayoutParams layoutParams= (RecyclerView.LayoutParams) view.getLayoutParams();
        switch (type) {
            case ChannelAdapter.TYPE_BOTTOM:
                view.setTag(R.id.short_menu_bottom, true);
                outRect.set(0, 0, 0, 0);
                break;
            case ChannelAdapter.TYPE_MY:
            case ChannelAdapter.TYPE_OTHER:
                int positionType = getPositionType(parent, itemAdapterPosition, type);

                if (positionType == 1||positionType == 10||positionType ==11 ||positionType ==310){
                    outRect.set(padding_left,0,0,0);
                } else if (positionType == 2 || positionType == 20 || positionType == 21|| positionType == 311) {
                    outRect.set(0, 0, padding_right, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
                break;
            default:
                outRect.set(0, 0, 0, 0);
                break;
        }
    }

    /**
     *
     * @param parent
     * @param itemAdapterPosition
     * @param type 0指中间   1指最左边一列  2指最右边一列   3指第一行   4指最后一行  10指第一列第一个  11指第一列最后一个  20指最后一列第一个  21指最后一列最后一个
     *             310指只有一行且第一个  311指只有一行且最后第一个  312指只有一行
     * @return
     */
    public static int getPositionType(RecyclerView parent, int itemAdapterPosition,int type) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        ChannelAdapter adapter= (ChannelAdapter) parent.getAdapter();
        if (layoutManager instanceof GridLayoutManager||layoutManager instanceof StrongGridLayoutManager) {
            int spanCount;

            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                spanCount = gridLayoutManager.getSpanCount();
            } else {
                StrongGridLayoutManager gridLayoutManager = (StrongGridLayoutManager) layoutManager;
                spanCount = gridLayoutManager.getSpanCount();
            }

            int mySize = adapter.getMyChannelItems().size();
            switch (type) {
                case ChannelAdapter.TYPE_MY:
                    int myPosition = itemAdapterPosition - ChannelAdapter.getCountPreMyHeader();
                    if (mySize <= spanCount) {
                        if (myPosition == 0) {
                            return 310;
                        }
                        if (myPosition == spanCount - 1) {
                            return 311;
                        }
                        return 312;
                    }
                    if (myPosition % spanCount == 0) {
                        if (myPosition == 0) {
                            return 10;
                        }
                        if (myPosition == (mySize- spanCount)) {
                            return 11;
                        }
                        return 1;
                    }
                    if ((myPosition + 1) % spanCount == 0) {
                        if (myPosition == spanCount-1) {
                            return 20;
                        }
                        if (myPosition == (mySize-1)) {
                            return 21;
                        }
                        return 2;
                    }
                   if (myPosition>0&&myPosition<spanCount-1) {
                        return 3;
                    }
                    if (myPosition > (mySize-spanCount) && myPosition < (mySize-1)) {
                        return 4;
                    }
                    break;
                case ChannelAdapter.TYPE_OTHER:
                    int otherSize = adapter.getOtherChannelItems().size();
                    int otherPosition = itemAdapterPosition - ChannelAdapter.getCountPreOtherHeader()-mySize;
                    if (otherSize <= spanCount) {
                        if (otherPosition == 0) {
                            return 310;
                        }
                        if (otherPosition == spanCount - 1) {
                            return 311;
                        }
                        return 312;
                    }
                    if (otherPosition % spanCount == 0) {
                        if (otherPosition == 0) {
                            return 10;
                        }
                        if (otherPosition == (otherSize - spanCount)) {
                            return 11;
                        }
                        return 1;
                    }
                    if ((otherPosition + 1) % spanCount == 0) {
                        if (otherPosition == spanCount-1) {
                            return 20;
                        }
                        if (otherPosition == (otherSize-1)) {
                            return 21;
                        }
                        return 2;
                    }
                    if (otherPosition>0&&otherPosition<spanCount-1) {
                        return 3;
                    }
                    if (otherPosition > (-spanCount) && otherPosition < (otherSize-1)) {
                        return 4;
                    }
                    break;
            }
        }
        return 0;
    }
}
