package com.mumu.meipai.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mumu.common.utils.LogUtil;
import com.mumu.common.utils.ToastUitl;
import com.mumu.meipai.R;
import com.mumu.meipai.fragment.AttentionFragment;
import com.mumu.meipai.fragment.FindFragment;
import com.mumu.meipai.fragment.HomeFragment;
import com.mumu.meipai.fragment.MineFragment;
import com.mumu.meipai.receiver.MainReceiver;
import com.mumu.vcamera.ui.record.MediaRecorderActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_tab_home;
    private ImageView iv_tab_attention;
    private ImageView iv_tab_find;
    private ImageView iv_tab_mime;

    private RelativeLayout rl_tab_home;
    private RelativeLayout rl_tab_attention;
    private RelativeLayout rl_tab_capture_video;
    private RelativeLayout rl_tab_find;
    private RelativeLayout rl_tab_mime;

    private TextView tv_tab_home;
    private TextView tv_tab_attention;
    private TextView tv_tab_find;
    private TextView tv_tab_mime;

    private Fragment fragments[];
    private ImageView[] iv_tabs;
    private TextView[] tv_tabs;

    private int currentTabIndex = 0;
    private int index = 0;
    private long exitTime = 0;
    private MainReceiver mainReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initNetEvent();
    }

    private void initNetEvent() {
        //注册监听网络状态变化的广播
        registerMainReceiver();
    }

    private void initData() {
        HomeFragment homeFragment = new HomeFragment();
        AttentionFragment attentionFragment = new AttentionFragment();
        FindFragment findFragment = new FindFragment();
        MineFragment mineFragment = new MineFragment();

        fragments = new Fragment[]{
                homeFragment,
                attentionFragment,
                findFragment,
                mineFragment
        };

        iv_tabs = new ImageView[]{
                iv_tab_home,
                iv_tab_attention,
                iv_tab_find,
                iv_tab_mime
        };
        tv_tabs = new TextView[]{
                tv_tab_home,
                tv_tab_attention,
                tv_tab_find,
                tv_tab_mime
        };

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[currentTabIndex])
                .show(fragments[currentTabIndex]).commitAllowingStateLoss();
    }

    private void initView() {
        rl_tab_home = (RelativeLayout) findViewById(R.id.rl_tab_home);
        rl_tab_attention = (RelativeLayout) findViewById(R.id.rl_tab_attention);
        rl_tab_capture_video = (RelativeLayout) findViewById(R.id.rl_tab_capture_video);
        rl_tab_find = (RelativeLayout) findViewById(R.id.rl_tab_find);
        rl_tab_mime = (RelativeLayout) findViewById(R.id.rl_tab_mime);

        iv_tab_home = (ImageView) findViewById(R.id.iv_tab_home);
        iv_tab_attention = (ImageView) findViewById(R.id.iv_tab_attention);
        iv_tab_find = (ImageView) findViewById(R.id.iv_tab_find);
        iv_tab_mime = (ImageView) findViewById(R.id.iv_tab_mime);

        tv_tab_home = (TextView) findViewById(R.id.tv_tab_home_text);
        tv_tab_attention = (TextView) findViewById(R.id.tv_tab_attention_text);
        tv_tab_find = (TextView) findViewById(R.id.tv_tab_find_text);
        tv_tab_mime = (TextView) findViewById(R.id.tv_tab_mime_text);


        rl_tab_home.setOnClickListener(this);
        rl_tab_attention.setOnClickListener(this);
        rl_tab_capture_video.setOnClickListener(this);
        rl_tab_find.setOnClickListener(this);
        rl_tab_mime.setOnClickListener(this);

        iv_tab_home.setSelected(true);
        tv_tab_home.setSelected(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_home:
                index = 0;
                LogUtil.loge("tabName: " + "Home");
                break;
            case R.id.rl_tab_attention:
                index = 1;
                LogUtil.loge("tabName: " + "Attention");
                break;
            case R.id.rl_tab_capture_video:
                startRecorder();
                LogUtil.loge("tabName: " + "Capture Video");
                break;
            case R.id.rl_tab_find:
                index = 2;
                LogUtil.loge("tabName: " + "Find");
                break;
            case R.id.rl_tab_mime:
                index = 3;
                LogUtil.loge("tabName: " + "Mine");
                break;
        }
        switchTabs(index);
    }

    private void startRecorder() {
        Intent intent = new Intent(this, MediaRecorderActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    private void switchTabs(int index) {
        if (currentTabIndex != index) {//currentTab unSelected
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);//hide currentTab
            if (!fragments[index].isAdded())
                trx.add(R.id.fragment_container, fragments[index]);
            trx.show(fragments[index]).commitAllowingStateLoss();
        }

        iv_tabs[currentTabIndex].setSelected(false);
        iv_tabs[index].setSelected(true);

        tv_tabs[currentTabIndex].setSelected(false);
        tv_tabs[index].setSelected(true);

        currentTabIndex = index;
    }


    /**
     * 注册一个网络状态变化的广播
     */
    public void registerMainReceiver() {
        mainReceiver = new MainReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(1000);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mainReceiver, filter);
    }

    private void unregisterReceiver() {
        if (mainReceiver != null)
            unregisterReceiver(mainReceiver);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    ToastUitl.showLong(getString(R.string.press_again_exit));
                    // 将系统当前的时间赋值给exitTime
                    exitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                    System.exit(0);
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
