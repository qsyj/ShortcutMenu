package com.ww.wqlin.widget.ChannelRecyclerView.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ww.wqlin.R;
import com.ww.wqlin.util.ScreenUtil;
import com.ww.wqlin.util.bitmap.BitmapUtils;
import com.ww.wqlin.widget.ChannelRecyclerView.bean.ShortcutMenuBean;
import com.ww.wqlin.widget.ChannelRecyclerView.helper.OnDragVHListener;
import com.ww.wqlin.widget.ChannelRecyclerView.helper.OnItemMoveListener;
import com.ww.wqlin.widget.ChannelRecyclerView.utils.VibratorUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 拖拽排序 + 增删
 * Created by YoKeyword on 15/12/28.
 */
public final class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemMoveListener {
    // 我的频道 标题部分
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    // 我的频道
    public static final int TYPE_MY = 1;
    // 其他频道 标题部分
    public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
    // 其他频道
    public static final int TYPE_OTHER = 3;
    //底部描述
    public static final int TYPE_BOTTOM = 4;

    // 我的频道之前的header数量  该demo中 即标题部分 为 1
    private static final int COUNT_PRE_MY_HEADER = 1;
    // 我的频道之前的header数量  该demo中 即标题部分 为 1
    private static final int COUNT_PRE_BOTTOM = 1;
    // 其他频道之前的header数量  该demo中 即标题部分 为 COUNT_PRE_MY_HEADER + 1
    private static final int COUNT_PRE_OTHER_HEADER = COUNT_PRE_MY_HEADER + 1;
    private  long duration_animmove;//动画移动 时长
    private long start_move_anim_time;//开始动画时间
    private long time_reset;//重置 一切属性

    private LayoutInflater mInflater;
    private ItemTouchHelper mItemTouchHelper;

    // 是否为 编辑 模式
    private boolean isEditMode;
    private List<ShortcutMenuBean> mMyChannelItems, mOtherChannelItems;

    // 我的频道点击事件
    private OnMyChannelItemClickListener mChannelItemClickListener;
    private Activity context;
    private final int DEFAULT_ITEM_NUM;
    private RecyclerView rv;
    private TextView tvNoMore;
    public static int getCountPreMyHeader() {
        return COUNT_PRE_MY_HEADER;
    }

    public static int getCountPreOtherHeader() {
        return COUNT_PRE_OTHER_HEADER;
    }

    public static int getCountPreBottom() {
        return COUNT_PRE_BOTTOM;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public List<ShortcutMenuBean> getMyChannelItems() {
        return mMyChannelItems;
    }

    public List<ShortcutMenuBean> getOtherChannelItems() {
        return mOtherChannelItems;
    }

    public boolean isMore() {
        if (mOtherChannelItems.size() == 0) {
            return false;
        }

        return true;
    }

    /**
     * My to Other 之前调用
     * @param isMore
     */
    private void refreshOtherHeader(boolean isMore) {
        if (tvNoMore != null) {
            if (isMore) {
                tvNoMore.setVisibility(View.GONE);
            } else {
                tvNoMore.setVisibility(View.VISIBLE);
            }
        }
    }
    private void delayNotifyDataSetChanged() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, time_reset);
    }
    private void setMoveInfo(View itemView, final ShortcutMenuBean item) {
        ShortcutMenuBean.MoveInfo moveInfo = new ShortcutMenuBean.MoveInfo();
        moveInfo.setFromX(itemView.getLeft());
        moveInfo.setFromY(itemView.getTop());
        item.setMoveInfo(moveInfo);
        setStartMoveAnimTime();
        rv.setNestedScrollingEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (item != null) {
                    rv.setNestedScrollingEnabled(true);
                    item.setMoveInfo(null);
                }
            }
        }, duration_animmove);

    }
    public boolean isDefaultItem(int position) {
        if (position >= DEFAULT_ITEM_NUM + COUNT_PRE_MY_HEADER) {
            return false;
        }
        return true;
    }

    public ChannelAdapter(Activity context, ItemTouchHelper helper, List<ShortcutMenuBean> mMyChannelItems,
                          List<ShortcutMenuBean> mOtherChannelItems,int defaultItemNum,long animMoveDuration) {
        initTime(animMoveDuration);
        DEFAULT_ITEM_NUM = defaultItemNum;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemTouchHelper = helper;
        this.mMyChannelItems = mMyChannelItems;
        this.mOtherChannelItems = mOtherChannelItems;

    }

    private void initTime(long animMoveDuration) {
        duration_animmove = animMoveDuration;
        time_reset = duration_animmove +100;
    }

    private boolean isMoveAnim() {
        if (System.currentTimeMillis() - start_move_anim_time < time_reset) {
            return true;
        }
        return false;
    }

    private void setStartMoveAnimTime() {
        start_move_anim_time = System.currentTimeMillis();
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {    // 我的频道 标题部分
            return TYPE_MY_CHANNEL_HEADER;
        } else if (position == mMyChannelItems.size() + COUNT_PRE_MY_HEADER) {    // 其他频道 标题部分
            return TYPE_OTHER_CHANNEL_HEADER;
        }
        else if (position == getItemCount() - 1) {
            return TYPE_BOTTOM;
        }
        else if (position > 0 && position < mMyChannelItems.size() + COUNT_PRE_MY_HEADER) {
            return TYPE_MY;
        }else {
            return TYPE_OTHER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (rv == null) rv = (RecyclerView) parent;
        final View view;
        switch (viewType) {
            case TYPE_MY_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_short_menu_header, parent, false);
                ScreenUtil.scaleProcess(view);
                ScreenUtil.scale(view);
                final MyChannelHeaderViewHolder holder = new MyChannelHeaderViewHolder(view);
                holder.tvBtnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEditMode) {
                            startEditMode((RecyclerView) parent);
                            holder.tvBtnEdit.setText(R.string.short_menu_finish);
                        } else {
                            cancelEditMode((RecyclerView) parent);
                            holder.tvBtnEdit.setText(R.string.short_menu_btn_modify);
                        }
                    }
                });
                return holder;

            case TYPE_MY:
                view = mInflater.inflate(R.layout.item_short_menu, parent, false);
                ScreenUtil.scaleProcess(view);
                ScreenUtil.scale(view);
                final MyViewHolder myHolder = new MyViewHolder(view);
                return myHolder;

            case TYPE_OTHER_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_short_menu_other, parent, false);
                ScreenUtil.scaleProcess(view);
                ScreenUtil.scale(view);
                return new OtherChannelHeaderViewHolder(view);
            case TYPE_BOTTOM:
                view = mInflater.inflate(R.layout.item_short_menu_bottom, parent, false);
                ScreenUtil.scaleProcess(view);
                ScreenUtil.scale(view);
                return new RecyclerView.ViewHolder(view) {};
            case TYPE_OTHER:
                view = mInflater.inflate(R.layout.item_short_menu, parent, false);
                ScreenUtil.scaleProcess(view);
                ScreenUtil.scale(view);
                final OtherViewHolder otherHolder = new OtherViewHolder(view);
                return otherHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            onBindMyViewHolder((MyViewHolder) holder,position);

        } else if (holder instanceof OtherViewHolder) {
           onBindOtherViewHolder((OtherViewHolder) holder,position);
        } else if (holder instanceof MyChannelHeaderViewHolder) {
            MyChannelHeaderViewHolder headerHolder = (MyChannelHeaderViewHolder) holder;
            if (isEditMode) {
                headerHolder.tvBtnEdit.setText(R.string.short_menu_finish);
            } else {
                headerHolder.tvBtnEdit.setText(R.string.short_menu_btn_modify);
            }
        } else if (holder instanceof OtherChannelHeaderViewHolder) {
            OtherChannelHeaderViewHolder otherHeaderHolder = (OtherChannelHeaderViewHolder) holder;
            if (isMore()) {
                otherHeaderHolder.tvNoMore.setVisibility(View.GONE);
            } else {
                otherHeaderHolder.tvNoMore.setVisibility(View.VISIBLE);
            }
        }
    }

    private void onBindMyViewHolder(final MyViewHolder myHolder, int position) {
        ShortcutMenuBean shortcutMenuBean=mMyChannelItems.get(position - COUNT_PRE_MY_HEADER);
        myHolder.textView.setText(shortcutMenuBean.getMenuName());
//        myHolder.img.setImageResource(shortcutMenuBean.getIconResId());
        BitmapUtils.getInstance(context).loadSampleImage(shortcutMenuBean.getEdit_icon(), myHolder.img,shortcutMenuBean.getIconResId());
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) myHolder.itemView.getLayoutParams();
//                Debug.e("宽度:" + myHolder.itemView.getWidth() + ",ml:" + params.leftMargin + ",mr:" + params.rightMargin + ",l:" + myHolder.itemView.getLeft() + ",r" + myHolder.itemView.getRight());
                int position = myHolder.getAdapterPosition();
                if (isEditMode) {
                    if (!isDefaultItem(position)) {
                        if (!isMoveAnim()) {
                            moveMyToOther(myHolder);
                        }
                    }
                }
                if (mChannelItemClickListener != null) {
                    mChannelItemClickListener.onItemClick(v, position - COUNT_PRE_MY_HEADER);
                }
            }
        });

        myHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                if (!isEditMode) {
                    startEditMode(rv);
                    // header 按钮文字 改成 "完成"
                    View view = rv.getChildAt(0);
                    if (view == rv.getLayoutManager().findViewByPosition(0)) {
                        TextView tvBtnEdit = (TextView) view.findViewById(R.id.tv_btn_edit);
                        tvBtnEdit.setText(R.string.short_menu_finish);
                    }
                }
                int position =myHolder.getAdapterPosition();
                if (!isDefaultItem(position)) {
                    VibratorUtil.Vibrate(context, 70);
                    mItemTouchHelper.startDrag(myHolder);
                }
                return true;
            }
        });
    }
    private void onBindOtherViewHolder(final OtherViewHolder otherHolder, int position) {
        ShortcutMenuBean shortcutMenuBean=mOtherChannelItems.get(position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER);
        otherHolder.textView.setText(shortcutMenuBean.getMenuName());
//        otherHolder.img.setImageResource(shortcutMenuBean.getIconResId());
        BitmapUtils.getInstance(context).loadSampleImage(shortcutMenuBean.getEdit_icon(), otherHolder.img,shortcutMenuBean.getIconResId());
        otherHolder.itemView.setOnLongClickListener(null);
        otherHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMoveAnim()) {
                    moveOtherToMy(otherHolder);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        // 我的频道  标题 + 我的频道.size + 其他频道 标题 + 其他频道.size
        return mMyChannelItems.size() + mOtherChannelItems.size() + COUNT_PRE_OTHER_HEADER+COUNT_PRE_BOTTOM;
    }

    /**
     * 反射修改RecyclerView.LayoutParams的boolean mInsetsDirty属性  当调用 notifyItemMoved(from,to)前调用该方法(传入true),
     * 这样RecyclerView在执行getItemDecorInsetsForChild()(该方法是去获取View的ItemDecoration)才会重新去获取from－to以外View的ItemDecoration尺寸
     * @param insetsDirty
     */
    private void setAllChildViewInsetsDirty(boolean insetsDirty) {
        try {
            int count = rv.getChildCount();
            for (int i = 0; i < count; i++) {
                View itemView=rv.getChildAt(i);
                RecyclerView.LayoutParams  params= (RecyclerView.LayoutParams) itemView.getLayoutParams();
                Field field=RecyclerView.LayoutParams.class.getDeclaredField("mInsetsDirty");
                field.setAccessible(true);
                field.set(params,insetsDirty);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 我的频道 移动到 其他频道
     *
     * @param myHolder
     */
    private void moveMyToOther(MyViewHolder myHolder) {
        int position = myHolder.getAdapterPosition();

        int startPosition = position - COUNT_PRE_MY_HEADER;
        if (startPosition > mMyChannelItems.size() - 1) {
            return;
        }
        if (mOtherChannelItems.size() == 0) {
            refreshOtherHeader(true);
        }
        ShortcutMenuBean item = mMyChannelItems.get(startPosition);
        mMyChannelItems.remove(startPosition);
        mOtherChannelItems.add(item);
        setMoveInfo(myHolder.itemView,item);
        setAllChildViewInsetsDirty(true);
        notifyItemMoved(position, mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER+mOtherChannelItems.size()-1);
        delayNotifyDataSetChanged();
    }

    /**
     * 其他频道 移动到 我的频道
     *
     * @param otherHolder
     */
    private void moveOtherToMy(OtherViewHolder otherHolder) {
        int position = processItemRemoveAdd(otherHolder);
        if (position == -1) {
            return;
        }
        setAllChildViewInsetsDirty(true);
        notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
        delayNotifyDataSetChanged();
    }
    private int processItemRemoveAdd(OtherViewHolder otherHolder) {
        int position = otherHolder.getAdapterPosition();

        int startPosition = position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER;
        if (startPosition > mOtherChannelItems.size() - 1) {
            return -1;
        }

        ShortcutMenuBean item = mOtherChannelItems.get(startPosition);
        mOtherChannelItems.remove(startPosition);
        mMyChannelItems.add(item);
        setMoveInfo(otherHolder.itemView,item);
        return position;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (isDefaultItem(toPosition)) {
            return;
        }
        ShortcutMenuBean item = mMyChannelItems.get(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.remove(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.add(toPosition - COUNT_PRE_MY_HEADER, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 开启编辑模式
     *
     * @param parent
     */
    private void startEditMode(RecyclerView parent) {
        isEditMode = true;
        // TODO 开启编辑模式
    }

    /**
     * 完成编辑模式
     *
     * @param parent
     */
    private void cancelEditMode(RecyclerView parent) {
        isEditMode = false;
        // TODO 完成编辑模式
    }


    public interface OnMyChannelItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnMyChannelItemClickListener(OnMyChannelItemClickListener listener) {
        this.mChannelItemClickListener = listener;
    }

    /**
     * 我的频道
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener {
        private TextView textView;
        private ImageView img;
        private Drawable background = null;
        private int bkcolor = -1;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_item);
            img = (ImageView) itemView.findViewById(R.id.iv_icon);
        }

        /**
         * item 被选中时
         */
        @Override
        public void onItemSelected() {
            Drawable drawable = itemView.getBackground();
            if (drawable == null) {
                bkcolor = 0;
            } else {
                background = drawable;
            }
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        /**
         * item 取消选中时
         */
        @Override
        public void onItemFinish() {
            itemView.setAlpha(1.0f);
            if (background != null) itemView.setBackgroundDrawable(background);
            if (bkcolor != -1) itemView.setBackgroundColor(bkcolor);
        }
    }

    /**
     * 其他频道
     */
    class OtherViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView img;

        public OtherViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_item);
            img = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }

    /**
     * 我的频道  标题部分
     */
    class MyChannelHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBtnEdit;

        public MyChannelHeaderViewHolder(View itemView) {
            super(itemView);
            tvBtnEdit = (TextView) itemView.findViewById(R.id.tv_btn_edit);
        }
    }
    /**
     * 其他的频道  标题部分
     */
    class OtherChannelHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNoMore;
        public OtherChannelHeaderViewHolder(View itemView) {
            super(itemView);
            tvNoMore = (TextView) itemView.findViewById(R.id.tv_no_more);
            ChannelAdapter.this.tvNoMore = tvNoMore;
        }
    }
}
