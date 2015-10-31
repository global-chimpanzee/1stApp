package mokuhyouKanriApp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelperクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{


	private static final String DB_NAME = "sqlite_goalmanager.db";
	private static final int DB_VERSION = 1;

	private static final String CREATE_TABLE1 =  "create table goalInfoTable ( " +
										"GOAL_ID integer primary key autoincrement, " +
										"M_GENRE text not null," +
										"GOAL text not null," +
										"G_NUMBER real not null" +
										"G_DUE text not null" +
										"G_MEMO text " +
										"TIMESTAMP text not null" +
										");";

	private static final String CREATE_TABLE2 =  "create table achieveInfoTable ( " +
			"GOAL_ID integer not null, " +
			"A_DATE text primary key," +
			"A_NUMBER real not null," +
			"A_COMMENT text" +
			"TIMESTAMP text not null" +
			");";

	private static final String DROP_TABLE1 = "drop table goalInfoTable;";
	private static final String DROP_TABLE2 = "drop table achieveInfoTable;";

	public MySQLiteOpenHelper(Context c) {
		super(c, DB_NAME, null, DB_VERSION);
	}

	/**
	 * データベースファイル初回使用時に実行される処理クラス
	 * @param SQLLiteDatabase db
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// テーブル作成のクエリを発行
		db.execSQL(CREATE_TABLE1);
		db.execSQL(CREATE_TABLE2);
	}

	/**
	 * データベースのバージョンアップ時に実行される処理クラス
	 * @param SQLiteDatabase db
	 * @param int oldVersion
	 * @param int newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// テーブルの破棄と再作成
		db.execSQL(DROP_TABLE1);
		db.execSQL(DROP_TABLE2);
		onCreate(db);
	}

}
