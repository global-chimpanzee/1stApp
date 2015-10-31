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


	// インスタンス化不可
	private GoalDAO() {}

	/**
	 * テーブルに編集データを追加
	 * @param SQLiteDatabase db
	 * @param dataMokuhyoJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static String goalInsertUpdate(SQLiteDatabase db, dataMokuhyoJohoBean bean, boolean hasDataNothingDB) {

		// ContentValuesにデータを格納
		ContentValues values = new ContentValues();
		values.put("goalGenre", bean.getGoalGenre());
		values.put("goal", bean.getGoal());
		values.put("goalNumber", bean.getGoalNumber());
		values.put("goalDue", bean.getGoalDue());
		values.put("memo", bean.getMemo());

		// 新規の場合テーブルにデータ追加
		if(hasDataNothingDB) {

			// データの挿入
			db.insert("goalInfoTable", null, values);
			return "新規登録しました";
		}
		// 既にデータがある場合は更新
		else {

			// データの更新
			final String UPSATE_QUERY = "update goalInfoTable " +
										"set goalgenre = '" + bean.getGoalGenre() +
										"', goal = '" + bean.getGoal() +
										"', goalNumber = '" + bean.getGoalNumber() +
										"', goalDue = '" + bean.getGoalDue() +
										"', memo = '" + bean.getMemo() +
										"' where _id = (select max(_id) from goalInfoTable)";
			db.execSQL(UPSATE_QUERY);
			return "データを更新しました";
		}
	}

	/**
	 * テーブルから全データを取得するクラス
	 * @param SQLiteDatabase db
	 */
	public static List<dataMokuhyoJohoBean> goalSelect(SQLiteDatabase db) {

		//検索結果を格納するList<Bean>を生成する
		List<dataMokuhyoJohoBean> tableDataList = new ArrayList<dataMokuhyoJohoBean>();

		Cursor cursor = db.query(
				"goalInfoTable", new String[] {"goalId", "goalgenre", "goal", "goalnumber", "goaldue", "memo"},
				null, null, null, null, "goalId DESC");

		// 参照先を一番始めに(検索直後は0件目を指しているため)
		boolean isEof = cursor.moveToFirst();

		// データがなかった時のためにもこのループに入る条件文は必須
		while(isEof) {
			String goalGenre = cursor.getString(cursor.getColumnIndex("goalGenre"));
			String goal = cursor.getString(cursor.getColumnIndex("goal"));
			String goalNumber = cursor.getString(cursor.getColumnIndex("goalNumber"));
			String goalDue = cursor.getString(cursor.getColumnIndex("goalDue"));
			String memo = cursor.getString(cursor.getColumnIndex("memo"));

			// 取得データオブジェクト生成
			dataMokuhyoJohoBean bean = new dataMokuhyoJohoBean(goalGenre, goal, goalNumber, goalDue, memo);
			tableDataList.add(bean);

			isEof = cursor.moveToNext();
		}
		cursor.close();

		return tableDataList;
	}

}
