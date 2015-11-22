package mokuhyouKanriApp.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mokuhyouKanriApp.bean.MokuhyoJohoBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 目標情報テーブルを操作するDAOクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class GoalDAO {

	/**
	 * データの追加（INSERT）
	 *
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
		long result = db.insert(MySQLiteOpenHelper.GOAL_INFO_TABLE, "", values);

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
	 * @param goalId 目標ID（検索条件）
	 * @return DB処理結果
	 */
	public static boolean update(Context c, ContentValues values, int goalId) {

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// タイムスタンプの値を設定
		setTimeStamp(values);

		// where句の作成
		String whereClause = MySQLiteOpenHelper.GOAL_ID + " = " + goalId;

		// UPDATEを実行
		int result = db.update(MySQLiteOpenHelper.GOAL_INFO_TABLE, values, whereClause, null);

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
	public static List<MokuhyoJohoBean> selectAllDatas(Context c) {

		// MySQLiteOpenHelperインスタンスを取得
		MySQLiteOpenHelper mHelper = new MySQLiteOpenHelper(c);

		// SQLiteDatabaseインスタンスを取得
		SQLiteDatabase db = mHelper.getWritableDatabase();

		//検索結果を格納するリストを生成
		List<MokuhyoJohoBean> tableDataList = new ArrayList<MokuhyoJohoBean>();

		// 取得するカラム名の配列を生成
		String[] columns = new String[]{MySQLiteOpenHelper.GOAL_ID, MySQLiteOpenHelper.M_GENRE, MySQLiteOpenHelper.GOAL, MySQLiteOpenHelper.G_NUMBER, MySQLiteOpenHelper.G_DUE, MySQLiteOpenHelper.G_MEMO};

		// SELECTの実行
		Cursor cursor = db.query(MySQLiteOpenHelper.GOAL_INFO_TABLE, columns, null, null, null, null, MySQLiteOpenHelper.GOAL_ID + " desc", null);

		// 1番目のデータを参照
		boolean isEof = cursor.moveToFirst();

		// 検索結果をリストに格納
		while(isEof) {

			// 各カラムの値を取得
			int goalId = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.GOAL_ID));
			String mGenre = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.M_GENRE));
			String goal = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.GOAL));
			int gNumber = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.G_NUMBER));
			String gDue = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.G_DUE));
			String gMemo = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.G_MEMO));

			// ビーンに値をセット
			MokuhyoJohoBean bean = new MokuhyoJohoBean();
			bean.setGoalId(goalId);
			bean.setmGenre(mGenre);
			bean.setGoal(goal);
			bean.setgNumber(gNumber);
			bean.setgDue(gDue);
			bean.setgMemo(gMemo);

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
		int result = db.delete(MySQLiteOpenHelper.GOAL_INFO_TABLE, null, null);

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
		values.put(MySQLiteOpenHelper.G_TIMESTAMP, sdf.format(now));

	}

}
