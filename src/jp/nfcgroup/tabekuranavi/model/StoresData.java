package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.model.database.TabekuraDatabase;
import jp.nfcgroup.tabekuranavi.model.vo.DishVO;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class StoresData {
	private static final String TAG = StoresData.class.getSimpleName();

	private static StoresData mStoresData = new StoresData();
	private static boolean mInitFlag = false;
	
	private ArrayList<StoreVO> mStores = new ArrayList<StoreVO>();
	
	private StoresData() {
	}
	
	/**
	 * インスタンス取得
	 * @return
	 */
	public static final StoresData getInstance() {
		return mStoresData;
	}
	
	/**
	 * 全店舗および商品の情報を取得
	 * @param context
	 * @return
	 */
	public ArrayList<StoreVO> getAllStore(Context context) {
		Log.d(TAG, "store list size -> "+mStores.size());
		if(!mInitFlag) initialize(context);
		
		//for debug
		Log.w(TAG, "VVVVVVV for debug VVVVVVVV");
		int size = mStores.size();
		for(int i = 0; i < size; i++) {
			StoreVO vo = mStores.get(i);
			Log.i(TAG, String.format("i:%d id:%d name:%s sub:%s weight:%d",
					i, vo.id, vo.name, vo.subTitle, vo.weight));
		}
		//for debug
		
		return mStores;
	}
	
	/**
	 * データベースから店舗情報を作成する
	 * @param context
	 */
	private void initialize(Context context) {
		StoreVO svo = new StoreVO();
		DishVO dvo = new DishVO();
		int storeId = 0;
		
		// データベースから店舗情報を取得
		TabekuraDatabase db = new TabekuraDatabase(context);
		Cursor c = db.fetchAllStores();
		
		// 店舗情報を初期化
		mStores.clear();
		svo.id = 0;
		
		if(c.moveToFirst()) {
			do {
				// 店舗IDを取得
				storeId = c.getInt(c.getColumnIndex("shop_id"));
				
				// 店舗情報を作成
				if(svo.id != storeId) {
					if(!c.isFirst()) {
						mStores.add(svo);
						svo = new StoreVO();
					}
					svo.id = storeId;
					svo.name = c.getString(c.getColumnIndex("shop_name"));
					String str = c.getString(c.getColumnIndex("shop_subtitle"));
					svo.subTitle = (str != null) ? str : " ";
					svo.weight = 0;
					Log.i(TAG, String.format("id:%d name:%s sub:%s weight:%d",
							svo.id, svo.name, svo.subTitle, svo.weight));
					
					svo.dishes.clear();
					dvo.id = c.getInt(c.getColumnIndex("dish_id"));
					dvo.name = c.getString(c.getColumnIndex("dish_name"));
					dvo.priceTo = c.getInt(c.getColumnIndex("dish_price_to"));
					dvo.priceFrom = c.getInt(c.getColumnIndex("dish_price_from"));
					dvo.price = dvo.priceFrom;
					svo.dishes.add(dvo);
					dvo = new DishVO();
				} else {
					dvo.id = c.getInt(c.getColumnIndex("dish_id"));
					dvo.name = c.getString(c.getColumnIndex("dish_name"));
					dvo.priceTo = c.getInt(c.getColumnIndex("dish_price_to"));
					dvo.priceFrom = c.getInt(c.getColumnIndex("dish_price_from"));
					dvo.price = dvo.priceFrom;
					svo.dishes.add(dvo);
					dvo = new DishVO();
				}
				
				if(c.isLast()) {
					mStores.add(svo);
				}
			} while(c.moveToNext());
			
			// 店舗情報の作成済フラグをセット
			mInitFlag = true;
		}
	}
}
