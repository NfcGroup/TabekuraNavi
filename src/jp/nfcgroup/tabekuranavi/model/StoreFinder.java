package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.model.database.TabekuraDatabase;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

public class StoreFinder {
    
    private static final String TAG = StoreFinder.class.getSimpleName();
	
	private Context mContext;
	private KeywordData mKeyword;
	private TabekuraDatabase mDatabase;
	private ArrayList<StoreVO> mStores;
	private SparseArray<Integer> mStoresInfo;
	private TagVO mTvo;
	
	@SuppressWarnings("unused")
	private static final String[] DUMMY_TAG_DATA = {
		" ご飯", "アルコール", "中華", "ジャマイカ", "2", "6"
	};
	
	public StoreFinder(Context context) {
		mContext = context;
		mKeyword = KeywordData.getInstance();
		mDatabase = new TabekuraDatabase(context);
		mStores  = new ArrayList<StoreVO>();
		mStoresInfo = new SparseArray<Integer>();
		mTvo = new TagVO();
	}
	
	public void addKeyword(int tagId) {
		// データベースからタグ情報を取得
		Cursor cursor = mDatabase.findTag(tagId);
		cursor.moveToFirst();
		
		// タグ情報を作成
		mTvo.id = tagId;
		mTvo.name = cursor.getString(cursor.getColumnIndex("tag_name"));
		mTvo.genreId = cursor.getInt(cursor.getColumnIndex("tag_genre_id"));
		
		cursor.close();
		// テストデータ
		/*
		int id = 1;
		if(tagId > 0 && 6 > tagId) {
			id = tagId;
		}
		tvo.id = id;
		tvo.name = DUMMY_TAG_DATA[id-1];
		tvo.genreId = id / 2;
		*/
		
		mKeyword.addKeyword(mTvo);
	}

	public void deleteKeyword(int tagId) {
		ArrayList<TagVO> tags = getKeywords();
		int size = tags.size();
		int arrayIndex = 0;
		
		for(int i = 0; i < size; i++) {
			if(tagId == tags.get(i).id) {
				arrayIndex = i;
				break;
			}
		}
		mKeyword.deleteKeyword(arrayIndex);
	}
	
	public void clearKeyword() {
		mKeyword.clearKeyword();
	}
	
	public ArrayList<StoreVO> getAndStores() {
		return getStores(true);
	}
	
	public ArrayList<StoreVO> getOrStores() {
		return getStores(false);
	}
	
	public ArrayList<StoreVO> getStores(boolean flag) {
		int storeId = 0;
		int sid = 0;
		int dishId = 0;
		int did = 0;
		int storeWeight = 1;
		int weightCounter = 1;
		Cursor cursor;
		
		// タグIDを取得
		ArrayList<TagVO> tags = mKeyword.getKeywords();
		//for debug
		int size = tags.size();
		int[] tagIds = new int[size];
		//test data
		//int size = 1;
		//int[] tagIds = new int[1];
		//tagIds[0] = 15;
		//test data
		for(int i = 0; i < size; i++) {
			tagIds[i] = tags.get(i).id;
		}
		//for debug
		
		// 店舗情報を初期化
		mStores.clear();

		// 全店舗の初期情報を取得
		StoresData sdat = StoresData.getInstance();
		ArrayList<StoreVO> storeList = sdat.getAllStore(mContext);
		int listSize = storeList.size();
		Log.d(TAG, "listSize="+listSize);
		
		if(size == 0) {
			// 検索キーワード無しなので、全店舗返す
			return storeList;
		}
		
		// データベースから該当店舗を取得
		if(flag) {
			cursor = mDatabase.findAndStores(tagIds);
		} else {
			cursor = mDatabase.findOrStores(tagIds);
		}
		
		// 店舗情報を作成
		mStoresInfo.clear();
		if(cursor.moveToFirst()) {
			do {
				// 該当する店舗IDの記憶と重みの計算
				// 店舗情報をデータベースから取得
				sid = cursor.getInt(cursor.getColumnIndex("dish_shop_id"));
				did = cursor.getInt(cursor.getColumnIndex("dish_id"));
				//Log.i(TAG, "shop_id:"+sid+" dish_id:"+did);
				if(storeId == sid) {
					// 重みの計測
					if(dishId == did) {
						// 重みを加算
						weightCounter++;
					} else {
						if(weightCounter > storeWeight) storeWeight = weightCounter;
						// 重みカウンター初期化
						weightCounter = 1;
					}
				} else {
					if(!cursor.isFirst()) {
						// 店舗IDと重みを記録	
						mStoresInfo.put(storeId, storeWeight);
						//Log.i(TAG, "store info put : id="+storeId+" weight="+storeWeight);
						// 重みを初期化
						storeWeight = 1;
					}
				}
				
				// 商品IDを退避
				dishId = did;
				// 店舗IDを退避
				storeId = sid;
			} while(cursor.moveToNext());
			
		}
		cursor.close();
		
		// タグに該当する店舗のみ戻り値に登録
		for(int i = 0; i < listSize; i++) {
			int id = storeList.get(i).id;
			if(mStoresInfo.get(id) != null) {
				// さっき計測した重みをセット
				storeList.get(i).weight = mStoresInfo.get(id);
				mStores.add(storeList.get(i));
			}
		}
		
		return mStores;
	}
	
	public ArrayList<TagVO> getKeywords() {
		return mKeyword.getKeywords();
	}
	
	public TagVO getKeyword(int tagId) {
		ArrayList<TagVO> tags = getKeywords();
		int size = tags.size();
		int arrayIndex = 0;
		
		for(int i = 0; i < size; i++) {
			if(tagId == tags.get(i).id) {
				arrayIndex = i;
				break;
			}
		}
		return tags.get(arrayIndex);
	}
}
