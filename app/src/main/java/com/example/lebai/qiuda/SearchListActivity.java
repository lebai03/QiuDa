package com.example.lebai.qiuda;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import miscutils.CommonUtil;


public class SearchListActivity extends ActionBarActivity {

    private Context mContext = null;
    private SearchSpinnerAdapter adapterDistance;
    private SearchSpinnerAdapter adapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Bundle bun = new Bundle();
        bun = this.getIntent().getExtras();
        int index = bun.getInt("index");

        mContext = this;
        int screenWidth = CommonUtil.getWidth(mContext);
        int screenHeight = CommonUtil.getHeight(mContext);
        float screenDensity = CommonUtil.getDensity(mContext);

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


        SearchBottomBar.createSearchBottomBar(mContext, "您的当前位置：", R.drawable.circle_yellow);
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
}
