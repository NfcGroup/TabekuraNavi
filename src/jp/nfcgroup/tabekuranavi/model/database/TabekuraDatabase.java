package jp.nfcgroup.tabekuranavi.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class TabekuraDatabase {
	
	private SQLiteDatabase mDb;

	public TabekuraDatabase(Context context) {
		DatabaseHelper helper = new DatabaseHelper(context);
		mDb = helper.getReadableDatabase();
	}

}
