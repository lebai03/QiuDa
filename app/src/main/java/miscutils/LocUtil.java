package miscutils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * Created by lebai on 2015/4/3.
 */
public class LocUtil {

    public MyLocationListener mMyLocationListener;
    private LocationClient mLocationClient;
    private static volatile boolean isFirstLocation = true;
    private volatile boolean isRequest = false;
    private static double mCurrentLatitude = 0.0;
    private static double mCurrentLongitude = 0.0;
    private static float mCurrentAccuracy = 0;
    private static String mCurrentAddress = "";
    private Context mContext = null;
    private GeoCoder mGC = null;

    public LocUtil(Context context) {
        mContext = context;
        mGC = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Log.e("LocUtil", "Can't get Address");
                }
                else {

                    if (result.getAddress() != null) {
                        mCurrentAddress = result.getAddress();
                        Log.v("LocUtil", "Address " + mCurrentAddress);
                    } else
                        Log.e("LocUtil", "Address is null in onGetReverseGeoCodeResult");
                }
            }
            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                }
            }
        };
        mGC.setOnGetGeoCodeResultListener(listener);

        initMyLocation();
    }

    private void initMyLocation()
    {
        // 定位初始化
        mLocationClient = new LocationClient(mContext);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);

        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    public boolean isFirstLocation() {
        return isFirstLocation;
    }

    public int getLocation (Location loc) {
        if (isRequest || isFirstLocation) {
            return -1;
        }

        Bundle bun = new Bundle();
        loc.setAccuracy(mCurrentAccuracy);
        loc.setLatitude(mCurrentLatitude);
        loc.setLongitude(mCurrentLongitude);
        bun.putString("address", mCurrentAddress);
        loc.setExtras(bun);

        return 0;
    }

    public int startRequestLocation() {
        mLocationClient.start();
        isRequest = true;

        if(mLocationClient != null){
            int cnt = 0;
            while (!mLocationClient.isStarted() && (cnt < 3)){
                SystemClock.sleep(200);
                cnt += 1;
            }
            if (cnt == 3)
                return -1;

            mLocationClient.requestLocation();

            return 0;
        }else{
            Log.d("LocUtil", "locClient is null or not started");
            return -1;
        }
    }

    public int endRequestLocation() {

        mLocationClient.stop();

        return 0;
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            Log.d("LocUtil", "Location Receive");
            // map view 销毁后不在处理新接收的位置
            if (location == null)
                return;
            // 构造定位数据
            isRequest = isFirstLocation = false;
            mCurrentAccuracy = location.getRadius();
            mCurrentLatitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            Log.d("LocUtil", "Listener Accuracy " + mCurrentAccuracy);
            Log.d("LocUtil", "Listener Latitude " + mCurrentLatitude);
            Log.d("LocUtil", "Listener Longitude " + mCurrentLongitude);
            Log.d("LocUtil", "Listener Provider " + location.getLocType());
            Log.d("LocUtil", "Listener hasAddr " + location.hasAddr());
            Log.d("LocUtil", "Listener City " + location.getCity());
            Log.d("LocUtil", "Listener District " + location.getDistrict());
            Log.d("LocUtil", "Listener Street " + location.getStreet());
            Log.d("LocUtil", "Listener Street No. " + location.getStreetNumber());
            Log.d("LocUtil", "Listener Address " + location.getAddrStr());
            LatLng latlng = new LatLng(mCurrentLatitude, mCurrentLongitude);
            mGC.reverseGeoCode(new ReverseGeoCodeOption().location(latlng));
        }

    }
}
