package mokuhyouKanriApp.dialog.fragment;


import java.text.SimpleDateFormat;
import java.util.Date;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.dataMokuhyoJohoBean;
import mokuhyouKanriApp.dao.MySQLiteOpenHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登録編集ダイアログクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class GoalEditDialog extends DialogFragment {

	OnUpdateMokuhyoJohoListener mListener;

	TextView goalGenreText = null;
	TextView goalText = null;
	TextView goalNumberText = null;
	TextView goalDueText = null;
	TextView memoText = null;

	/** SQLiteOpenHelper */
	MySQLiteOpenHelper mHelper = null;

	/** SQLiteDatabase */
	SQLiteDatabase mDb = null;

	/** 目標ID */
	int goalId = 0;

	/** 目標数 */
	int goalNumber = 0;

	/** データ存在判定処理 */
	boolean mHasDataNothingDB;

	/** DB処理結果 */
	boolean dbExecuteResult;


	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddkkmm");

	/**
	 * ダイアログ生成時イベント
	 * @param Bundle savedInstanceState
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// ダイアログ系は必ずActivityに紐付ける
		Dialog dialog = new Dialog(getActivity());

		// レイアウトビュー設定
		dialog.setContentView(R.layout.dialog_achieve_edit);

		// 各項目にBundleデータを取得・設定
		String goalGenre = getArguments().getString("goalgenre");
		goalGenreText = (TextView)dialog.findViewById(R.id.editgoalgenre);
		goalGenreText.setText(goalGenre);

		String goal = getArguments().getString("goal");
		goalText = (TextView)dialog.findViewById(R.id.editgoal);
		goalText.setText(goal);

		goalNumber = getArguments().getInt("goalnumber");
		goalNumberText = (TextView)dialog.findViewById(R.id.editgoalnumber);
		goalNumberText.setText(goalNumber);

		String goalDue = getArguments().getString("goaldue");
		goalDueText = (TextView)dialog.findViewById(R.id.editgoaldue);
		goalDueText.setText(goalDue);


		String memo = getArguments().getString("memo");
		memoText = (TextView)dialog.findViewById(R.id.editmemo);
		memoText.setText(memo);

		mHasDataNothingDB = getArguments().getBoolean("NothingData");

		goalId = getArguments().getInt("goalId");


		//登録ボタンを取得
        //Button registerGoalButton = (Button) dialog.findViewById(R.id.registergoalbutton);

        // 自インスタンスをリスナーとしてセット
        //registerGoalButton.setOnClickListener(this);

        //ダイアログを返却
		return dialog;
	}

	/**
	 * 目標登録ダイアログインナークラス
	 * @param View v
	 */
	public void onClick(View v) {

		if(v.getId() == R.id.registergoalbutton){
			// 未入力状態はNG 目標ジャンル・目標数・達成期限のチェック
			if (goalGenreText.getText() == null || goalGenreText.getText().length() == 0
					|| goalNumberText.getText() == null || goalNumberText.getText().length() == 0) {

				//未入力項目がある場合、エラーメッセージをセットする
				Toast.makeText(getActivity(), "未入力項目を入力してください", Toast.LENGTH_SHORT).show();

				return;
			}

			/** DBオープン処理
			 * データベースオブジェクトがない又はデータベースが開いていなければ
			 * データベースを開く
			 */
			if(mDb == null || !mDb.isOpen()){

				mHelper = new MySQLiteOpenHelper(getActivity());
				mDb = mHelper.getWritableDatabase();

			}

			// 編集後のデータをまとめる
			//final dataMokuhyoJohoBean bean = new dataMokuhyoJohoBean(goalId, goalGenreText.getText().toString(),
					//goalText.getText().toString(), goalNumber, goalDueText.getText().toString(),
					//memoText.getText().toString());

			// データ挿入
			//String msg = GoalDAO.goalInsertUpdate(mDb, bean, mHasDataNothingDB);
			//Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

			//現在時刻を取得する
			Date date = new Date();
			String timeStamp = sdf.format(date);

			// ContentValuesにデータを格納
			/*ContentValues values = new ContentValues();
			values.put("M_GENRE", bean.getGoalGenre());
			values.put("GOAL", bean.getGoal());
			values.put("G_NUMBER", bean.getGoalNumber());
			values.put("G_DUE", bean.getGoalDue());
			values.put("G_MEMO", bean.getMemo());
			values.put("TIMESTAMP", timeStamp);*/

			if(mHasDataNothingDB){

				/** データが存在しない場合
				データベースにアクセスしinsertする */
				//dbExecuteResult = GoalDAO.goalInsert(mDb, values);

				if(dbExecuteResult){

					//DB処理がうまくいった場合
					Toast.makeText(getActivity(), "新規登録しました", Toast.LENGTH_SHORT).show();

				} else {

					//DB処理がうまくいかなかった場合
					Toast.makeText(getActivity(), "目標の登録に失敗しました", Toast.LENGTH_SHORT).show();

				}

			} else {

				/** データが存在する場合
				    データベースにアクセスしupdateする */
				//dbExecuteResult = GoalDAO.goalUpdate(mDb, values, bean.getGoalId());

				if(dbExecuteResult){

					//DB処理がうまくいった場合
					Toast.makeText(getActivity(), "目標を更新しました", Toast.LENGTH_SHORT).show();

				} else {

					//DB処理がうまくいかなかった場合
					Toast.makeText(getActivity(), "目標の更新に失敗しました", Toast.LENGTH_SHORT).show();

				}

			}

			// アクティビティへイベントを飛ばす
			//mListener.onUpdateEditData();
		}

		// ダイアログを閉じる
		dismiss();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnUpdateMokuhyoJohoListener)activity;
		} catch (ClassCastException e){
			throw new IllegalStateException("activity should implement FragmentCallbacks", e);
			// throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
		}
	}

	public static interface OnUpdateMokuhyoJohoListener {
		public void onUpdateEditData();
	}

	/**
	 * ダイアログのサイズ指定クラス
	 * @param Bundle savedInstanceState
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		customDialogWindow(dialog);
	}

	/**
	 * GoalEditDialogFragmentのインスタンス生成クラス
	 * @param dataMokuhyoJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static GoalEditDialog newInstance(dataMokuhyoJohoBean bean, boolean hasDataNothingDB) {

		final GoalEditDialog goalEditDialog = new GoalEditDialog();

		/*int goalId = bean.getGoalId();
		String goalGenre = bean.getGoalGenre();
		String goal = bean.getGoal();
		int goalNumber = bean.getGoalNumber();
		String goalDue = bean.getGoalDue();
		String memo = bean.getMemo();

		// 値をBundleに渡してsetArgumentsしてやる
		Bundle args = new Bundle();
		args.putInt("goalId", goalId);
		args.putString("goalGenre", goalGenre);
		args.putString("goal", goal);
		args.putInt("goalNumber", goalNumber);
		args.putString("goalDue", goalDue);
		args.putString("memo", memo);
		args.putBoolean("NothingData", hasDataNothingDB);

		goalEditDialog.setArguments(args);*/

		return goalEditDialog;
	}

	/**
	 * Dialogウィンドウのカスタマイズクラス
	 * @param Dialog dialog
	 */
	private void customDialogWindow(Dialog dialog) {

		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		// ディスプレイ情報取得
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		lp.width = (int)(metrics.widthPixels * 0.8);
		lp.height = (int)(metrics.heightPixels * 0.8);
		dialog.getWindow().setAttributes(lp);

	}

}
