package mokuhyouKanriApp.dialog.fragment;


import android.R;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import mokuhyouKanriApp.bean.dataMokuhyoJohoBean;
import mokuhyouKanriApp.dialog.fragment.AchieveEditDialog.DeleteAchieveAdapter;
import mokuhyouKanriApp.dialog.fragment.AchieveEditDialog.RegistAchieveAdapter;

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
	private String mGenre = null;

	/** 目標 */
	private String goal = null;

	/** 目標数 */
	private long gNumber = 0;

	/** 達成期限 */
	private String gDue = null;

	/** 目標メモ */
	private String gMemo = null;
	
	/** dataMokuhyoJohoBeanインスタンス */
	private dataMokuhyoJohoBean mokuhyoJohoBean = null;

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

		// バンドルから年月日文字列を取得
		this.mGenre = getArguments().getString("mGenre");
		this.goal = getArguments().getString("goal");
		this.gNumber = getArguments().getLong("gNumber");
		this.gDue = getArguments().getString("gDue");
		this.gMemo = getArguments().getString("gMemo");

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

		// 選択日付表示テキストビューを取得し、選択日付をセット
		TextView mGenreText = (TextView) this.view.findViewById(R.id.editgoalgenre);
		selectedDate.setText(this.year + "年" + this.month + "月" + this.date + "日");

		// DB検索結果nullチェック
		if(this.mokuhyoJohoBean != null){

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

}
