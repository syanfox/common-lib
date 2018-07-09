package com.guo.librarymanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.guo.common.customcontrol.UpdateDialog;
import com.guo.common.util.LogUtils;
import com.guo.common.util.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDialog updateDialog=new UpdateDialog(MainActivity.this);

                updateDialog.setOnUpgradeClickListener(new UpdateDialog.OnUpgradeClickListener() {
                    @Override
                    public void OnUpgradeClickListener() {
                        ToastUtils.showShort("升级事件");
                    }
                });

                updateDialog.show();

                updateDialog.setText("升级到最新版本","V1.0.1","1.bug修复\r\n2.其他问题解决");
            }
        });

    }
}
