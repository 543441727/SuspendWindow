package com.qianmo.suspendcircle;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;




/**
 * Created by Administrator on 2017/2/9 0009.
 * E-Mail：543441727@qq.com
 */

public class WindowService extends Service {
    private final String TAG = this.getClass().getSimpleName();

    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private View mWindowView;
    private TextView mPercenrTv;

    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Create");
        initWindowParams();
        initView();
        addWindowViewZWindow();
        initClick();
    }

    /**
     * 点击事件和拖拽事件
     */
    private void initClick() {
        mPercenrTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    //按下鼠标的时候记录下屏幕的位置
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) event.getRawX();
                        mStartY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int) event.getRawX();
                        mEndY = (int) event.getRawY();
                        if (needIntercept()) {
                            //getRawX是触摸位置相对于整个屏幕的位置，getX是控触摸点相对于控件最左边的位置
                            wmParams.x = (int) event.getRawX() - mWindowView.getMeasuredWidth() / 2;
                            wmParams.y = (int) event.getRawY() - mWindowView.getMeasuredHeight() / 2;
                            mWindowManager.updateViewLayout(mWindowView, wmParams);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (needIntercept()) {
                            return true;
                        }
                        break;
                }

                return false;
            }
        });

        mPercenrTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "点击了");
                if (isAppAtBackground(WindowService.this)) {
                    Intent intent = new Intent(WindowService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });


    }

    /**
     * 判断当前应用是前台还是后台
     */
    private boolean isAppAtBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否拦截，根据滑动的距离
     *
     * @return
     */
    private boolean needIntercept() {
        if (Math.abs(mStartX - mEndX) > 30 || Math.abs(mStartY - mEndY) > 30) {
            return true;
        }
        return false;
    }

    /**
     * 添加View到桌面Window界面上
     */
    private void addWindowViewZWindow() {
        mWindowManager.addView(mWindowView, wmParams);
    }

    /**
     * 初始化加速球控件
     */
    private void initView() {
        mWindowView = LayoutInflater.from(getApplication()).inflate(R.layout.layout_window, null);
        mPercenrTv = (TextView) mWindowView.findViewById(R.id.percentTv);
    }

    /**
     * 初始化Window对象的参数
     */
    private void initWindowParams() {
        //1,获取系统级别的WindowManager
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();

        //2,添加系统参数，确保悬浮框能显示到手机上

        //电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下。
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //期望的位图格式。默认为不透明
        wmParams.format = PixelFormat.TRANSLUCENT;
        //不许获得焦点
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //窗口停靠位置
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWindowView != null) {
            //移除悬浮窗口
            Log.i(TAG, "removeView");
            mWindowManager.removeView(mWindowView);
        }
        Log.i(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
