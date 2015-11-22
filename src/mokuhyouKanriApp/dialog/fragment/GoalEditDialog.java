package mokuhyouKanriApp.dialog.fragment;


import java.util.Calendar;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.MokuhyoJohoBean;
import mokuhyouKanriApp.dao.GoalDAO;
import mokuhyouKanriApp.dao.MySQLiteOpenHelper;
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

	/** 目標情報Bean */
	private MokuhyoJohoBean goalInfo = null;

	/** コールバックインターフェース */
	private GoalEditCallback callback = null;

	/**
	 * GoalEditDialogインスタンスの生成
	 *
	 * @return GoalEditDialogインスタンス
	 */
	public static GoalEditDialog newInstance() {

		// AchieveEditDialogインスタンスの生成
		GoalEditDialog goalEditDialog = new GoalEditDialog();

		// ダイアログフラグメントを返却
		return goalEditDialog;

	}

	/**
	 * DB検索を行う
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// 目標情報DB検索
		List<MokuhyoJohoBean> goalInfoList = GoalDAO.selectAllDatas(getActivity());
		if(goalInfoList.size() != 0){

			// 取得値をフィールドにセット
			this.goalInfo = goalInfoList.get(0);

		}

	}

	/**
	 * 目標編集画面ダイアログフラグメントを表示する
	 *
	 * @param inflater LayoutInflaterインスタンス
	 * @param container ViewGroupインスタンス
	 * @param savedInstanceState バンドル
	 * @return 目標編集画面View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// dialog_fragment_goal.xmlを紐付ける
		this.view = inflater.inflate(R.layout.dialog_fragment_goal, container, false);

		// タイトルを非表示にする
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// 変数をを格納する変数を初期化
		String mGenre = "";
		String goal = "";
		int gNumber = 0;
		String gDue = "";
		String gMemo = "";

		// 目標情報が登録済みの場合、値を取得
		if(goalInfo != null){

			mGenre = this.goalInfo.getmGenre();
			goal = this.goalInfo.getGoal();
			gNumber = this.goalInfo.getgNumber();
			gDue = this.goalInfo.getgDue();
			gMemo = this.goalInfo.getgMemo();

		}

		// 目標ジャンルをセット
		EditText editmGenre = (EditText) this.view.findViewById(R.id.goal_genre_edittext);
		editmGenre.setText(mGenre);

		// 目標をセット
		EditText editGoal = (EditText) this.view.findViewById(R.id.goal_edittext);
		editGoal.setText(goal);

		// 目標数をセット
		EditText editGoalNumber = (EditText) this.view.findViewById(R.id.goal_num_edittext);
		editGoalNumber.setText(String.valueOf(gNumber));

		// 達成期限をセット
		if(gDue.length() == 8){

			// 8桁の文字列から年月日を切り取る（例："20151121"　→　"2015", "11", "21"）
			String gDueYear = gDue.substring(0, 4);
			String gDueMonth = gDue.substring(4, 4+2);
			String gDueDay = gDue.substring(6, 6+2);

			// 文字列をint型に変換
			int year = Integer.parseInt(gDueYear);
			int month = Integer.parseInt(gDueMonth);
			int date = Integer.parseInt(gDueDay);

			// デートピッカーに初期値を設定する
			DatePicker datepicker = (DatePicker)this.view.findViewById(R.id.datePicker1);
			datepicker.updateDate(year, (month - 1), date);

		}

		// メモをセット
		EditText editgMemo = (EditText) this.view.findViewById(R.id.goal_memo_edittext);
		editgMemo.setText(gMemo);

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
		 *
		 * @param paramView クリックされたビュー
		 */
		@Override
		public void onClick(View paramView) {

			// 登録フラグ
			boolean registFlg = false;

			// 目標ジャンルEditTextコンポーネントを取得し、String型に変換
			EditText editmGenre = (EditText) view.findViewById(R.id.goal_genre_edittext);
			String editGoalGenreString = editmGenre.getText().toString();

			// 目標EditTextコンポーネントを取得し、String型に変換
			EditText editGoal = (EditText) view.findViewById(R.id.goal_edittext);
			String editGoalString = editGoal.getText().toString();

			// 目標数EditTextコンポーネントを取得し、String型に変換
			EditText editGoalNumber = (EditText) view.findViewById(R.id.goal_num_edittext);
			String editGoalNumberString = editGoalNumber.getText().toString();

			// DatePickerコンポーネントを取得し、int型に変換
	        DatePicker datePicker1 = (DatePicker) view.findViewById(R.id.datePicker1);
	        int year = datePicker1.getYear();
	        int month = datePicker1.getMonth();
	        int date = datePicker1.getDayOfMonth();

			// memo EditTextコンポーネントを取得
			EditText editgMemo = (EditText) view.findViewById(R.id.goal_memo_edittext);
			String editMemoString = editgMemo.getText().toString();

			// 現在日付を取得（達成期限のチェック用）
			Calendar today = Calendar.getInstance();
			// 目標期限をCalendar型に変換
			Calendar dueDate = Calendar.getInstance();
			dueDate.set(Calendar.YEAR, year);
			dueDate.set(Calendar.MONTH, month);
			dueDate.set(Calendar.DATE, date);

			//////////////////////////////////////////
			// 入力チェック
			//////////////////////////////////////////
			if(editGoalGenreString.isEmpty()){

				// <目標ジャンルが未入力の場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示（"目標ジャンルを入力してください"）
				inputCheckMsg.setText(getString(R.string.input_nothing_msg, getString(R.string.goal_genre)));

			} else if (editGoalGenreString.length() > 20) {

				// <目標ジャンルが20文字より多い場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示（”目標ジャンルは”20文字以内にしてください）
				inputCheckMsg.setText(getString(R.string.input_charover_msg, getString(R.string.goal_genre), 20));

			} else if (editGoalString.length() > 150) {

				// <目標が150文字より多い場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示（"目標は150文字以内にしてください"）
				inputCheckMsg.setText(getString(R.string.input_charover_msg, getString(R.string.goal), 150));

			} else if (editGoalNumberString.isEmpty()) {

				// <目標数が未入力の場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示（"目標数を入力してください"）
				inputCheckMsg.setText(getString(R.string.input_nothing_msg, getString(R.string.goal_num)));

			} else if (editGoalNumberString.length() > 6) {

				// <目標数が6桁より多い場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示（"目標数は6桁以内にしてください"）
				inputCheckMsg.setText(getString(R.string.input_lengthover_msg, getString(R.string.goal_num), 6));

			}  else if (dueDate.compareTo(today) < 0) {

				// <達成期限が現在日付より過去の場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示（"達成期限は今日以降の日付を指定してください"）
				inputCheckMsg.setText(R.string.input_incorrect_date_msg);

			} else if (editMemoString.length() > 200) {

				// <memoが200文字より多い場合>

				// 入力チェックメッセージを表示するテキストビューコンポーネントを取得
				TextView inputCheckMsg = (TextView) view.findViewById(R.id.input_check_msg);

				// 入力チェックメッセージを表示（"memoは200文字以内にしてください"）
				inputCheckMsg.setText(getString(R.string.input_charover_msg, getString(R.string.memo), 200));

			} else {

				// <エラー項目が無い場合>

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

				// 目標期限を文字列に加工
				// 月と日が1文字だった場合先頭に0を付ける
				String monthString = String.format(Locale.JAPANESE, "%2s", (month + 1)).replace(" ", "0");
				String dateString = String.format(Locale.JAPANESE, "%2s", date).replace(" ", "0");
				// 年月日を連結し、8桁の文字列を作成（例："20151121"）
				String editGoalDueString = year + monthString + dateString;


				// Bundleから目標IDを取得
				// 無い場合は目標IDは1とする
				int goalId = 1;
				if(getArguments() != null){

					goalId = getArguments().getInt("goalId");

				}

				// DBに追加するデータをセット
				ContentValues values = new ContentValues();
				values.put(MySQLiteOpenHelper.GOAL_ID, goalId);
				values.put(MySQLiteOpenHelper.M_GENRE, editGoalGenreString);
				values.put(MySQLiteOpenHelper.GOAL, editGoalString);
				values.put(MySQLiteOpenHelper.G_NUMBER, gNumber);
				values.put(MySQLiteOpenHelper.G_DUE, editGoalDueString);
				values.put(MySQLiteOpenHelper.G_MEMO, editMemoString);

				// 登録済みデータ存在チェック
				boolean result = false;
				if(goalInfo == null){

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

					// 目標情報Beanを生成
					MokuhyoJohoBean mokuhyoJohoBean = new MokuhyoJohoBean();
					mokuhyoJohoBean.setmGenre(editGoalGenreString);
					mokuhyoJohoBean.setGoal(editGoalString);
					mokuhyoJohoBean.setgNumber(gNumber);
					mokuhyoJohoBean.setgDue(editGoalDueString);
					mokuhyoJohoBean.setgMemo(editMemoString);

					// コールバックメソッドを呼ぶ
					callback.displayRegisteredDataOnCallback(mokuhyoJohoBean);

					// ダイアログを閉じる
					dismiss();

				}

			}

		}

	}

	/**
	 * コールバックインターフェース
	 */
	public interface GoalEditCallback extends EventListener {

		/** DB登録後、目標登録画面に登録値を表示する */
		public void displayRegisteredDataOnCallback(MokuhyoJohoBean mokuhyoJohoBean);

	}

	/**
	 * コールバック設定メソッド
	 */
	public void setCallback(GoalEditCallback callback){
		this.callback = callback;
	}

}
