package miscutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class CommonUtil {

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
    /**
     * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С����
     * 
     * @param pxValue
     * @param scale
     *            ��DisplayMetrics��������density��
     * @return
     */ 
    public static int px2dip(Context context, float pxValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С����
     * 
     * @param dipValue
     * @param scale
     *            ��DisplayMetrics��������density��
     * @return
     */ 
    public static int dip2px(Context context, float dipValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * ��pxֵת��Ϊspֵ����֤���ִ�С����
     * 
     * @param pxValue
     * @param fontScale
     *            ��DisplayMetrics��������scaledDensity��
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * ��spֵת��Ϊpxֵ����֤���ִ�С����
     * 
     * @param spValue
     * @param fontScale
     *            ��DisplayMetrics��������scaledDensity��
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f); 
    }
    
    public static int getWidth(Context context) {
    	DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;    	
    }
    
    public static int getHeight(Context context) {
    	DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;    	
    }
    
    public static float getDensity(Context context) {
    	DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;    	
    }
}
