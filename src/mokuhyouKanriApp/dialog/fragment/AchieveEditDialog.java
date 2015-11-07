package mokuhyouKanriApp.dialog.fragment;

import java.util.Locale;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.EditAchieveBean;
import mokuhyouKanriApp.dao.AchieveDAO;
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

	/** ビューコンポーネント */
	View view = null;

	/** 選択年 */
	private String year = null;

	/** 選択月 */
	private String month = null;

	/** 選択日 */
	private String date = null;

	/** 選択日の完全表示（例："20151108"） */
	private String fullDate = null;

	/** EditAchieveBeanインスタンス */
	private EditAchieveBean editAchieveBean = null;

	/**
	 * AchieveEditDialogインスタンス生成し、値をセットする
	 *
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

		// 月と日が1文字だった場合先頭に0を付ける
		String monthString = String.format(Locale.JAPANESE, "%2s", this.month).replace(" ", "0");
		String dateString = String.format(Locale.JAPANESE, "%2s", this.date).replace(" ", "0");

		// 文字列を連結して年月日を作成
		this.fullDate = this.year + monthString + dateString;

		// DB検索で、達成数とコメントを取得
		this.editAchieveBean = AchieveDAO.selectForEditAchieve(getActivity(), this.fullDate);

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
		this.view = inflater.inflate(R.layout.dialog_achieve_edit, container, false);

		// タイトルを非表示にする
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// 選択日付表示テキストビューを取得し、選択日付をセット
		TextView selectedDate = (TextView) this.view.findViewById(R.id.selected_date);
		selectedDate.setText(this.year + "年" + this.month + "月" + this.date + "日");

		// DB検索結果nullチェック
		if(this.editAchieveBean != null){

			// <DB検索結果が存在する場合>

			// 達成数EditTextコンポーネントを取得し、初期値をセット
			EditText editGoalNum = (EditText) this.view.findViewById(R.id.achieve_num_edittext);
			editGoalNum.setText(String.valueOf(this.editAchieveBean.getaNumber()));

			// コメントEditTextコンポーネントを取得し、初期値をセット
			EditText editComment = (EditText) this.view.findViewById(R.id.comment_edittext);
			editComment.setText(this.editAchieveBean.getaComment());

		}

		// 登録ボタンにリスナーをセット
		Button registButton = (Button) this.view.findViewById(R.id.registerachievebutton);
		RegistAchieveAdapter raa = new RegistAchieveAdapter();
		registButton.setOnClickListener(raa);

		// 削除ボタンにリスナーをセット
		Button deleteButton = (Button) this.view.findViewById(R.id.deleteachievebutton);
		DeleteAchieveAdapter daa = new DeleteAchieveAdapter();
		deleteButton.setOnClickListener(daa);

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
	class RegistAchieveAdapter implements OnClickListener {

		/**
		 * 日次実績登録処理
		 */
		@Override
		public void onClick(View paramView) {

			// 達成数EditTextコンポーネントを取得し、int型変数に変換
			EditText editGoalNum = (EditText) view.findViewById(R.id.achieve_num_edittext);
			int aNumber = Integer.parseInt(editGoalNum.getText().toString());

			// コメントEditTextコンポーネントを取得
			EditText editComment = (EditText) view.findViewById(R.id.comment_edittext);
			String aComment = editComment.getText().toString();

			// Bundleから目標IDを取得
			int goalId = getArguments().getInt("goalId");

			// DBに追加するデータをセット
			ContentValues values = new ContentValues();
			values.put(MySQLiteOpenHelper.A_GOAL_ID, goalId);
			values.put(MySQLiteOpenHelper.A_NUMBER, aNumber);
			values.put(MySQLiteOpenHelper.A_COMMENT, aComment);

			// 登録済みデータ存在チェック
			boolean result = false;
			if(editAchieveBean == null){

				// <登録済みデータが存在しない場合>

				// 実績年月日を追加データにセット
				values.put(MySQLiteOpenHelper.A_DATE, fullDate);

				// DBにデータを追加
				result = AchieveDAO.insert(getActivity(), values);

			}else{

				// <登録済みデータが存在する場合>

				// DBの登録済みデータを更新
				result = AchieveDAO.update(getActivity(), values, fullDate);

			}

			// DB処理結果判定
			if(result){

				// <データ追加が成功した場合>

				// ダイアログを閉じる
				dismiss();

			}

		}

	}

	/**
	 * 削除ボタンリスナークラス
	 */
	class DeleteAchieveAdapter implements OnClickListener {

		/**
		 * 日次実績削除処理
		 */
		@Override
		public void onClick(View paramView) {

			// 選択日付のデータを削除
			boolean result = AchieveDAO.deleteOneData(getActivity(), fullDate);

			// 削除結果判定
			if(result){

				// <データ削除が成功した場合>

				// ダイアログを閉じる
				dismiss();

			}

		}

	}

}
