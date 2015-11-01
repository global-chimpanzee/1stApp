package mokuhyouKanriApp.dialog.fragment;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.dao.MySQLiteOpenHelper;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 日次実績編集ダイアログクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class AchieveEditDialog extends DialogFragment {

	TextView achieveNumberText = null;
	TextView commentText = null;
	TextView selectDateText = null;
	TextView selectDayText = null;
	TextView selectMonthText = null;

	/** SQLiteOpenHelper */
	MySQLiteOpenHelper mHelper = null;

	/** SQLiteDatabase */
	SQLiteDatabase mDb = null;

	/** 選択年 */
	private String year = null;

	/** 選択月 */
	private String month = null;

	/** 選択日 */
	private String date = null;


	boolean mHasDataNothingDB;

	/**
	 * AchieveEditDialogインスタンス生成し、値をセットする
	 * @param year 選択年
	 * @param month 選択月
	 * @param date 選択日
	 * @return 日次実績編集画面ダイアログフラグメント
	 */
	public static AchieveEditDialog newInstance(String year, String month, String date) {

		// AchieveEditDialogインスタンスの生成
		AchieveEditDialog achieveEditDialog = new AchieveEditDialog();

		// バンドルの生成
		Bundle args = new Bundle();

		// バンドルに値をセット
		args.putString("year", year);
		args.putString("month", month);
		args.putString("date", date);

		// バンドルをダイアログフラグメントにセット
		achieveEditDialog.setArguments(args);

		// ダイアログフラグメントを返却
		return achieveEditDialog;

	}

	/**
	 * DB検索を行う
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// バンドルから年月日文字列を取得
		this.year = getArguments().getString("year");
		this.month = getArguments().getString("month");
		this.date = getArguments().getString("date");

		// DBオープン処理
		//mHelper = new MySQLiteOpenHelper(getActivity());
	    //mDb = mHelper.getWritableDatabase();

		// DB検索で達成数とコメントを取得
		// TODO ここにDB処理を記述


	}

	/**
	 * 日次実績編集画面ダイアログフラグメントを表示する
	 * @param inflater LayoutInflaterインスタンス
	 * @param container ViewGroupインスタンス
	 * @param savedInstanceState バンドル
	 * @return 日次実績編集画面View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// dialog_goal_edit.xmlを紐付ける
		View view = inflater.inflate(R.layout.dialog_achieve_edit, container, false);

		// タイトルを非表示にする
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// 選択日付表示テキストビューを取得し、選択日付をセット
		TextView selectedDate = (TextView) view.findViewById(R.id.selected_date);
		selectedDate.setText(this.year + "年" + this.month + "月" + this.date + "日");

		// 達成数EditTextコンポーネントを取得し、初期値をセット
		EditText editGoalNum = (EditText) view.findViewById(R.id.achieve_num_edittext);
		editGoalNum.setText("ここにDBから取得したものをいれる");

		// コメントEditTextコンポーネントを取得し、初期値をセット
		EditText editComment = (EditText) view.findViewById(R.id.comment_edittext);
		editComment.setText("ここにDBから取得したものをいれる");

		return view;

	}

	/**
	 * ダイアログ生成時イベントクラス
	 * @param Bundle savedInstanceState
	 */
	//@Override
	//public Dialog onCreateDialog(Bundle savedInstanceState) {

		// ダイアログ系は必ずActivityに紐付ける
		//Dialog dialog = new Dialog(getActivity());

		// レイアウトビュー設定
		//dialog.setContentView(R.layout.dialog_goal_edit);

		// 各項目にBundleデータを取得・設定
		//String achieveNumber = getArguments().getString("achieveNumber");
		//achieveNumberText = (TextView)dialog.findViewById(R.id.editachievenumber);
		//achieveNumberText.setText(achieveNumber);

		//String comment = getArguments().getString("comment");
		//commentText = (TextView)dialog.findViewById(R.id.editcomment);
		//commentText.setText(comment);

        //TextView selectMonthText = (TextView) dialog.findViewById(R.id.selectmonth_text);
        //selectMonthText.setText("11");

        //TextView selectDateText = (TextView) dialog.findViewById(R.id.selectdate_text);
        //selectDateText.setText("11");

        //TextView selectDayText = (TextView) dialog.findViewById(R.id.selectday_text);
        //selectDayText.setText("水");

		//mHasDataNothingDB = getArguments().getBoolean("NothingData");


		//登録ボタンを取得
        //Button registerAchieveButton = (Button) dialog.findViewById(R.id.registerachievebutton);

		//登録ボタンを取得
        //Button deleteAchieveButton = (Button) dialog.findViewById(R.id.deleteachievebutton);

        // 自インスタンスをリスナーとしてセット
        //registerAchieveButton.setOnClickListener(this);

        //deleteAchieveButton.setOnClickListener(this);

        //ダイアログを返却
		//return dialog;
	//}

	/**
	 * ボタンイベントクラス
	 * @param View v
	 */
	/*public void onClick(View v) {

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
			//final dataAchieveJohoBean bean = new dataAchieveJohoBean(achieveNumberText.getText().toString(),
			//		commentText.getText().toString(), selectMonthText.getText().toString(),
			//		selectDayText.getText().toString(), selectDateText.getText().toString());

			// データ挿入
			//String msg = AchieveDAO.achieveInsertUpdate(mDb, bean, mHasDataNothingDB);
			//Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

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
				//final dataAchieveJohoBean delbean = new dataAchieveJohoBean(achieveNumberText.getText().toString(),
				//		commentText.getText().toString(), selectMonthText.getText().toString(),
				//		selectDayText.getText().toString(), selectDateText.getText().toString());

				// データ挿入
				//AchieveDAO.achieveDelete(mDb, delbean, mHasDataNothingDB);

				// アクティビティへイベントを飛ばす
				//mListener.onUpdateEditData();

			}

			// ダイアログを閉じる
			dismiss();


			break;

		}



	}*/

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
	 * Dialogウィンドウカスタマイズクラス
	 * @param Dialog dialog
	 */
	private void AchieveDialogWindow(Dialog dialog) {

		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		// ディスプレイ情報取得
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		lp.width = (int)(metrics.widthPixels * 0.9);
		lp.height = (int)(metrics.heightPixels * 0.9);
		dialog.getWindow().setAttributes(lp);

	}

}
