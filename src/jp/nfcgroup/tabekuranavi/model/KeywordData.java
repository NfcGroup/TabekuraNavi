package jp.nfcgroup.tabekuranavi.model;

import java.util.ArrayList;

import jp.nfcgroup.tabekuranavi.model.vo.TagVO;

public class KeywordData {

	private static KeywordData keywordData = new KeywordData();
	private ArrayList<TagVO> _keyword;
	
	private KeywordData() {
		super();
	}
	
	public static final KeywordData getInstance() {
		return keywordData;
	}
	
	public void addKeyword(TagVO tvo) {
		_keyword.add(tvo);
	}
	
	public void clearKeyword() {
		_keyword.clear();
	}
	
	public ArrayList<TagVO> getKeywords() {
		return _keyword;
	}
}
