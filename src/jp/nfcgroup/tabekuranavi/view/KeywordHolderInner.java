package jp.nfcgroup.tabekuranavi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class KeywordHolderInner extends LinearLayout {
	
	public KeywordHolderInner(Context context) {
		super(context);
	}
	
	public KeywordHolderInner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public KeywordHolderInner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		CustomScrollView parent = (CustomScrollView)this.getParent(); 
		parent.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
	}
}