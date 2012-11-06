package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.model.vo.StoreVO;
import jp.nfcgroup.tabekuranavi.model.vo.TagVO;
import android.util.SparseArray;

public class StoreFinder {
	
	private KeywordData _keywordData;
	
	public StoreFinder() {
		_keywordData = KeywordData.getInstance();
	}
	
	public void addKeyword(int tagId) {
		TagVO tvo = new TagVO();
		tvo.tagId = tagId;
		_keywordData.addKeyword(tvo);
	}

	public void clearKeyword() {
		_keywordData.clearKeyword();
	}
	
	public SparseArray<StoreVO> getStores() {
		SparseArray<StoreVO> stores = new SparseArray<StoreVO>(3);
		ArrayList<StoreVO> sl = TestModel.getTestStores();
		stores.put(0, sl.get(0));
		stores.put(1, sl.get(1));
		stores.put(2, sl.get(2));
		return stores;
	}
	
	public ArrayList<TagVO> getKeywords() {
		return _keywordData.getKeywords();
	}
}
