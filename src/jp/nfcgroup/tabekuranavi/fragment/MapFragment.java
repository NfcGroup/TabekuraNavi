package jp.nfcgroup.tabekuranavi.fragment;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.R;
import jp.nfcgroup.tabekuranavi.model.StoreColorVO;
import jp.nfcgroup.tabekuranavi.model.StoreFinder;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MapFragment extends Fragment {
	private StoreFinder mStoreFinder;
	private SparseArray<StoreColorVO> mStoreColors;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 親コンテナを指定する場合はattachToRootをfalseにする
		return (ViewGroup)inflater.inflate(R.layout.fragment_map, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
        mStoreFinder = new StoreFinder(getActivity().getApplicationContext());
        initialize();
        updateViews();
	}
	
	public void updateViews() {
        ArrayList<StoreVO> stores = mStoreFinder.getOrStores();
        parseStores(stores);
	}
	
	/**
	 * 事前処理
	 */
	private void initialize() {
		mStoreColors = new SparseArray<StoreColorVO>();
		
		Button dialogButton = (Button)getActivity().findViewById(R.id.button_dialog);
		dialogButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				StoreDialogFragment sdialog = StoreDialogFragment.newInstance(16);
				sdialog.show(getFragmentManager(), "dialog");
			}
		});
	}
	
	/**
	 * 絞込みされた店舗情報から、店舗ボタンの色一覧データを更新する
	 * @param stores
	 */
	private void parseStores(ArrayList<StoreVO> stores) {
		int size = mStoreColors.size();
		int storesCounter = 0;
		StoreColorVO storeColor;
		
		for(int i = 0; i < size; i++) {
			// 検索キーワードに該当する店舗か判定
			if(i == stores.get(storesCounter).id) {
				storeColor = createStoreColor(stores.get(storesCounter).weight);
				storesCounter++;
			} else {
				storeColor = createStoreColor(0);
			}
			mStoreColors.put(i, storeColor);
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
}
