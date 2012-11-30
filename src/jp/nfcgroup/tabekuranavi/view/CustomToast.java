package jp.nfcgroup.tabekuranavi.view;

import jp.nfcgroup.tabekuranavi.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {
	private TextView mTextView;

	public CustomToast(Activity activity) {
       	super(activity.getApplicationContext());
	}
	
	public CustomToast(Activity activity, int resId) {
		super(activity.getApplicationContext());
		
		//カスタムビューを適用
       	LayoutInflater inflater = activity.getLayoutInflater();
       	View view = inflater.inflate(resId, null);
       	setView(view);
       	
       	mTextView = (TextView)view.findViewById(R.id.toast_text);
	}
	
	public void setCustomText(String message) {
		if(mTextView != null) {
			mTextView.setText(message);
		}
	}
}
