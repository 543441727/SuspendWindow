package com.qianmo.redrepair;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tinker.lib.tinker.TinkerInstaller;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;


/**
 * wangjitao
 * dex文件的生成和加载
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "wangjitao";
    private String activityName = getClass().getSimpleName();
    private TextView tv_content;
    private Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                Toast.makeText(MainActivity.this, tv_content.getText().toString(), Toast.LENGTH_SHORT).show();
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/path_signed.apk");
                Log.i("wangjitao", Environment.getExternalStorageDirectory().getAbsolutePath());
                break;
        }
    }
}
