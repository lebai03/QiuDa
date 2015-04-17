package com.example.lebai.qiuda;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import miscutils.CommonUtil;

public class SearchBottomBar extends Activity{
	
    public static void setSearchBottomBar(Context context, String text, int picId) {
        ListView lvSBB = (ListView) ((Activity) context)
                .findViewById(R.id.listViewLoc);

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", picId);
        map.put("ItemTitle", text);
        listItem.add(map);

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(context, listItem,
                R.layout.search_bottom_bar, new String[] { "ItemImage",
                "ItemTitle" }, new int[] { R.id.imageViewLocStatus,
                R.id.textViewLoc });

        /*
        LayoutParams lp = new LayoutParams(CommonUtil.getWidth(context)-CommonUtil.dip2px(context, 32),
                CommonUtil.dip2px(context, 32));
        lp.leftMargin = 0;
        lp.rightMargin = CommonUtil.dip2px(context, 32);
        Log.v("SearchBottomBar", "right " + lp.rightMargin);
        lp.bottomMargin = 0;
        lvSBB.setLayoutParams(lp);
        */
        lvSBB.setBackgroundColor(context.getResources().getColor(R.color.gainsboro));
        lvSBB.setAdapter(mSimpleAdapter);

        return;

    }

    /*
	public static void addTitleBar(Context context, String text) {
		ListView lvTitleBar = (ListView) ((Activity) context)
				.findViewById(R.id.listViewLoc);

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ItemImage", picId);
		map.put("ItemTitle", text);
		listItem.add(map);

		SimpleAdapter mSimpleAdapter = new SimpleAdapter(context, listItem,
				R.layout.title_bar_no_add_list, new String[] { "ItemImage",
						"ItemTitle" }, new int[] { R.id.imageViewApp,
						R.id.textViewApp });

		lvTitleBar.setAdapter(mSimpleAdapter);
		
		return;
	}

	public static ListView createTitleBar(Context context, String text, int picId) {
		ListView lvTitleBar = new ListView(context);

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ItemImage", picId);
		map.put("ItemTitle", text);
		listItem.add(map);

		SimpleAdapter mSimpleAdapter = new SimpleAdapter(context, listItem,
				R.layout.title_bar_no_add_list, new String[] { "ItemImage",
						"ItemTitle" }, new int[] { R.id.imageViewApp,
						R.id.textViewApp });

		lvTitleBar.setAdapter(mSimpleAdapter);
		
		return lvTitleBar;
	}


	public static void addTitleBarLeftArrow(Context context, String text) {
		ListView lvTitleBar = (ListView) ((Activity) context)
				.findViewById(R.id.listViewTitleBar);
	
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	
		HashMap<String, Object> map = new HashMap<String, Object>();
	//	map.put("ItemImage", picId);
		map.put("ItemTitle", text);
		listItem.add(map);
	
		SimpleAdapter mSimpleAdapter = new SimpleAdapter(context, listItem,
				R.layout.title_bar_left_arrow_list, new String[] { "ItemImage",
						"ItemTitle" }, new int[] { R.id.imageViewApp,
						R.id.textViewApp });
	
		lvTitleBar.setAdapter(mSimpleAdapter);
		
		return;
	}
    */
}
