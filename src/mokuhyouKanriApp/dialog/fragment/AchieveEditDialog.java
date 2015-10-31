package mokuhyouKanriApp.dialog.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.dataAchieveJohoBean;
import mokuhyouKanriApp.dao.AchieveDAO;
import mokuhyouKanriApp.dao.MySQLiteOpenHelper;

/**
 * 日次実績編集ダイアログクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class AchieveEditDialog extends DialogFragment {

	OnUpdateAchieveEditListener mListener;

	TextView achieveNumberText = null;
	TextView commentText = null;
	TextView selectDateText = null;
	TextView selectDayText = null;
	TextView selectMonthText = null;

	/** SQLiteOpenHelper */
	MySQLiteOpenHelper mHelper = null;

	/** SQLiteDatabase */
	SQLiteDatabase mDb = null;

	/** 目標id */
	int goalId = 0;

	/** 達成数 */
	int achieveNumber = 0;

	/** 選択年月日 */
	String selectDate = "";

	/** データ存在判定 */
	boolean mHasDataNothingDB;

	/** DB処理結果 */
	boolean dbExecuteResult;

	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddkkmm");

	/**
	 * ダイアログ生成時イベントクラス
	 * @param Bundle savedInstanceState
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// ダイアログ系は必ずActivityに紐付ける
		Dialog dialog = new Dialog(getActivity());

		// レイアウトビュー設定
		dialog.setContentView(R.layout.dialog_goal_edit);

		// 各項目にBundleデータを取得・設定
		achieveNumber = getArguments().getInt("achieveNumber");
		achieveNumberText = (TextView)dialog.findViewById(R.id.editachievenumber);
		achieveNumberText.setText(achieveNumber);

		String comment = getArguments().getString("comment");
		commentText = (TextView)dialog.findViewById(R.id.editcomment);
		commentText.setText(comment);

        TextView selectMonthText = (TextView) dialog.findViewById(R.id.selectmonth_text);
        selectMonthText.setText("11");

        TextView selectDateText = (TextView) dialog.findViewById(R.id.selectdate_text);
        selectDateText.setText("11");

        TextView selectDayText = (TextView) dialog.findViewById(R.id.selectday_text);
        selectDayText.setText("水");

        goalId = getArguments().getInt("goalId");

        selectDate = getArguments().getString("selectDate");

		mHasDataNothingDB = getArguments().getBoolean("NothingData");

        //ダイアログを返却
		return dialog;
	}

	/**
	 * ボタンイベントクラス
	 * @param View v
	 */
	public void onClick(View v) {

		//現在時刻を取得する
		Date date = new Date();
		String timeStamp = sdf.format(date);

		// ContentValuesにデータを格納
		ContentValues values = new ContentValues();
		values.put("A_NUMBER", achieveNumber);
		values.put("A_COMMENT", commentText.getText().toString());
		values.put("A_DATE", selectDate);
		values.put("GOAL_ID", goalId);
		values.put("TIMESTAMP", timeStamp);

		/** DBオープン処理
		 * データベースオブジェクトがない又はデータベースが開いていなければ
		 * データベースを開く
		 */
		if(mDb == null || !mDb.isOpen()){

			mHelper = new MySQLiteOpenHelper(getActivity());
			mDb = mHelper.getWritableDatabase();

		}


		//各ボタンが押された場合で処理を分ける
		switch(v.getId()){

		//登録ボタンを選択した場合
		case R.id.registerachievebutton:

			// 未入力状態はNG 目標ジャンル・目標数・達成期限のチェック
			if (achieveNumberText.getText() == null ) {

				//未入力項目がある場合、エラーメッセージをセットする
				Toast.makeText(getActivity(), "未入力項目を入力してください", Toast.LENGTH_SHORT).show();

				return;
			}

			// データ挿入
			if(mHasDataNothingDB){

				dbExecuteResult = AchieveDAO.achieveInsert(mDb, values);

				if(dbExecuteResult){

					//DB処理がうまくいった場合
					Toast.makeText(getActivity(), "新規登録しました", Toast.LENGTH_SHORT).show();

				} else {

					//DB処理がうまくいかなかった場合
					Toast.makeText(getActivity(), "実績の登録に失敗しました", Toast.LENGTH_SHORT).show();

				}

			} else {

				dbExecuteResult = AchieveDAO.achieveUpdate(mDb, values, selectDate);

				if(dbExecuteResult){

					//DB処理がうまくいった場合
					Toast.makeText(getActivity(), "実績を更新しました", Toast.LENGTH_SHORT).show();

				} else {

					//DB処理がうまくいかなかった場合
					Toast.makeText(getActivity(), "実績の更新に失敗しました", Toast.LENGTH_SHORT).show();

				}

			}

			// アクティビティへイベントを飛ばす
			//mListener.onUpdateEditData();

			// ダイアログを閉じる
			dismiss();

			//お祝い画面表示判定処理


			break;

		//削除ボタンを選択した場合
		case R.id.deleteachievebutton:

			// DBにデータが存在する場合は削除ボタンを押下すると削除しにいく
			if (mHasDataNothingDB) {

				// データ挿入
				dbExecuteResult = AchieveDAO.achieveDelete(mDb, selectDate);

				if(dbExecuteResult){

					//DB処理がうまくいった場合
					Toast.makeText(getActivity(), "実績を削除しました", Toast.LENGTH_SHORT).show();

				} else {

					//DB処理がうまくいかなかった場合
					Toast.makeText(getActivity(), "実績の削除に失敗しました", Toast.LENGTH_SHORT).show();

				}

				// アクティビティへイベントを飛ばす
				//mListener.onUpdateEditData();

			}

			// ダイアログを閉じる
			dismiss();


			break;

		}



	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnUpdateAchieveEditListener)activity;
		} catch (ClassCastException e){
			throw new IllegalStateException("activity should implement FragmentCallbacks", e);
			// throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
		}
	}

	public static interface OnUpdateAchieveEditListener {
		public void onUpdateEditData();
	}

	/**
	 * ダイアログサイズ指定クラス
	 * @param Bundle savedInstanceState
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		AchieveDialogWindow(dialog);
	}

	/**
	 * AchieveEditDialogFragmentのインスタンス生成するクラス
	 * @param dataAchieveJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static AchieveEditDialog newInstance(dataAchieveJohoBean bean, boolean hasDataNothingDB) {

		final AchieveEditDialog achieveEditDialog = new AchieveEditDialog();

		int achieveNumber = bean.getAchieveNumber();
		int goalId = bean.getGoalId();
		String comment = bean.getComment();
		String selectDate = bean.getSelectDate();

		// 値をBundleに渡してsetArgumentsしてやる
		Bundle args = new Bundle();
		args.putInt("goalId", goalId);
		args.putInt("achieveNumber", achieveNumber);
		args.putString("comment", comment);
		args.putString("selectDate", selectDate);
		args.putBoolean("NothingData", hasDataNothingDB);

		achieveEditDialog.setArguments(args);

		return achieveEditDialog;
	}

	/**
	 * Dialogウィンドウカスタマイズクラス
	 * @param Dialog dialog
	 */
	private void AchieveDialogWindow(Dialog dialog) {

		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		// ディスプレイ情報取得
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		lp.width = (int)(metrics.widthPixels * 0.8);
		lp.height = (int)(metrics.heightPixels * 0.8);
		dialog.getWindow().setAttributes(lp);

	}

}
