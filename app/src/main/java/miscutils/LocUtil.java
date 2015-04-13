package miscutils;

import android.content.Context;
import android.location.Location;
import com.baidu.mapapi.SDKInitializer;
/**
 * Created by lebai on 2015/4/3.
 */
public class LocUtil {

    public LocUtil(Context context) {
        SDKInitializer.initialize(context);
    }

    public Location getLocation () {
        mapManager = new BMapManager(getApplication());
        mapManager.init("fiwGjzRondOHzkwYvVfVaBB0", null);
    }

}
