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

public class AchieveEditDialog extends DialogFragment implements OnClickListener {

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


	boolean mHasDataNothingDB;

	/**
	 * ダイアログ生成時イベント
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// ダイアログ系は必ずActivityに紐付ける
		Dialog dialog = new Dialog(getActivity());

		// レイアウトビュー設定
		dialog.setContentView(R.layout.dialog_goal_edit);

		// 各項目にBundleデータを取得・設定
		String achieveNumber = getArguments().getString("achieveNumber");
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

		mHasDataNothingDB = getArguments().getBoolean("NothingData");


		//登録ボタンを取得
        Button registerAchieveButton = (Button) dialog.findViewById(R.id.registerachievebutton);

		//登録ボタンを取得
        Button deleteAchieveButton = (Button) dialog.findViewById(R.id.deleteachievebutton);

        // 自インスタンスをリスナーとしてセット
        registerAchieveButton.setOnClickListener(this);

        deleteAchieveButton.setOnClickListener(this);

        //ダイアログを返却
		return dialog;
	}


	public void onClick(View v) {

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

			// DBオープン処理
			mHelper = new MySQLiteOpenHelper(getActivity());
			mDb = mHelper.getWritableDatabase();

			// 編集後のデータをまとめる
			final dataAchieveJohoBean bean = new dataAchieveJohoBean(achieveNumberText.getText().toString(),
					commentText.getText().toString(), selectMonthText.getText().toString(),
					selectDayText.getText().toString(), selectDateText.getText().toString());

			// データ挿入
			String msg = AchieveDAO.achieveInsertUpdate(mDb, bean, mHasDataNothingDB);
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

			// アクティビティへイベントを飛ばす
			mListener.onUpdateEditData();

			// ダイアログを閉じる
			dismiss();

			break;

		//削除ボタンを選択した場合
		case R.id.deleteachievebutton:

			// DBにデータが存在する場合は削除ボタンを押下すると削除しにいく
			if (mHasDataNothingDB) {

				// DBオープン処理
				mHelper = new MySQLiteOpenHelper(getActivity());
				mDb = mHelper.getWritableDatabase();

				// 編集後のデータをまとめる
				final dataAchieveJohoBean delbean = new dataAchieveJohoBean(achieveNumberText.getText().toString(),
						commentText.getText().toString(), selectMonthText.getText().toString(),
						selectDayText.getText().toString(), selectDateText.getText().toString());

				// データ挿入
				AchieveDAO.achieveDelete(mDb, delbean, mHasDataNothingDB);

				// アクティビティへイベントを飛ばす
				mListener.onUpdateEditData();

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
	 * ここでダイアログのサイズを指定してやる
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		AchieveDialogWindow(dialog);
	}

	/**
	 * AchieveEditDialogFragmentのインスタンス生成(できる限りMainActivityには書かない)
	 * @param data
	 */
	public static AchieveEditDialog newInstance(dataAchieveJohoBean bean, boolean hasDataNothingDB) {

		final AchieveEditDialog achieveEditDialog = new AchieveEditDialog();

		String achieveNumber = bean.getAchieveNumber();
		String comment = bean.getComment();
		String selectMonth = bean.getSelectMonth();
		String selectDate = bean.getSelectDate();
		String selectDay = bean.getSelectDay();

		// 値をBundleに渡してsetArgumentsしてやる
		Bundle args = new Bundle();
		args.putString("achieveNumber", achieveNumber);
		args.putString("comment", comment);
		args.putString("selectMonth", selectMonth);
		args.putString("selectDate", selectDate);
		args.putString("selectDay", selectDay);
		args.putBoolean("NothingData", hasDataNothingDB);

		achieveEditDialog.setArguments(args);

		return achieveEditDialog;
	}

	/**
	 * Dialogウィンドウのカスタマイズ
	 * @param dialog
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
