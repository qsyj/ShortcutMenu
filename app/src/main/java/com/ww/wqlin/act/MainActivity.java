package com.ww.wqlin.act;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StrongGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.ww.wqlin.BaseActivity;
import com.ww.wqlin.R;
import com.ww.wqlin.bean.MyShortcutMenuBean;
import com.ww.wqlin.util.ToastUtil;
import com.ww.wqlin.widget.ChannelRecyclerView.adapter.ChannelAdapter;
import com.ww.wqlin.widget.ChannelRecyclerView.animator.ShortMenuAnimator;
import com.ww.wqlin.widget.ChannelRecyclerView.bean.ShortcutMenuBean;
import com.ww.wqlin.widget.ChannelRecyclerView.decoration.ShortMenuItemDecoration;
import com.ww.wqlin.widget.ChannelRecyclerView.helper.ItemDragHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.rv_short_menu)
    RecyclerView rv;
    private final int DEFAULT_SHORTCUTMENU_NUM = 4;
    private List<ShortcutMenuBean> otherChannelList = new ArrayList<ShortcutMenuBean>();//其它栏目列表
    private List<ShortcutMenuBean> userChannelList = new ArrayList<ShortcutMenuBean>();//用户栏目列表

    private ChannelAdapter adapter;

    @Override
    protected void init() {
        setTitle("快捷菜单");
        initRecyclerView();
        getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
    private void initRecyclerView() {
        final ShortMenuItemDecoration shortMenuItemDecoration =new ShortMenuItemDecoration(this);
        StrongGridLayoutManager manager = new StrongGridLayoutManager(this, 4, new StrongGridLayoutManager.ICalculateItemBorders() {
            @Override
            public int[] calculateItemBorders(int[] cachedBorders, int spanCount, int totalSpace) {
                cachedBorders[0]=0;
                for (int i = 1; i <= spanCount; i++) {
                    if (i != spanCount) {
                        cachedBorders[i] = shortMenuItemDecoration.getItem_w() * i + shortMenuItemDecoration.getPadding_left();
                    } else {
                        cachedBorders[i] = totalSpace;
                    }
                }
                return cachedBorders;
            }
        });
        rv.setLayoutManager(manager);
        ShortMenuAnimator shortMenuAnimator=new ShortMenuAnimator(rv);
        rv.setItemAnimator(shortMenuAnimator);
        rv.addItemDecoration(shortMenuItemDecoration);
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
        adapter = new ChannelAdapter(this, helper,userChannelList , otherChannelList,DEFAULT_SHORTCUTMENU_NUM,shortMenuAnimator.getMoveDuration());
        adapter.setEditMode(true);
        manager.setSpanSizeLookup(new StrongGridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        rv.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position < DEFAULT_SHORTCUTMENU_NUM) {
                    ToastUtil.showLongMessage(MainActivity.this, MainActivity.this.getString(R.string.cannotModify));
                }
            }
        });
    }

    private void getData() {
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_add_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_adting_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_appoint_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_card_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_collect_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_credit_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_draw_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_friendsting_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_interal_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_loan_xxh));
        userChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_member_xxh));

        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_money_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_opinionting_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_order_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_pay_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_playting_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_setting_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_shoppingcar_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_setting_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_takeout_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_task2_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_taskting_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_tiket_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_topline_xxh));
        otherChannelList.add(new MyShortcutMenuBean(R.drawable.btn_shortcut_transfer_xxh));

        adapter.notifyDataSetChanged();
    }
}
