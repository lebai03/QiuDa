package com.example.lebai.qiuda;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class BaiduMapActivity extends ActionBarActivity {

    private final String TAG = "BaiduMapActivity";
    private MapView mBmapView;
    private BaiduMap mBmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);

        mBmapView = (MapView)findViewById(R.id.bmap_View);
        mBmap = mBmapView.getMap();

        Bundle bun = this.getIntent().getExtras();
        double curLat = bun.getDouble("Lat");
        double curLng = bun.getDouble("Lng");
        float curAccu = bun.getFloat("Accu");
        Log.v(TAG, "Lat " + curLat + " Lng " + curLng + " Accu " + curAccu);

        /*
        //定义Maker坐标点
        LatLng point = new LatLng(curLat, curLng);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.map_red_32);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBmap.addOverlay(option);
        */

        // 开启定位图层
        mBmap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(curAccu)
                .latitude(curLat)
                .longitude(curLng).build();
        // 设置定位数据
        mBmap.setMyLocationData(locData);

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        mBmap.setMyLocationConfigeration(config);

        mBmap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
        // 当不需要定位图层时关闭定位图层
        //mBmap.setMyLocationEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baidu_map, menu);
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

    @Override
    protected void onResume() {
        super.onResume();

        mBmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBmapView.onPause();
    }
}
