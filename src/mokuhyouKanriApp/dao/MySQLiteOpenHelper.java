package mokuhyouKanriApp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelperクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since 2015
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	/** データベース名 */
	public static final String DB_NAME = "sqlite_goalmanager.db";

	/** データベースバージョン */
	public static final int DB_VERSION = 1;

	/** 目標情報テーブル */
	public static final String GOAL_INFO_TABLE = "goal_info";

	/** 日次実績テーブル */
	public static final String DAY_ACHIEVE_TABLE = "day_achieve";

	/** 目標IDカラム（目標情報テーブル） */
	public static final String GOAL_ID = "goal_id";

	/** 目標ジャンルカラム（目標情報テーブル） */
	public static final String M_GENRE = "m_genre";

	/** 目標カラム（目標情報テーブル） */
	public static final String GOAL = "goal";

	/** 目標数カラム（目標情報テーブル） */
	public static final String G_NUMBER = "g_number";

	/** 達成期限カラム（目標情報テーブル） */
	public static final String G_DUE = "g_due";

	/** メモカラム（目標情報テーブル） */
	public static final String G_MEMO = "g_memo";

	/** タイムスタンプカラム（目標情報テーブル） */
	public static final String G_TIMESTAMP = "g_timestamp";

	/** 実績年月日カラム（日次実績テーブル） */
	public static final String A_DATE = "a_date";

	/** 目標IDカラム（日次実績テーブル） */
	public static final String A_GOAL_ID = "a_goal_id";

	/** 達成数カラム（日次実績テーブル） */
	public static final String A_NUMBER = "a_number";

	/** コメントカラム（日次実績テーブル） */
	public static final String A_COMMENT = "a_comment";

	/** タイムスタンプカラム（日次実績テーブル） */
	public static final String A_TIMESTAMP = "a_timestamp";

	/**
	 * コンストラクタ
	 *
	 * @param c コンテキスト
	 */
	public MySQLiteOpenHelper(Context c) {
		super(c, DB_NAME, null, DB_VERSION);
	}

	/**
	 * データベースファイル初回使用時に実行されるテーブル作成メソッド
	 *
	 * @param db SQLiteDatabaseインスタンス
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		// 目標情報テーブルの作成
		StringBuilder createGoalInfo = new StringBuilder();
		createGoalInfo.append("create table " + GOAL_INFO_TABLE + "(");
		createGoalInfo.append(GOAL_ID + " integer primary key autoincrement,");
		createGoalInfo.append(M_GENRE + " text not null,");
		createGoalInfo.append(GOAL + " text not null,");
		createGoalInfo.append(G_NUMBER + " integer not null,");
		createGoalInfo.append(G_DUE + " text not null,");
		createGoalInfo.append(G_MEMO + " text,");
		createGoalInfo.append(G_TIMESTAMP + " text not null);");
		db.execSQL(createGoalInfo.toString());

		// 日次実績テーブルの作成
		StringBuilder createDayAchieve = new StringBuilder();
		createDayAchieve.append("create table " + DAY_ACHIEVE_TABLE + "(");
		createDayAchieve.append(A_DATE + " text primary key,");
		createDayAchieve.append(A_GOAL_ID + " integer not  null,");
		createDayAchieve.append(A_NUMBER + " integer not null,");
		createDayAchieve.append(A_COMMENT + " text,");
		createDayAchieve.append(A_TIMESTAMP + " text not null);");
		db.execSQL(createDayAchieve.toString());

	}

	/**
	 * データベースのバージョンアップ時に実行される、テーブル削除・再作成メソッド
	 *
	 * @param db SQLiteDatabaseインスタンス
	 * @param oldVersion 旧バージョン
	 * @param newVersion 新バージョン
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// 目標情報テーブルの削除
		String dropGoalInfo = "drop table " + GOAL_INFO_TABLE;
		db.execSQL(dropGoalInfo);

		// 日次実績テーブルの削除
		String dropDayAchieve = "drop table " + DAY_ACHIEVE_TABLE;
		db.execSQL(dropDayAchieve);

		// テーブルの再作成
		onCreate(db);

	}

}
