package mokuhyouKanriApp.dialog.fragment;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
public class GoalEditDialog extends DialogFragment {

	/** ビューコンポーネント */
	private View view = null;

	/** 目標ジャンル */
	private int goalId = 0;

	/** 目標ジャンル */
	private String mGenre = null;

	/** 目標 */
	private String goal = null;

	/** 目標数 */
	private int gNumber = 0;

	/** 達成期限 */
	private String gDue = null;

	/** 目標メモ */
	private String gMemo = null;

	/** dataMokuhyoJohoBeanインスタンス */
	private dataMokuhyoJohoBean mokuhyoJohoBean = null;


	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

	/**
	 * GoalEditDialogFragmentのインスタンス生成クラス
	 * @param dataMokuhyoJohoBean bean
	 * @param boolean hasDataNothingDB
	 */
	public static GoalEditDialog newInstance(dataMokuhyoJohoBean bean) {



		// AchieveEditDialogインスタンスの生成
		GoalEditDialog goalEditDialog = new GoalEditDialog();

		// バンドルの生成
		Bundle args = new Bundle();

		// バンドルに値をセット
		args.putString("mGenre", bean.getmGenre());
		args.putString("goal", bean.getGoal());
		args.putLong("gNumber", bean.getgNumber());
		args.putString("gDue", bean.getgDue());
		args.putString("gMemo", bean.getgMemo());
		args.putLong("goalId", bean.getGoalId());

		// バンドルをダイアログフラグメントにセット
		goalEditDialog.setArguments(args);

		// ダイアログフラグメントを返却
		return goalEditDialog;
	}

	/**
	 * ダイアログ生成時イベント
	 * @param Bundle savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// バンドルから値を取得
		mokuhyoJohoBean = new dataMokuhyoJohoBean();
		this.goalId = getArguments().getInt("goalId");
		this.mGenre = getArguments().getString("mGenre");
		this.goal = getArguments().getString("goal");
		this.gNumber = getArguments().getInt("gNumber");
		this.gDue = getArguments().getString("gDue");
		this.gMemo = getArguments().getString("gMemo");
		mokuhyoJohoBean.setGoalId(this.goalId);
		mokuhyoJohoBean.setmGenre(this.mGenre);
		mokuhyoJohoBean.setGoal(this.goal);
		mokuhyoJohoBean.setgNumber(this.gNumber);
		mokuhyoJohoBean.setgDue(this.gDue);
		mokuhyoJohoBean.setgMemo(this.gMemo);

	}

	/**
	 * 目標編集画面ダイアログフラグメントを表示する
	 * @param inflater LayoutInflaterインスタンス
	 * @param container ViewGroupインスタンス
	 * @param savedInstanceState バンドル
	 * @return 目標編集画面View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// dialog_goal_edit.xmlを紐付ける
		this.view = inflater.inflate(R.layout.dialog_goal_edit, container, false);

		// タイトルを非表示にする
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// DB検索結果nullチェック
		if(this.mokuhyoJohoBean != null){

			// 目標ジャンルをセット
			TextView mGenreText = (TextView) this.view.findViewById(R.id.goal_genre_edittext);
			mGenreText.setText(this.mokuhyoJohoBean.getmGenre());

			// 目標をセット
			TextView goalText = (TextView) this.view.findViewById(R.id.goal_edittext);
			goalText.setText(this.mokuhyoJohoBean.getGoal());

			// 目標数をセット
			TextView goalNumberText = (TextView) this.view.findViewById(R.id.goal_num_edittext);
			goalNumberText.setText(String.valueOf(this.mokuhyoJohoBean.getgNumber()));

			// 達成期限をセット
			String gDueYear = "";
			String gDueMonth = "";
			String gDueDay = "";
			int year = 0;
			int month = 0;
			int day = 0;

			if(this.mokuhyoJohoBean.getgDue().length() == 8){
				gDueYear = gDue.substring(0, 4);
				gDueMonth = gDue.substring(4, 4+2);
				gDueDay = gDue.substring(6, 6+2);
				year = Integer.parseInt(gDueYear);
				month = Integer.parseInt(gDueMonth);
				day = Integer.parseInt(gDueDay);

				//目標が登録されている場合はdatepickerに初期値を設定する
				DatePicker datepicker = (DatePicker)this.view.findViewById(R.id.datePicker1);
				datepicker.updateDate(year,month,day);
			}

//			TextView gDueYearText = (TextView) this.view.findViewById(R.id.editgoaldue);
//			gDueYearText.setText(gDueYear);
	//
//			TextView gDueMonthText = (TextView) this.view.findViewById(R.id.editgoaldue);
//			gDueMonthText.setText(gDueMonth);
	//
//			TextView gDueDayText = (TextView) this.view.findViewById(R.id.editgoaldue);
//			gDueDayText.setText(gDueDay);

			// メモをセット
			TextView gMemoText = (TextView) this.view.findViewById(R.id.goal_memo_edittext);
			gMemoText.setText(this.mokuhyoJohoBean.getgMemo());

		}

		// 登録ボタンにリスナーをセット
		Button registButton = (Button) this.view.findViewById(R.id.registergoalbutton);
		RegistGoalAdapter rga = new RegistGoalAdapter();
		registButton.setOnClickListener(rga);

		// ビューを返却
		return view;

	}

	/**
	 * ダイアログサイズ指定
	 *
	 * @param savedInstanceState バンドル
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		// ディスプレイ情報取得
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		lp.width = (int)(metrics.widthPixels * 0.95);
		lp.height = (int)(metrics.heightPixels * 0.95);
		dialog.getWindow().setAttributes(lp);

	}


	/**
	 * 登録ボタンリスナークラス
	 */
	class RegistGoalAdapter implements OnClickListener {

		/**
		 * 目標登録処理
		 */
		@Override
		public void onClick(View paramView) {

			// 登録フラグ
			boolean registFlg = false;

			// 目標ジャンルEditTextコンポーネントを取得し、String型に変換
			EditText editGoalGenre = (EditText) view.findViewById(R.id.goal_genre_edittext);
			String editGoalGenreString = editGoalGenre.getText().toString();

			// 目標EditTextコンポーネントを取得
			EditText editGoal = (EditText) view.findViewById(R.id.goal_edittext);
			String editGoalString = editGoal.getText().toString();

			// 目標数EditTextコンポーネントを取得
			EditText editGoalNumber = (EditText) view.findViewById(R.id.goal_num_edittext);
			String editGoalNumberString = editGoalNumber.getText().toString();

			//DatePickerを設定
	        DatePicker datePicker1 = (DatePicker)view.findViewById(R.id.datePicker1);

	        int year = datePicker1.getYear();
	        int month = datePicker1.getMonth();
	        int day = datePicker1.getDayOfMonth();

//			// 達成期限(年)EditTextコンポーネントを取得
//			EditText editDueYear = (EditText) view.findViewById(R.id.editgoaldueyear);
//			String editDueYearString = editDueYear.getText().toString();
//
//			// 達成期限(月)EditTextコンポーネントを取得
//			EditText editDueMonth = (EditText) view.findViewById(R.id.editgoaldueMonth);
//			String editDueMonthString = editDueMonth.getText().toString();
//
//			// 達成期限(日)EditTextコンポーネントを取得
//			EditText editDueDay = (EditText) view.findViewById(R.id.editgoaldueday);
//			String editDueDayString = editDueDay.getText().toString();

			// 月と日が1文字だった場合先頭に0を付ける
			String editMonth = String.format(Locale.JAPANESE, "%2s", month).replace(" ", "0");
			String editDay = String.format(Locale.JAPANESE, "%2s", day).replace(" ", "0");

			String editGoalDueString = year + editMonth + editDay;

			// 目標MEMOEditTextコンポーネントを取得
			EditText editMemo = (EditText) view.findViewById(R.id.goal_memo_edittext);
			String editMemoString = editMemo.getText().toString();

			//現在日付を取得(達成期限のチェック用)
			Date date = new Date();
			int nowDate = Integer.parseInt(sdf1.format(date));
			int dueDate = Integer.parseInt(editGoalDueString);

			//////////////////////////////////////////
			// 入力チェック
			//////////////////////////////////////////
			if(editGoalGenreString.isEmpty()){

				// <目標ジャンルが未入力の場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示
				inputCheckMsg.setText(R.string.input_goal_genre_null_msg);

			} else if (editGoalGenreString.length() > 20) {

				// <目標ジャンルが20桁より多い場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示
				inputCheckMsg.setText(R.string.input_goal_genre_msg);

			} else if (editGoalString.length() > 150) {

				// <目標が150文字より多い場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示
				inputCheckMsg.setText(R.string.input_goal_msg);

			} else if (editGoalNumberString.isEmpty()) {

				// <目標数が未入力の場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示
				inputCheckMsg.setText(R.string.input_goal_number_msg);

			} else if (editGoalNumberString.length() > 6) {

				// <目標数が6文字より多い場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示
				inputCheckMsg.setText(R.string.input_goal_number_over_msg);

			} else if (editGoalDueString.length() != 8) {

				// <達成期限が8文字でない場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示
				inputCheckMsg.setText(R.string.input_goal_due_miss_msg);

			} else if (nowDate > dueDate) {

				// <達成期限が現在日付より過去の場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示
				inputCheckMsg.setText(R.string.input_goal_due_msg);

			} else {

				// <エラー項目がない場合>

				// 登録フラグを立てる
				registFlg = true;

			}

			//////////////////////////////////////////
			// DB登録処理
			//////////////////////////////////////////

			// 登録フラグチェック
			if(registFlg){

				// <入力チェックを通過した場合>

				// 目標数EditTextコンポーネントをint型変数に変換
				int gNumber = Integer.parseInt(editGoalNumberString);


				// Bundleから目標IDを取得
				int goalId = getArguments().getInt("goalId");

				// DBに追加するデータをセット
				ContentValues values = new ContentValues();
				values.put(MySQLiteOpenHelper.GOAL_ID, goalId);
				values.put(MySQLiteOpenHelper.G_NUMBER, gNumber);
				values.put(MySQLiteOpenHelper.M_GENRE, editGoalGenreString);
				values.put(MySQLiteOpenHelper.GOAL, editGoalString);
				values.put(MySQLiteOpenHelper.G_DUE, editGoalDueString);
				values.put(MySQLiteOpenHelper.G_MEMO, editMemoString);

				// 登録済みデータ存在チェック
				boolean result = false;
				if(mokuhyoJohoBean == null){

					// <登録済みデータが存在しない場合>

					// DBにデータを追加
					result = GoalDAO.insert(getActivity(), values);

				}else{

					// <登録済みデータが存在する場合>

					// DBの登録済みデータを更新
					result = GoalDAO.update(getActivity(), values, goalId);

				}

				// DB処理結果判定
				if(result){

					// <データ登録が成功した場合>

					// トーストを表示
					String registMsg = getString(R.string.regist_msg);
					Toast.makeText(getActivity(), registMsg, Toast.LENGTH_LONG).show();

					// コールバックメソッドを呼ぶ
//					callback.insertRegisteredDataOnCallback(editGoalNumString);

					// ダイアログを閉じる
					dismiss();

				}

			}

		}

	}

//	/**
//	 * コールバックインターフェース
//	 */
//	public interface GoalEditCallback extends EventListener {
//
//		/** DB登録後、目標登録Tabに値を追加する */
//		public void insertGoalDataOnCallback(String achievements);
//
//	}
//
//	/**
//	 * コールバック設定メソッド
//	 */
//	public void setCallback(GoalEditCallback callback){
//		this.callback = callback;
//	}

}
