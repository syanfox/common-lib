package com.guo.common.customcontrol;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guo.common.R;


public class UpdateDialog extends Dialog implements View.OnClickListener{

    private Context context;

    private int layoutResID;

    /**
     * 要监听的控件id
     */
    private int[] listenedItems;

    private OnCenterItemClickListener listener;


    private TextView updataversion_title;
    private TextView updataversion_code;
    private TextView updataversion_msg;
    private LinearLayout dialog_sure;


    public UpdateDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    public UpdateDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Window window = getWindow();
        //window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        //window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        //setContentView(layoutResID);
        setContentView(R.layout.dialog_update);
        // 宽度全屏
        //WindowManager windowManager = ((Activity) context).getWindowManager();
        //Display display = windowManager.getDefaultDisplay();
        //WindowManager.LayoutParams lp = getWindow().getAttributes();
        //lp.width = display.getWidth()*4/5; // 设置dialog宽度为屏幕的4/5
        //getWindow().setAttributes(lp);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);

     /*   for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }*/

        initView();
    }


    private void initView(){

        //标题
        updataversion_title=(TextView)findViewById(R.id.updataversion_title);
        //版本编码
        updataversion_code=(TextView)findViewById(R.id.updataversion_code);
        //updataversion_msg  升级信息
        updataversion_msg=(TextView)findViewById(R.id.updataversion_msg);
        dialog_sure=(LinearLayout)findViewById(R.id.dialog_sure);

        dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpgradeClickListener.OnUpgradeClickListener();
            }
        });
    }


    public void setText(String titel,String code,String msg){
        updataversion_title.setText(titel);
        updataversion_code.setText(code);
        updataversion_msg.setText(msg);
    }


    public interface OnCenterItemClickListener {

        void OnCenterItemClick(UpdateDialog dialog, View view);

    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        listener.OnCenterItemClick(this, view);
    }


    private OnUpgradeClickListener onUpgradeClickListener;

    public interface OnUpgradeClickListener{
        void OnUpgradeClickListener();
    }

    public void setOnUpgradeClickListener(OnUpgradeClickListener listener){
        this.onUpgradeClickListener=listener;
    }
}
