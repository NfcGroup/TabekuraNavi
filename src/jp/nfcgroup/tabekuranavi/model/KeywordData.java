package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.model.vo.TagVO;

public class KeywordData {

	private static KeywordData keywordData = new KeywordData();
	private ArrayList<TagVO> _keywords;
	
	private KeywordData() {
		_keywords = new ArrayList<TagVO>();
	}
	
	public static final KeywordData getInstance() {
		return keywordData;
	}
	
	public void addKeyword(TagVO tvo) {
		_keywords.add(tvo);
	}
	
	public void deleteKeyword(int index) {
		_keywords.remove(index);
	}
	
	public void clearKeyword() {
		_keywords.clear();
	}
	
	public ArrayList<TagVO> getKeywords() {
		return _keywords;
	}
}
