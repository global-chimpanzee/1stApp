package mokuhyouKanriApp.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mokuhyouKanriApp.bean.DateDisplayObject;
import mokuhyouKanriApp.bean.EditAchieveBean;
import mokuhyouKanriApp.bean.AchieveJohoBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 日次実績テーブルを操作するDAOクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class AchieveDAO {

	/**
	 * データの追加（INSERT）
	 * @param c コンテキスト
	 * @param values 登録値
	 * @return DB処理結果
	 */
	public static boolean insert(Context c, ContentValues values) {

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// タイムスタンプの値を設定
		setTimeStamp(values);

		// INSERTを実行
		long result = db.insert(MySQLiteOpenHelper.DAY_ACHIEVE_TABLE, "", values);

		// DBクローズ処理
		db.close();

		// 処理結果返却
		if(result != -1){

			return true;

		} else {

			return false;

		}

	}

	/**
	 * データの更新（UPDATE）
	 *
	 * @param c コンテキスト
	 * @param values 更新値
	 * @param achieveDate 実績年月日（検索条件）
	 * @return DB処理結果
	 */
	public static boolean update(Context c, ContentValues values, String achieveDate) {

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// タイムスタンプの値を設定
		setTimeStamp(values);

		// where句の作成
		String whereClause = MySQLiteOpenHelper.A_DATE + " = '" + achieveDate + "'";

		// UPDATEを実行
		int result = db.update(MySQLiteOpenHelper.DAY_ACHIEVE_TABLE, values, whereClause, null);

		// DBクローズ処理
		db.close();

		// 処理結果返却
		if(result == 1){

			return true;

		} else {

			return false;

		}

	}


	/**
	 * データの全件検索（SELECT）
	 *
	 * @param c コンテキスト
	 */
	public static List<AchieveJohoBean> selectAllDatas(Context c) {

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		//検索結果を格納するリストを生成
		List<AchieveJohoBean> tableDataList = new ArrayList<AchieveJohoBean>();

		// 取得するカラム名の配列を生成
		String[] columns = new String[]{MySQLiteOpenHelper.A_DATE, MySQLiteOpenHelper.A_GOAL_ID, MySQLiteOpenHelper.A_NUMBER, MySQLiteOpenHelper.A_COMMENT};

		// SELECTの実行
		Cursor cursor = db.query(MySQLiteOpenHelper.DAY_ACHIEVE_TABLE, columns, null, null, null, null, MySQLiteOpenHelper.A_DATE + " desc", null);

		// 1番目のデータを参照
		boolean isEof = cursor.moveToFirst();

		// 検索結果をリストに格納
		while(isEof) {

			// 各カラムの値を取得
			String aDate = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.A_DATE));
			int aGoalId  = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.A_GOAL_ID));
			int aNumber = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.A_NUMBER));
			String aComment = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.A_COMMENT));

			// ビーンに値をセット
			AchieveJohoBean bean = new AchieveJohoBean();
			bean.setaDate(aDate);
			bean.setaGoalId(aGoalId);
			bean.setaNumber(aNumber);
			bean.setaComment(aComment);

			// リストに追加
			tableDataList.add(bean);

			// 次のデータ参照へ遷移
			isEof = cursor.moveToNext();

		}

		// Cursorクローズ処理
		cursor.close();

		// DBクローズ処理
		db.close();

		// リストを返却
		return tableDataList;

	}

	/**
	 * ひと月分の実績年月日と達成数を取得（SELECT）
	 *
	 * @param c コンテキスト
	 * @param targetMonth 検索対象月
	 * @return 検索結果
	 */
	public static List<DateDisplayObject> selectMonthDatas(Context c, String targetMonth){

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		//検索結果を格納するリストを生成
		List<DateDisplayObject> tableDataList = new ArrayList<DateDisplayObject>();

		// 取得するカラム名の配列を生成
		String[] columns = new String[]{MySQLiteOpenHelper.A_DATE, MySQLiteOpenHelper.A_NUMBER};

		// where句の作成
		String whereClause = MySQLiteOpenHelper.A_DATE + " like '" + targetMonth + "%'";

		// SELECTの実行
		Cursor cursor = db.query(MySQLiteOpenHelper.DAY_ACHIEVE_TABLE, columns, whereClause, null, null, null, null, null);

		// 1番目のデータを参照
		boolean isEof = cursor.moveToFirst();

		// 検索結果をリストに格納
		while(isEof) {

			// 各カラムの値を取得
			String aDate = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.A_DATE));
			int aNumber  = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.A_NUMBER));

			// ビーンに値をセット
			DateDisplayObject bean = new DateDisplayObject();
			bean.setaDate(aDate);
			bean.setaNumber(aNumber);

			// リストに追加
			tableDataList.add(bean);

			// 次のデータ参照へ遷移
			isEof = cursor.moveToNext();

		}

		// Cursorクローズ処理
		cursor.close();

		// DBクローズ処理
		db.close();

		// リストを返却
		return tableDataList;

	}

	/**
	 * 実績編集画面で利用する達成数とコメントを検索（SELECT）
	 *
	 * @param c コンテキスト
	 * @param targetDate 検索対象日
	 * @return 検索結果
	 */
	public static EditAchieveBean selectForEditAchieve(Context c, String targetDate){

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// 取得するカラム名の配列を生成
		String[] columns = new String[]{MySQLiteOpenHelper.A_NUMBER, MySQLiteOpenHelper.A_COMMENT};

		// where句の作成
		String whereClause = MySQLiteOpenHelper.A_DATE + " = '" + targetDate + "'";

		// SELECTの実行
		Cursor cursor = db.query(MySQLiteOpenHelper.DAY_ACHIEVE_TABLE, columns, whereClause, null, null, null, null, null);

		// 1番目のデータを参照
		boolean isEof = cursor.moveToFirst();

		// 検索結果をリストに格納
		EditAchieveBean bean = null;
		if(isEof) {

			// 各カラムの値を取得
			int aNumber = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.A_NUMBER));
			String aComment  = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.A_COMMENT));

			// ビーンに値をセット
			bean = new EditAchieveBean();
			bean.setaNumber(aNumber);
			bean.setaComment(aComment);

		}

		// Cursorクローズ処理
		cursor.close();

		// DBクローズ処理
		db.close();

		// リストを返却
		return bean;

	}

	/**
	 * 達成数の合計値を取得（SELECT）
	 *
	 * @param c コンテキスト
	 * @param goalId 目標ID（検索条件）
	 * @return 達成数の合計値
	 */
	public static int getSumOfAchieveNum(Context c, int goalId){

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// 検索クエリを作成
		String sql = "select sum(" + MySQLiteOpenHelper.A_NUMBER + ") from " + MySQLiteOpenHelper.DAY_ACHIEVE_TABLE + " where " + MySQLiteOpenHelper.A_GOAL_ID + " = ?";

		// SELECTの実行
		Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(goalId)});

		// 検索結果をリストに格納
		int sum = 0;
		if(cursor.moveToNext()) {

			// 達成数の合計値を取得
			sum = cursor.getInt(0);

		}

		// Cursorクローズ処理
		cursor.close();

		// DBクローズ処理
		db.close();

		// リストを返却
		return sum;

	}

	/**
	 * 指定日付のデータ削除（DELETE）
	 *
	 * @param c コンテキスト
	 * @param 削除する指定日付（検索条件）
	 * @return DB処理結果
	 */
	public static boolean deleteOneData(Context c, String achieveDate) {

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// where句の作成
		String whereClause = MySQLiteOpenHelper.A_DATE + " = '" + achieveDate + "'";

		// DELETEを実行
		int result = db.delete(MySQLiteOpenHelper.DAY_ACHIEVE_TABLE, whereClause, null);

		// DBクローズ処理
		db.close();

		// 処理結果返却
		if(result == 1){

			return true;

		}else{

			return false;

		}

	}

	/**
	 * 全データの削除（DELETE）
	 *
	 * @param c コンテキスト
	 * @return DB処理結果
	 */
	public static boolean deleteAllDatas(Context c){

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// DELETEを実行
		int result = db.delete(MySQLiteOpenHelper.DAY_ACHIEVE_TABLE, null, null);

		// DBクローズ処理
		db.close();

		// 処理結果返却
		if(result > 0){

			return true;

		}else{

			return false;

		}

	}

	/**
	 * タイムスタンプの作成
	 *
	 * @param values ContentValuesインスタンス
	 */
	private static void setTimeStamp(ContentValues values){

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());
		values.put(MySQLiteOpenHelper.A_TIMESTAMP, sdf.format(now));

	}

}
