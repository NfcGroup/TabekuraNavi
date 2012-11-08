package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.model.database.TabekuraDatabase;
import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;
import android.content.Context;
import android.util.SparseArray;

public class StoreFinder {
	
	private KeywordData mKeyword;
	private TabekuraDatabase mDatabase;
	
	private static final String[] DUMMY_TAG_DATA = {
		" ご飯", "アルコール", "中華", "ジャマイカ", "2", "6"
	};
	
	public StoreFinder(Context context) {
		mKeyword = KeywordData.getInstance();
		//mDatabase = new TabekuraDatabase(context);
	}
	
	public void addKeyword(int tagId) {
		// データベースからタグ情報を取得
		
		// タグ情報を作成
		TagVO tvo = new TagVO();
		/* 修正中
		tvo.tagId = tagId;
		tvo.tagName = "";
		*/
		
		// テストデータ
		int id = 1;
		if(tagId > 0 && 6 > tagId) {
			id = tagId;
		}
		tvo.id = id;
		tvo.name = DUMMY_TAG_DATA[id-1];
		tvo.genreId = id / 2;
		
		mKeyword.addKeyword(tvo);
	}

	public void clearKeyword() {
		mKeyword.clearKeyword();
	}
	
	public SparseArray<StoreVO> getStores() {
		/* 修正中
		SparseArray<StoreVO> stores;
		
		// タグIDを取得
		ArrayList<TagVO> tags = mKeyword.getKeywords();
		int size = tags.size();
		int[] tagIds = new int[size];
		for(int i = 0; i < size; i++) {
			tagIds[i] = tags.get(i).id;
		}
		
		// データベースから該当店舗を取得
		mDatabase.findStores(tagIds);
		
		// 店舗情報を作成
		
		return stores;
		*/
		return null;
	}
	
	public ArrayList<TagVO> getKeywords() {
		return mKeyword.getKeywords();
	}
}
