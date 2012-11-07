package jp.nfcgroup.tabekuranavi.model.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = DatabaseHelper.class.getSimpleName();

	private final Context mContext;
	private String mDbPath;
	private SQLiteDatabase mDatabase;
	
	private static final String DB_NAME = "tabekura_database";
	private static final String DB_ASSET = "nfc.db";
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		mContext = context;
		mDbPath = context.getDatabasePath(DB_NAME).getPath();
	}
	
	/**
	 * assetに格納したデータベースをコピーするために、空のデータベースを作成する
	 */
	public void createEmptyDatabase() throws IOException {
		boolean dbExist = checkDatabaseExist();
		
		if(dbExist) {
			// データベース作成済
		} else {
			this.getReadableDatabase();
			try {
				// assetに格納したデータベースをコピーする
				copyDatabaseFromAsset();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * 再コピー防止のため、データベースの有無をチェックする
	 * 
	 * @return 存在している場合 {@code true}
	 */
	private boolean checkDatabaseExist() {
		SQLiteDatabase checkDb = null;
		
		try {
			Log.d(TAG, "Database path:"+mDbPath);
			checkDb = SQLiteDatabase.openDatabase(mDbPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// データベースは存在していない
		}
		
		if(checkDb != null) {
			checkDb.close();
		}
		
		return checkDb != null ? true : false;
	}
	
	/**
	 * assetに格納したデータベースをデフォルトの
	 * データベースパスに作成した空のデータベースにコピーする
	 */
	private void copyDatabaseFromAsset() throws IOException {
		// asset内のデータベースファイルにアクセス
		InputStream input = mContext.getAssets().open(DB_ASSET);
		// 空のDB
		OutputStream output = new FileOutputStream(mDbPath);
		
		// コピー
		byte[] buffer = new byte[1024];
		int size;
		while ((size = input.read(buffer)) > 0) {
			output.write(buffer, 0, size);
		}
		
		// クローズ
		output.flush();
		output.close();
		input.close();
	}
	
	public SQLiteDatabase openTabekuraDatabase() throws IOException {
		mDatabase = SQLiteDatabase.openDatabase(mDbPath, null, SQLiteDatabase.OPEN_READONLY);
		return mDatabase;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public synchronized void close() {
		if(mDatabase != null) {
			mDatabase.close();
		}
		super.close();
	}
}
