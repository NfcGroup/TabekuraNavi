package jp.nfcgroup.tabekuranavi.model.database;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class TabekuraDatabase {
	
	private static final String TAG = TabekuraDatabase.class.getSimpleName();
	
	private final Context mContext;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	// タグIDで店舗を検索する
	private static final String SQL_STRING_FIND_STORES =
			//"select category_id, category_tag_id, category_shop_id " +
			"select * from category " +
			"inner join dish on category_dish_id=dish_id " +
			// WHERE句は動的に生成
			" %s " +
			"order by dish_shop_id, category_dish_id";
	// タグIDで該当するタグを検索する
	private static final String SQL_STRING_FIND_TAG =
			"select * from tag " +
			// WHERE句は動的に生成
			" %s ";
	// 全店舗の詳細情報を取得する
	private static final String SQL_STRING_ALL_STORES =
			"select * from shop inner join dish on shop_id=dish_shop_id";
	
	public TabekuraDatabase(Context context) {
		mContext = context;
		setDatabase();
	}
	
	private void setDatabase() {
		mDbHelper = new DatabaseHelper(mContext);
		try {
			//mDbHelper.createEmptyDatabase();
			mDb = mDbHelper.openTabekuraDatabase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		} catch (SQLException sqle) {
			throw sqle;
		}
	}
	
	/**
	 * 指定したタグIDの店舗一覧を取得
	 */
	private Cursor findStores(int[] tagId, boolean flag) {
		// タグIDを指定して、店舗IDでグルーピングかつソートする
		String tagCondition = "";
		int size = tagId.length;
		for(int i = 0; i < size; i++) {
			if(i == 0) {
				tagCondition = "where category_tag_id=" + tagId[0];
			} else {
				if(flag) {
					tagCondition += " AND category_tag_id=" + tagId[i];
				} else {
					tagCondition += " OR category_tag_id=" + tagId[i];
				}
			}
		}
		// SQL文を作成
		String sqlstr = String.format(SQL_STRING_FIND_STORES, tagCondition);
		Log.w(TAG, "SQL:"+sqlstr);
		Cursor cursor = mDb.rawQuery(sqlstr, null);
		return cursor;
	}

	/**
	 * 指定したタグIDの店舗一覧を取得(OR検索)
	 */
	public Cursor findOrStores(int[] tagId) {
		return findStores(tagId, false);
	}
	
	/**
	 * 指定したタグIDの店舗一覧を取得(AND検索)
	 */
	public Cursor findAndStores(int[] tagId) {
		return findStores(tagId, true);
	}
	
	/**
	 * 指定したタグIDの情報を取得
	 */
	public Cursor findTag(int tagId) {
		// SQL文を作成
		String sqlstr = String.format(SQL_STRING_FIND_TAG, "where tag_id=" + tagId);
		return mDb.rawQuery(sqlstr, null);
	}
	
	/**
	 * 全店舗の詳細情報を取得
	 */
	public Cursor fetchAllStores() {
		return mDb.rawQuery(SQL_STRING_ALL_STORES, null);
	}
}
