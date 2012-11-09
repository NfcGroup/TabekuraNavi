package jp.nfcgroup.tabekuranavi.model.listener;

import java.util.EventListener;


public interface OnDataErrorListener extends EventListener {

	// データベースのオープン失敗のエラー通知
	public void onFailedOpenDatabase();
	
}
