package com.example.lebai.qiuda;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageTextBtn extends RelativeLayout {
    
    private ImageView imgView;  
    private TextView  textView;

    public ImageTextBtn(Context context) {
        super(context, null);
        Log.v("ImageTextBtn", "One param");
    }
    
    public ImageTextBtn(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        Log.v("ImageTextBtn", "Two param");
        LayoutInflater.from(context).inflate(R.layout.image_text_button, this, true);
        
        this.imgView = (ImageView)findViewById(R.id.imgItemImage);
        this.textView = (TextView)findViewById(R.id.textItemName);
        
        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource(int resourceID) {
        this.imgView.setImageResource(resourceID);
    }
    
    public void setImgPos(int l, int t, int w, int h) {
    	LayoutParams lp = new LayoutParams(
    			w, h);
    	lp.leftMargin = l;
    	lp.topMargin = t;
    	this.imgView.setLayoutParams(lp);
    }

    public void setImgPosition(int l, int t) {
    	this.imgView.setPadding(l, t, l, t);
    }
    
    public void setText(String text) {
        this.textView.setText(text);
    }
    
    public void setTextColor(int color) {
        this.textView.setTextColor(color);
    }
    
    public void setTextSize(float size) {
        this.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }
    
    public void setTextPos(int l, int t, int w, int h) {
        LayoutParams lp = new LayoutParams(
                w, h);
        lp.leftMargin = l;
        lp.topMargin = t;
        this.textView.setLayoutParams(lp);
    }
}