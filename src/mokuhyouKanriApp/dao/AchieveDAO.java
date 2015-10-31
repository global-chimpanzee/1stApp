package mokuhyouKanriApp.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import mokuhyouKanriApp.bean.dataAchieveJohoBean;

/**
 * 日次実績情報を検索・登録・削除する実行処理クラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class AchieveDAO {

	// インスタンス化不可
	private AchieveDAO() {}

	/**
	 * テーブルに編集データを新規に追加する
	 * @param SQLiteDatabase db
	 * @param dataAchieveJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static boolean achieveInsert(SQLiteDatabase db, ContentValues values) {

		/** DBアクセス実行結果 */
		boolean dbExecuteResult = false;

		long result;

		// データの挿入
		result = db.insert("AchieveInfoTable", null, values);

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
	 * @param dataAchieveJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static boolean achieveUpdate(SQLiteDatabase db, ContentValues values, String AchieveDate) {

		/** DBアクセス実行結果 */
		boolean dbExecuteResult = false;

		int result;

		String whereClause = "A_DATE = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = AchieveDate;
		result = db.update("achieveInfoTable", values, whereClause, whereArgs);

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
	public static List<dataAchieveJohoBean> achieveSelect(SQLiteDatabase db) {

		//検索結果を格納するList<Bean>を生成する
		List<dataAchieveJohoBean> tableDataList = new ArrayList<dataAchieveJohoBean>();

		Cursor cursor = db.query(
				"AchieveInfoTable", new String[] {"GOAL_ID", "A_NUMBER", "A_COMMENT", "A_DATE", "TIMESTAMP"},
				null, null, null, null, "A_DATE DESC");

		// 参照先を一番始めに(検索直後は0件目を指しているため)
		boolean isEof = cursor.moveToFirst();

		// データがなかった時のためにもこのループに入る条件文は必須
		while(isEof) {
			int goalId = cursor.getInt(cursor.getColumnIndex("GOAL_ID"));
			int achieveNumber = cursor.getInt(cursor.getColumnIndex("A_NUMBER"));
			String comment = cursor.getString(cursor.getColumnIndex("A_COMMENT"));
			String achieveDue = cursor.getString(cursor.getColumnIndex("A_DATE"));
			//String timeStamp = cursor.getString(cursor.getColumnIndex("TIMESTAMP"));

			// 取得データオブジェクト生成
			dataAchieveJohoBean bean = new dataAchieveJohoBean(goalId, achieveNumber, comment, achieveDue);
			tableDataList.add(bean);

			isEof = cursor.moveToNext();
		}
		cursor.close();

		return tableDataList;
	}

	/**
	 * テーブルから選択した日付データを削除するクラス
	 * @param SQLiteDatabase db
	 * @param dataAchieveJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static boolean achieveDelete(SQLiteDatabase db, String achieveDate) {

		/** DBアクセス実行結果 */
		boolean dbExecuteResult;

		//選択した日次実績を削除する
		int result;
		result = db.delete("achieveInfoTable", achieveDate, null);

		/** 結果判定処理 */
		if(result == -1){

			dbExecuteResult = false;

		} else {

			dbExecuteResult = true;

		}

		return dbExecuteResult;
	}

}
