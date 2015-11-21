package mokuhyouKanriApp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.dataMokuhyoJohoBean;
import mokuhyouKanriApp.dialog.fragment.GoalEditDialog;

/**
 * 目標登録タブクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class EditGoalTab extends Fragment {

	/** 本フラグメントインスタンス */
	EditGoalTab thisFragment = this;

	/** データ格納用 */
	dataMokuhyoJohoBean mokuhyoJohoBean = null;

	/**
	 * コンストラクタ
	 */
	public EditGoalTab() {}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// レイアウトファイル"fragment_goal_tab.xml"をセットしたViewコンポーネントを取得
		View view = inflater.inflate(R.layout.fragment_goal_tab, container, false);

		// Bundle引数を格納する変数を初期化
		int goalId = 0;
		String mGenre = "";
		String goal = "";
		int gNumber = 0;
		String gDue = "";
		String gMemo = "";

		// Bundle引数存在チェック
		if(getArguments() != null){

			// <引数が渡されてきた場合>

			// 引数Bundleから取得
			goalId = getArguments().getInt("goalId");
			mGenre = getArguments().getString("mGenre");
			goal = getArguments().getString("goal");
			gNumber = getArguments().getInt("gNumber");
			gDue = getArguments().getString("gDue");
			gMemo = getArguments().getString("gMemo");

		}

		// 目標ジャンルをセット
		TextView goalGenreText = (TextView) view.findViewById(R.id.goal_genre_text);
		goalGenreText.setText(mGenre);

		// 目標をセット
		TextView goalText = (TextView) view.findViewById(R.id.goal_text);
		goalText.setText(goal);

		// 目標数をセット
		TextView goalNumberText = (TextView) view.findViewById(R.id.goal_num_text);
		goalNumberText.setText(String.valueOf(gNumber));

		// 達成期限をセット
		// 達成期限を画面表示用に加工する
		String gDueYear = "";
		String gDueMonth = "";
		String gDueDay = "";

		if(gDue.length() == 8){
			gDueYear = gDue.substring(0, 4);
			gDueMonth = gDue.substring(4, 4+2);
			gDueDay = gDue.substring(6, 6+2);

		}

		String processDue = gDueYear + "年" + gDueMonth + "月" + gDueDay + "日";
		TextView goalDueText = (TextView) view.findViewById(R.id.goal_due_text);
		goalDueText.setText(processDue);

		// 目標MEMOをセット
		TextView goalMemoText = (TextView) view.findViewById(R.id.goal_memo_text);
		goalMemoText.setText(gMemo);

		//各値をbeanにセットする
		mokuhyoJohoBean = new dataMokuhyoJohoBean();
		mokuhyoJohoBean.setGoalId(goalId);
		mokuhyoJohoBean.setmGenre(mGenre);
		mokuhyoJohoBean.setGoal(goal);
		mokuhyoJohoBean.setgNumber(gNumber);
		mokuhyoJohoBean.setgDue(gDue);
		mokuhyoJohoBean.setgMemo(gMemo);

		// 登録ボタンにリスナーをセット
		Button registButton = (Button) view.findViewById(R.id.register_button);
		EditGoalAdapter ega = new EditGoalAdapter();
		registButton.setOnClickListener(ega);


		// Viewコンポーネントを返却
		return view;

	}

	/**
	 * 編集ボタン押したときのリスナークラス
	 */
	class EditGoalAdapter implements OnClickListener{

//		// テキストビュー
//		TextView textView = null;
//
//		/**
//		 * コンストラクタ
//		 */
//		EditGoalAdapter(TextView textView){
//
//			this.textView = textView;
//
//		}

		/**
		 * 目標編集ダイアログの表示
		 */
		@Override
		public void onClick(View arg0) {

			//各値をbeanに格納する
			//

			// GoalEditDialogインスタンスを生成
			GoalEditDialog goalEditDialog = GoalEditDialog.newInstance(mokuhyoJohoBean);

			// Bundle引数存在チェック
			if(getArguments() != null){

				// <引数が渡されてきた場合>

				// 引数をgoalEditTabにセット
				goalEditDialog.setArguments(getArguments());

			}

//			// コールバックを設定
//			GoalEditDialog.setCallback(thisFragment);

			// ダイアログフラグメントを表示
			goalEditDialog.show(getChildFragmentManager().beginTransaction(), "GoalEditDialog");

		}

	}

	/**
	 * DB登録後、当該データをラベル表示するコールバックメソッド
	 */
//	@Override
//	public void insertGoalDataOnCallback(String achievements) {
//
//		// TextViewコンポーネントを生成
//		TextView achieveLabel = new TextView(getContext());
//
//		// 背景を設定
//		achieveLabel.setBackgroundResource(R.drawable.registered_achievement);
//
//		// 表示するラベルを設定（例：英単語：46）
//		//achieveLabel.setText(getArguments().getString("mGenre") + "：" + this.map.get(dateString));
//		achieveLabel.setText("英単語" + ":" + achievements);
//		achieveLabel.setTextSize(8);
//
//		// レイアウトにTextViewコンポーネントをセット
//		touchedLinearLayout.addView(achieveLabel, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//
//	}

}
