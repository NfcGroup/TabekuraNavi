package jp.nfcgroup.tabekuranavi.fragment;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.model.StoreColorVO;
import jp.nfcgroup.tabekuranavi.model.StoreFinder;
import jp.nfcgroup.tabekuranavi.model.StoreFinderParcelable;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import jp.nfcgroup.tabekuranavi.view.MapView;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = null;
	
    private ArrayList<StoreColorVO> mStoreColors;
	private MapView mMapView;
	private StoreFinder mStoreFinder;
	private ArrayList<StoreVO> mStoreInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 親コンテナを指定する場合はattachToRootをfalseにする
		return (ViewGroup)inflater.inflate(R.layout.fragment_map, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle args = getArguments();
        StoreFinderParcelable storeFinderParcelable = args.getParcelable("finder");
        mStoreFinder = storeFinderParcelable.getStoreFinder();
        
        initialize();
        updateViews();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	public void updateViews() {
	    //Log.d(TAG,"updateViews");
        
		mStoreInfo.clear();
	    mStoreInfo = mStoreFinder.getOrStores();
        parseStores(mStoreInfo);
        mMapView.updateColors(mStoreColors);
	}
	
	/**
	 * 事前処理
	 */
	private void initialize() {
		mStoreColors = new ArrayList<StoreColorVO>();
		mStoreInfo = new ArrayList<StoreVO>();
		mMapView = (MapView)getActivity().findViewById(R.id.mapView);
		mMapView.mParentFragment = this;
	}
	
	/**
	 * 絞込みされた店舗情報から、店舗ボタンの色一覧データを更新する
	 * @param stores
	 */
	private void parseStores(ArrayList<StoreVO> stores) {
		int size = stores.size();
		StoreColorVO storeColor;
		
		mStoreColors.clear();
		for(int i = 0; i < size; i++) {
			// 検索キーワードに該当する店舗か判定
			//Log.d(TAG,"weight="+stores.get(i).weight);
			storeColor = createStoreColor(stores.get(i).weight);
			mStoreColors.add(i, storeColor);
		}
	}
	
	/**
	 * 重みに応じて店舗ボタンの色をARGB値で生成する
	 * @param weight
	 * @return
	 */
	private StoreColorVO createStoreColor(int weight) {
		StoreColorVO scvo;
		
		switch(weight) {
		case 1:
			scvo = new StoreColorVO(255, 255, 161, 49);
			break;
		case 2:
			scvo = new StoreColorVO(255, 255, 101, 26);
			break;
		case 3:
			scvo = new StoreColorVO(255, 216, 46, 0);
			break;
		case 4:
			scvo = new StoreColorVO(255, 134, 18, 18);
			break;
		default:
			scvo = new StoreColorVO(255, 255, 188, 122);
			break;
		}
		return scvo;
	}
	/*
	public void execute(MotionEvent event) {
		
		if( event.getPointerCount() == 1){

			//ドラッグ
			switch(event.getAction() & MotionEvent.ACTION_MASK)
			{
				case MotionEvent.ACTION_DOWN:

					//ダイアログ表示
		    	    RectF[] hitRects = mMapGestureSurfaceView._shopHitRects;
		    	    int paddingTop = 200;//TODO padding調整

		    		for (int i = 0; i < hitRects.length; i++) {
		    			if(hitRects[i].contains((int)event.getX(), (int)event.getY() - paddingTop) == true){
		    				StoreDialogFragment sdialog = StoreDialogFragment.newInstance(i);
		    				sdialog.show(getFragmentManager(), "dialog");

		    				return;
		        		}
					}

		     		//ドラッグ開始
	        		mMapGestureSurfaceView.startDrag(event);

					break;

				//ドラッグ中
				case MotionEvent.ACTION_MOVE:
					mMapGestureSurfaceView.moveDrag(event, mStoreColors);
					break;

				//ドラッグ終了
				case	MotionEvent.ACTION_UP:
					mMapGestureSurfaceView.endDrag(event);
					break;
			}
		}else{
		    //ピンチイン・アウト
			switch(event.getAction() & MotionEvent.ACTION_MASK)
			{
				//ピンチ開始
				case MotionEvent.ACTION_POINTER_DOWN:
					mMapGestureSurfaceView.startPinch(event);
					break;

				//ピンチ中
				case MotionEvent.ACTION_MOVE:
					mMapGestureSurfaceView.movePinch(event, mStoreColors);
					break;

				//ピンチ終了
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					mMapGestureSurfaceView.endPinch(event);
					break;
			 }
		}
	}
	*/
	
	public void showStoreDialog(int storeId) {
		StoreDialogFragment dialog = StoreDialogFragment.newInstance(storeId, mStoreFinder);
		dialog.show(getFragmentManager(), "dialog");
	}
}
