package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.model.database.TabekuraDatabase;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseIntArray;

public class StoreFinder implements Parcelable {
    @SuppressWarnings("unused")
    private static final String TAG = StoreFinder.class.getSimpleName();
	
	private Context mContext;
	private KeywordData mKeyword;
	private TabekuraDatabase mDatabase;
	private ArrayList<StoreVO> mStores;
	private SparseIntArray mStoresInfo;
	
	@SuppressWarnings("unused")
	private static final String[] DUMMY_TAG_DATA = {
		" ご飯", "アルコール", "中華", "ジャマイカ", "2", "6"
	};
	
	public StoreFinder(Context context) {
		mContext = context;
		mKeyword = KeywordData.getInstance();
		mDatabase = new TabekuraDatabase(context);
		mStores  = new ArrayList<StoreVO>();
		mStoresInfo = new SparseIntArray();
	}
	
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		//dest.writeValue(mContext);
	}
	
	public static final Parcelable.Creator<StoreFinder> CREATOR =
			new Parcelable.Creator<StoreFinder>() {
		
		public StoreFinder createFromParcel(Parcel in) {
			return new StoreFinder(in);
		}
		
		public StoreFinder[] newArray(int size) {
			return new StoreFinder[size];
		}
	};

	private StoreFinder(Parcel in) {
		//Object object = in.readValue(Context.class.getClassLoader());
		//mContext = (Context)object;
	}
	
	public boolean addKeyword(int tagId) {
		TagVO tvo = new TagVO();
		// データベースからタグ情報を取得
		Cursor cursor = mDatabase.findTag(tagId);
		if(cursor.moveToFirst()) {
			// タグ情報を作成
			tvo.id = tagId;
			tvo.name = cursor.getString(cursor.getColumnIndex("tag_name"));
			tvo.genreId = cursor.getInt(cursor.getColumnIndex("tag_genre_id"));
		} else {
			return false;
		}
		
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
		
		boolean result = checkTagInfo(tvo);
		
		if(result) {
			mKeyword.addKeyword(tvo);
		}
		
		return result;
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
	
	public ArrayList<StoreVO> getStores(boolean andFlag) {
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
		//Log.d(TAG, "listSize="+listSize);
		
		if(size == 0) {
			// 検索キーワード無しなので、全店舗返す
			Log.d(TAG, "getStores() -----------> Return all store info.");
			return storeList;
		}
		
		// データベースから該当店舗を取得
		cursor = mDatabase.findOrStores(tagIds);
		
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
						if(weightCounter > storeWeight) storeWeight = weightCounter;
						// 店舗IDと重みを記録	
						mStoresInfo.put(storeId, storeWeight);
						//Log.i(TAG, "store info put : id="+storeId+" weight="+storeWeight);
						// 重みを初期化
						storeWeight = 1;
						weightCounter = 1;
					}
				}
				
				if(cursor.isLast()) {
					if(weightCounter > storeWeight) storeWeight = weightCounter;
					// 店舗IDと重みを記録	
					mStoresInfo.put(sid, storeWeight);
					//Log.i(TAG, "store info put : id="+sid+" weight="+storeWeight);
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
			if(mStoresInfo.get(id) > 0) {
				//List表示はタグのANDをとる
				if(andFlag && mStoresInfo.get(id) < tags.size()) continue;
				// さっき計測した重みをセット
				storeList.get(i).weight = mStoresInfo.get(id);
				mStores.add(storeList.get(i));
			} else {
				//List表示は該当なし店舗は無視
				if(andFlag) continue;
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
	
	public void databaseClose() {
		mKeyword.clearKeyword();
		mDatabase.databaseClose();
	}
	
	private boolean checkTagInfo(TagVO tvo) {
		ArrayList<TagVO> tags = getKeywords();
		int size = tags.size();
		boolean check = true;
		
		for(int i = 0; i < size; i++) {
			if(tvo.id == tags.get(i).id) {
				check = false;
				break;
			}
		}
		return check;
	}
}
