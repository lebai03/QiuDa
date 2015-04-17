package com.example.lebai.qiuda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import miscutils.CommonUtil;
import miscutils.LocUtil;


public class SearchListActivity extends ActionBarActivity {

    private static final int LOCATE_TIMES = 5;
    private static final int LOCATE_GREEN_SUCCESS = 2;
    private static final int LOCATE_YELLOW_SUCCESS = 3;
    private static final int LOCATE_RED_FAILURE = 4;
    private static final int LOCATING = 5;
    private Context mContext = null;
    private SearchSpinnerAdapter adapterDistance;
    private SearchSpinnerAdapter adapterCategory;
    private ProgressDialog mProgDlg;
    private LocUtil mLocUtil;
    private volatile boolean mRunFlag = false;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // //执行接收到的通知，更新UI 此时执行的顺序是按照队列进行，即先进先出
            super.handleMessage(msg);
            Bundle bun = msg.getData();
            String address = "当前位置：" + bun.getString("address");
            switch (msg.what) {
                case LOCATING:
                    mProgDlg.show();
                    break;
                case LOCATE_GREEN_SUCCESS:
                    SearchBottomBar.setSearchBottomBar(mContext, address, R.drawable.circle_green);
                    mProgDlg.dismiss();
                    break;
                case LOCATE_YELLOW_SUCCESS:
                    SearchBottomBar.setSearchBottomBar(mContext, address, R.drawable.circle_yellow);
                    mProgDlg.dismiss();
                    break;
                case LOCATE_RED_FAILURE:
                    SearchBottomBar.setSearchBottomBar(mContext, address, R.drawable.circle_red);
                    mProgDlg.dismiss();
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Bundle bun = this.getIntent().getExtras();
        int index = bun.getInt("index");

        mContext = this;
        int screenWidth = CommonUtil.getWidth(mContext);

        int firstHeight = CommonUtil.dip2px(mContext, 40);
        if (firstHeight > (screenWidth / 6)) {
            firstHeight = screenWidth / 6;
        }
        Log.v("SearchListActivity", "firstHeight " + Integer.toString(firstHeight));

        Spinner spinnerDistance = (Spinner)findViewById(R.id.spinnerDistance);
        Spinner spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);

        RelativeLayout.LayoutParams sdlp = new RelativeLayout.LayoutParams(
                (screenWidth/3),  firstHeight);
        sdlp.leftMargin = 4;
        sdlp.topMargin = 4;
        spinnerDistance.setLayoutParams(sdlp);

        RelativeLayout.LayoutParams sclp = new RelativeLayout.LayoutParams(
                (screenWidth/3), firstHeight);
        sclp.leftMargin = sdlp.width + (screenWidth/16);
        Log.v("SearchListActivity", "sclp left " + Integer.toString(sclp.leftMargin));
        sclp.topMargin = 4;
        spinnerCategory.setLayoutParams(sclp);

        ImageButton ibMap = (ImageButton)findViewById(R.id.imageButtonMap);
        RelativeLayout.LayoutParams iblp = new RelativeLayout.LayoutParams(
                firstHeight, firstHeight);
        iblp.leftMargin = sdlp.width + sclp.width + (screenWidth/8);
        Log.v("SearchListActivity", "iblp left " + Integer.toString(iblp.leftMargin));
        iblp.topMargin = 4;
        ibMap.setLayoutParams(iblp);

        String[] listDistance = getResources().getStringArray(R.array.distance_array);
        String[] listCategory = getResources().getStringArray(R.array.category_array);

        adapterDistance = new SearchSpinnerAdapter(this, listDistance);
        adapterDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistance.setAdapter(adapterDistance);

        spinnerDistance.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将mySpinner 显示*/
                Log.v("SearchListActivity", "Distance Select " + adapterDistance.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });

        adapterCategory = new SearchSpinnerAdapter(this, listCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setSelection(index, true);

        spinnerCategory.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将mySpinner 显示*/
                Log.v("SearchListActivity", "Category Select " + adapterCategory.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });

        SearchBottomBar.setSearchBottomBar(mContext, "当前位置：", R.drawable.circle_red);

        mProgDlg = new ProgressDialog(mContext);
        mProgDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgDlg.setMessage("定位中，请稍等。。。");
        mProgDlg.setIndeterminate(false);
        mProgDlg.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLocUtil = new LocUtil(getApplicationContext());

        if (mLocUtil.isFirstLocation() == true) {
            mRunFlag = true;
            runLocateThread();
        }
        else {
            Location loc = new Location("");
            Message msg = new Message();
            if (mLocUtil.getLocation(loc) != -1) {
                if (loc.getAccuracy() < 100) {
                    msg.what = LOCATE_GREEN_SUCCESS;
                } else {
                    msg.what = LOCATE_YELLOW_SUCCESS;
                }
            }
            else {
                msg.what = LOCATE_RED_FAILURE;
            }
            Bundle bun = loc.getExtras();
            msg.setData(bun);
            mHandler.sendMessage(msg);
        }
        Log.v("SearchListActivity", "onResume finish");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void runLocateThread() {

        new Thread(){
            @Override
            public void run()
            {
                while(!mRunFlag) {
                    SystemClock.sleep(200);
                }
                mRunFlag = false;
                mLocUtil.startRequestLocation();
                int cnt = 0;
                Location loc = new Location("");
                double currentLatitude = 0.0;
                double currentLongitude = 0.0;
                float currentAccuracy = 0;

                Message msg = new Message();
                msg.what = LOCATING;
                SearchListActivity.this.mHandler.sendMessage(msg);

                while(cnt < LOCATE_TIMES) {
                    SystemClock.sleep(1000);
                    if (mLocUtil.getLocation(loc) == -1)
                        continue;
                    currentAccuracy = loc.getAccuracy();
                    currentLatitude = loc.getLatitude();
                    currentLongitude = loc.getLongitude();
                    Log.v("SearchListActivity", "Accuracy " + currentAccuracy + " cnt " + cnt);
                    if (currentAccuracy < 100) {
                        break;
                    }
                    cnt += 1;
                }

                mLocUtil.endRequestLocation();

                msg = new Message();
                if (cnt < LOCATE_TIMES) {
                    msg.what = LOCATE_GREEN_SUCCESS;
                }
                else if (cnt == LOCATE_TIMES) {
                    msg.what = LOCATE_YELLOW_SUCCESS;
                }
                Bundle bun = loc.getExtras();
                msg.setData(bun);
                mHandler.sendMessage(msg);
            }
        }.start();

    }

    public void onClickReloc(View v) {
        Log.v("SearchListActivity", "Click Reloc");

        WifiManager wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        LocationManager alm =
                (LocationManager)this.getSystemService( Context.LOCATION_SERVICE );
        if( !alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER )
                && !wm.isWifiEnabled()) {
            Dialog alertDialog = new AlertDialog.Builder(this).
                    setTitle("提示").
                    setMessage("请打开GPS或者无线网络开关以提高定位的准确度").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置确定的按键
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do something
                            mRunFlag = true;
                        }
                    }).
                    create();
            alertDialog.show();
//            initPopWindow(mContext, v);
        }
        else
            mRunFlag = true;

        SearchBottomBar.setSearchBottomBar(mContext, "当前位置：", R.drawable.circle_red);

        runLocateThread();
    }

    public void onClickMap(View v) {
        Bundle bun = new Bundle();
        Intent it = new Intent();
        Location loc = new Location("");
        if (mLocUtil.getLocation(loc) != -1) {
            bun.putDouble("Lat", loc.getLatitude());
            bun.putDouble("Lng", loc.getLongitude());
            bun.putFloat("Accu", loc.getAccuracy());
        }
        it.putExtras(bun);
        it.setClass(this.mContext, BaiduMapActivity.class);
        startActivity(it);
    }
}
