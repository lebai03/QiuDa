package miscutils;

import android.content.Context;
import android.location.Location;
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

/**
 * Created by lebai on 2015/4/3.
 */
public class LocUtil {

    private static final String BD_KEY = "fiwGjzRondOHzkwYvVfVaBB0";
    private LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    private volatile boolean isFirstLocation = true;
    private volatile boolean isRequest = false;
    private double mCurrentLatitude = 0.0;
    private double mCurrentLongitude = 0.0;
    private float mCurrentAccuracy = 0;
    private Context mContext = null;

    public LocUtil(Context context) {
        SDKInitializer.initialize(context);
        mContext = context;
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

    public Location getLocation () {
        if ((isRequest == true) || (isFirstLocation == true)) {
            return null;
        }

        Location loc = new Location("");
        loc.setAccuracy(mCurrentAccuracy);
        loc.setAltitude(mCurrentLatitude);
        loc.setLongitude(mCurrentLongitude);

        return loc;
    }

    public int requestLocation() {
        isRequest = true;

        if(mLocationClient != null && mLocationClient.isStarted()){
            mLocationClient.requestLocation();
            return 0;
        }else{
            Log.d("log", "locClient is null or not started");
            return -1;
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            // map view 销毁后不在处理新接收的位置
            if (location == null)
                return;
            // 构造定位数据
            isRequest = isFirstLocation = false;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mCurrentAccuracy = location.getRadius();
            mCurrentLatitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            Log.d("LocUtil", "Listener Accuracy" + mCurrentAccuracy);
            Log.d("LocUtil", "Listener Latitude" + mCurrentLatitude);
            Log.d("LocUtil", "Listener Longitude" + mCurrentLongitude);
        }

    }
}
