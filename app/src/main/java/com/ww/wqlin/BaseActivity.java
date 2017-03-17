package com.ww.wqlin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ww.wqlin.util.ScreenUtil;

import butterknife.ButterKnife;


@SuppressLint("InlinedApi")
public abstract class BaseActivity extends FragmentActivity {


    public View titleView;
    private TextView tvTitle;

    protected abstract void init();
    protected abstract int getLayoutResId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init the scale values
        ScreenUtil.init(this);

        // 初始化布局
        setContentView(getLayoutResId());
        // 代码实现软键盘不遮挡输入框，同时不引起其他布局的混乱
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ScreenUtil.scale(findView(android.R.id.content));
        try {
            ButterKnife.bind(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initTitle();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    protected void setTitle(String titleText) {
        if (tvTitle != null) {
            tvTitle.setText(titleText);
        }
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(View v, int id) {
        return (T) v.findViewById(id);
    }

    public void initTitle() {
        if (titleView == null) {
            titleView = findView(R.id.title_bar);
            if (titleView!=null) {
                tvTitle = (TextView) titleView.findViewById(R.id.title_text);
            }
        }

       init();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
