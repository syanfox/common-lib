package com.guo.common.customcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.guo.common.R;


public class ProgressDialogFragment extends DialogFragment {
    private int mLayoutId;


    public int getLayoutId() {
        return mLayoutId;
    }

    public void setLayoutId(int layoutId) {
        mLayoutId = layoutId == 0 ? R.layout.dialog_nomal_loading : layoutId;
    }

    public static ProgressDialogFragment newInstance(int layoutId) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("resId", layoutId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getArguments().getInt("resId");
        mLayoutId = resId == 0 ? R.layout.dialog_nomal_loading : resId;
        View view = inflater.inflate(mLayoutId, container, false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setBackgroundColor(getResources().getColor(R.color.color_8A000000));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(R.style.Translucent_NoTitle, R.style.Translucent_NoTitle);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }
}
