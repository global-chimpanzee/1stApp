package mokuhyouKanriApp.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import mokuhyouKanriApp.bean.dataAchieveJohoBean;

/**
 * 日次実績情報テーブルにデータを検索・登録・削除する実行処理群クラス
 */


public class AchieveDAO {


	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddkkmm");

	// インスタンス化不可
	private AchieveDAO() {}

	/**
	 * テーブルに編集データを追加
	 * @param db
	 * @param dataAchieveJohoBean
	 * @param hasDataNothingDB
	 */
	public static String achieveInsertUpdate(SQLiteDatabase db, dataAchieveJohoBean bean, boolean hasDataNothingDB) {

		//現在時刻を取得する
		Date date = new Date();
		String timeStamp = sdf.format(date);

		//選択日付をまとめる
		String achieveDate = bean.getSelectYear()+bean.getSelectMonth()+bean.getSelectDate();

		// ContentValuesにデータを格納
		ContentValues values = new ContentValues();
		values.put("A_NUMBER", bean.getAchieveNumber());
		values.put("A_COMMENT", bean.getComment());
		values.put("A_DATE", achieveDate);
		values.put("GOAL_ID", bean.getGoalId());
		values.put("TIMESTAMP", timeStamp);

		// 新規の場合テーブルにデータ追加
		if(hasDataNothingDB) {

			// データの挿入
			db.insert("AchieveInfoTable", null, values);
			return "新規登録しました";
		}
		// 既にデータがある場合は更新
		else {

			// データの更新
			final String UPSATE_QUERY = "update AchieveInfoTable " +
										"set GOAL_ID = '" + bean.getGoalId() +
										"', A_NUMBER = '" + bean.getAchieveNumber() +
										"', A_COMMENT = '" + bean.getComment() +
										"', A_DATE = '" + achieveDate +
										"', TIMESTAMP = '" + sdf +
										"' where A_DATE = " + achieveDate;
			db.execSQL(UPSATE_QUERY);
			return "データを更新しました";
		}
	}

	/**
	 * テーブルから全データを取得
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
			String goalId = cursor.getString(cursor.getColumnIndex("goalId"));
			String achieveNumber = cursor.getString(cursor.getColumnIndex("achieveNumber"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			String achieveDue = cursor.getString(cursor.getColumnIndex("achieveDue"));
			String timeStamp = cursor.getString(cursor.getColumnIndex("timeStamp"));

			// 取得データオブジェクト生成
			dataAchieveJohoBean bean = new dataAchieveJohoBean(goalId, achieveNumber, comment, achieveDue, timeStamp);
			tableDataList.add(bean);

			isEof = cursor.moveToNext();
		}
		cursor.close();

		return tableDataList;
	}

	public static void achieveDelete(SQLiteDatabase db, dataAchieveJohoBean bean, boolean hasDataNothingDB) {

		//選択日付を設定する
		String achieveDate = bean.getSelectYear()+bean.getSelectMonth()+bean.getSelectDate();

		//データが存在する場合、データを削除する
		if(hasDataNothingDB){

			//選択した日次実績を削除する
			db.delete("achieveInfoTable", achieveDate, null);

		}


	}

}
