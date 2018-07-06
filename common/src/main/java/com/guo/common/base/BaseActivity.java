package com.guo.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.guo.common.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 *
 *  2018-07-03
 */
public abstract class BaseActivity extends AppCompatActivity {


    private static final String TAG = "BaseActivity";
    /**
     * 是否沉浸状态栏
     **/
    protected boolean isStatusBar = false;
    /**
     * 是否允许全屏
     **/
    protected boolean isFullScreen = false;
    /**
     * 是否禁止旋转屏幕
     **/
    protected boolean isScreenRoate = false;

    /**
     * context
     **/
    protected Context mContext;


    protected RxPermissions rxPermissions;




    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int setLayoutView();



    /**
     * 初始化界面
     **/
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 绑定事件
     */
    protected abstract void initEvent();

    private ScreenManager screenManager;

    private View mContextView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "--->onCreate()");

        Bundle bundle = getIntent().getExtras();
        mContextView = LayoutInflater.from(this).inflate(setLayoutView(), null);
        setContentView(mContextView);
        rxPermissions = new RxPermissions(this);
        initView(mContextView);
        initData();
        initEvent();
        mContext = this;
        if(isStatusBar){
            steepStatusBar();
        }
        ActivityStackManager.getActivityStackManager().pushActivity(this);
        //screenManager = ScreenManager.getInstance();
        //screenManager.setStatusBar(isStatusBar, this);
        //screenManager.setScreenRoate(isScreenRoate, this);
        //screenManager.setFullScreen(isFullScreen, this);
    }

    /**
     * 跳转Activity
     * skip Another Activity
     *
     * @param activity
     * @param cls
     */
    public  void skipAnotherActivity(Activity activity, Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 跳转Activity  携带参数
     * skip Another Activity
     *
     * @param activity
     * @param cls
     */
    public  void skipAnotherActivity(Activity activity, Class<? extends Activity> cls,Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        activity.finish();
    }



    /**
     * 沉浸状态栏
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 退出应用
     * called while exit app.
     */
    public void exitLogic() {
        ActivityStackManager.getActivityStackManager().popAllActivity();//remove all activity.
        System.exit(0);//system exit.
    }
    /**
     * [是否设置沉浸状态栏]
     * @param statusBar
     */
    public void setStatusBar(boolean statusBar) {
        isStatusBar = statusBar;
    }

    /**
     * [是否设置全屏]
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    /**
     * [是否设置旋转屏幕]
     * @param screenRoate
     */
    public void setScreenRoate(boolean screenRoate) {
        isScreenRoate = screenRoate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "--->onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "--->onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "--->onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "--->onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "--->onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "--->onDestroy()");
        ActivityStackManager.getActivityStackManager().popActivity(this);
    }



    protected View getEmptyView(RecyclerView recyclerView) {
        return getEmptyView(recyclerView, "");
    }

    protected View getEmptyView(RecyclerView recyclerView, String msg) {
        return getEmptyView(recyclerView, msg, -1);
    }

    protected View getEmptyView(RecyclerView recyclerView, String msg, @DrawableRes int imgRes) {
        View notDataView = getLayoutInflater().inflate(R.layout.common_empty_view, (ViewGroup) recyclerView.getParent(), false);
        TextView tvNoRecord = (TextView) notDataView.findViewById(R.id.emptyview_text);
        ImageView imgNoRecord = (ImageView) notDataView.findViewById(R.id.emptyview_imageview);
        if (!TextUtils.isEmpty(msg)) {
            tvNoRecord.setText(msg);
        }
        if (imgRes != -1) {
            imgNoRecord.setImageResource(imgRes);
        }
        return notDataView;
    }

}
