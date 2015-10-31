package mokuhyouKanriApp.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import mokuhyouKanriApp.bean.dataMokuhyoJohoBean;

/**
 * 目標登録情報を検索・登録・削除する処理実行クラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class GoalDAO {

	/** インスタンス化不可 */
	private GoalDAO() {}

	/**
	 * テーブルに編集データを新規追加する
	 * @param SQLiteDatabase db
	 * @param dataMokuhyoJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static boolean goalInsert(SQLiteDatabase db, ContentValues values) {

		/** DBアクセス実行結果 */
		boolean dbExecuteResult = false;

		/** データの挿入 */
		long result;

		result = db.insert("goalInfoTable", null, values);

		/** 結果判定処理 */
		if(result == 1){

			dbExecuteResult = true;

		} else {

			dbExecuteResult = false;

		}

		return dbExecuteResult;

	}

	/**
	 * テーブルに編集データを更新する
	 * @param SQLiteDatabase db
	 * @param dataMokuhyoJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static boolean goalUpdate(SQLiteDatabase db, ContentValues values, int goalId) {

		/** DBアクセス実行結果 */
		boolean dbExecuteResult = false;

		/** データの更新 */
		int result;

		String whereClause = "GOAL_ID = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = String.valueOf(goalId);
		result = db.update("goalInfoTable", values, whereClause, whereArgs);

		/** 結果判定処理 */
		if(result == -1){

			dbExecuteResult = false;

		} else {

			dbExecuteResult = true;

		}

		return dbExecuteResult;

	}


	/**
	 * テーブルから全データを取得するクラス
	 * @param SQLiteDatabase db
	 */
	public static List<dataMokuhyoJohoBean> goalSelect(SQLiteDatabase db) {

		//検索結果を格納するList<Bean>を生成する
		List<dataMokuhyoJohoBean> tableDataList = new ArrayList<dataMokuhyoJohoBean>();

		Cursor cursor = db.query(
				"goalInfoTable", new String[] {"GOAL_ID", "M_GENRE", "GOAL", "G_NUMBER", "G_DUE", "G_MEMO"},
				null, null, null, null, "GOAL_ID DESC");

		// 参照先を一番始めに(検索直後は0件目を指しているため)
		boolean isEof = cursor.moveToFirst();

		// データがなかった時のためにもこのループに入る条件文は必須
		while(isEof) {
			int goalId = cursor.getInt(cursor.getColumnIndex("GOAL_ID"));
			String goalGenre = cursor.getString(cursor.getColumnIndex("M_GENRE"));
			String goal = cursor.getString(cursor.getColumnIndex("GOAL"));
			int goalNumber = cursor.getInt(cursor.getColumnIndex("G_NUMBER"));
			String goalDue = cursor.getString(cursor.getColumnIndex("G_DUE"));
			String memo = cursor.getString(cursor.getColumnIndex("G_MEMO"));

			// 取得データオブジェクト生成
			dataMokuhyoJohoBean bean = new dataMokuhyoJohoBean(goalId, goalGenre, goal, goalNumber, goalDue, memo);
			tableDataList.add(bean);

			isEof = cursor.moveToNext();
		}
		cursor.close();

		return tableDataList;
	}

}
