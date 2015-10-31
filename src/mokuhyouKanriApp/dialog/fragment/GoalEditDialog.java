package mokuhyouKanriApp.dialog.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.dataMokuhyoJohoBean;
import mokuhyouKanriApp.dao.GoalDAO;
import mokuhyouKanriApp.dao.MySQLiteOpenHelper;

/**
 * 登録編集ダイアログクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */

public class GoalEditDialog extends DialogFragment implements OnClickListener {

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


	boolean mHasDataNothingDB;

	/**
	 * ダイアログ生成時イベント
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

		String goalNumber = getArguments().getString("goalnumber");
		goalNumberText = (TextView)dialog.findViewById(R.id.editgoalnumber);
		goalNumberText.setText(goalNumber);

		String goalDue = getArguments().getString("goaldue");
		goalDueText = (TextView)dialog.findViewById(R.id.editgoaldue);
		goalDueText.setText(goalDue);


		String memo = getArguments().getString("memo");
		memoText = (TextView)dialog.findViewById(R.id.editmemo);
		memoText.setText(memo);

		mHasDataNothingDB = getArguments().getBoolean("NothingData");


		//登録ボタンを取得
        Button registerGoalButton = (Button) dialog.findViewById(R.id.registergoalbutton);

        // 自インスタンスをリスナーとしてセット
        registerGoalButton.setOnClickListener(this);

        //ダイアログを返却
		return dialog;
	}


	public void onClick(View v) {

		// 未入力状態はNG 目標ジャンル・目標数・達成期限のチェック
		if (goalGenreText.getText() == null || goalGenreText.getText().length() == 0
				|| goalNumberText.getText() == null || goalNumberText.getText().length() == 0) {

			//未入力項目がある場合、エラーメッセージをセットする
			Toast.makeText(getActivity(), "未入力項目を入力してください", Toast.LENGTH_SHORT).show();

			return;
		}

		// DBオープン処理
		mHelper = new MySQLiteOpenHelper(getActivity());
		mDb = mHelper.getWritableDatabase();

		// 編集後のデータをまとめる
		final dataMokuhyoJohoBean bean = new dataMokuhyoJohoBean(goalGenreText.getText().toString(),
				goalText.getText().toString(), goalNumberText.getText().toString(), goalDueText.getText().toString(),
				memoText.getText().toString());

		// データ挿入
		String msg = GoalDAO.goalInsertUpdate(mDb, bean, mHasDataNothingDB);
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

		// アクティビティへイベントを飛ばす
		mListener.onUpdateEditData();

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
	 * ここでダイアログのサイズを指定してやる
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		customDialogWindow(dialog);
	}

	/**
	 * GoalEditDialogFragmentのインスタンス生成(できる限りMainActivityには書かない)
	 * @param data
	 */
	public static GoalEditDialog newInstance(dataMokuhyoJohoBean bean, boolean hasDataNothingDB) {

		final GoalEditDialog goalEditDialog = new GoalEditDialog();

		String goalGenre = bean.getGoalGenre();
		String goal = bean.getGoal();
		String goalNumber = bean.getGoalNumber();
		String goalDue = bean.getGoalDue();
		String memo = bean.getMemo();

		// 値をBundleに渡してsetArgumentsしてやる
		Bundle args = new Bundle();
		args.putString("goalGenre", goalGenre);
		args.putString("goal", goal);
		args.putString("goalNumber", goalNumber);
		args.putString("goalDue", goalDue);
		args.putString("memo", memo);
		args.putBoolean("NothingData", hasDataNothingDB);

		goalEditDialog.setArguments(args);

		return goalEditDialog;
	}

	/**
	 * Dialogウィンドウのカスタマイズ
	 * @param dialog
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
