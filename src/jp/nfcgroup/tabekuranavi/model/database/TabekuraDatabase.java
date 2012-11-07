package jp.nfcgroup.tabekuranavi.model.database;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class TabekuraDatabase {
	
	private final Context mContext;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	// タグIDで店舗を検索する
	private static final String SQL_STRING_FIND_STORES =
			"select category_id, category_tag_id, category_shop_id " +
			"from category " +
			// WHERE句は動的に生成
			"where %s " +
			"group by category_shop_id " +
			"order by category_shop_id";
	// タグIDで該当するタグを検索する
	private static final String SQL_STRING_WHERE_AND =
			"select catecory_id, category_tag_id, category_shop_id " +
			"from tag " +
			// WHERE句は動的に生成
			"where %s " +
			"group by category_shop_id " +
			"order by category_shop_id";
	
	public TabekuraDatabase(Context context) {
		mContext = context;
		setDatabase();
	}
	
	private void setDatabase() {
		mDbHelper = new DatabaseHelper(mContext);
		try {
			mDbHelper.createEmptyDatabase();
			mDb = mDbHelper.openTabekuraDatabase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		} catch (SQLException sqle) {
			throw sqle;
		}
	}
	
	private static final String TABLE_NAME_CATEGORY = "category_table";
	private static final String[] COLUMNS = {
		"category_id", "category_tag_id", "category_shop_id"
	};
	
	/**
	 * 指定したタグIDの店舗一覧を取得
	 */
	private Cursor findStores(int[] tagId, boolean flag) {
		// タグIDを指定して、店舗IDでグルーピングかつソートする
		String tagCondition = "";
		int size = tagId.length;
		for(int i = 0; i < size; i++) {
			if(i == 0) {
				tagCondition = "category_tag_id=" + tagId[0];
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
}
