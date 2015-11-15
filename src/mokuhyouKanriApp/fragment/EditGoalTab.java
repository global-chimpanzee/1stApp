package mokuhyouKanriApp.fragment;


import android.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
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

	/** データ格納用 */
	dataMokuhyoJohoBean mokuhyoJohoBean = null;

	/** 目標id */
	int goalId = 0;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// レイアウトファイル"fragment_goal_tab.xml"をセットしたViewコンポーネントを取得
		View view = inflater.inflate(R.layout.fragment_goal_tab, container, false);

		// Bundle引数を格納する変数を初期化
//		int goalId = 0;
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
		TextView goalGenreText = (TextView) view.findViewById(R.id.achieve_num_edittext);
		goalGenreText.setText(mGenre);

		// 目標をセット
		TextView goalText = (TextView) view.findViewById(R.id.comment_edittext);
		goalText.setText(goal);

		// 目標数をセット
		TextView goalNumberText = (TextView) view.findViewById(R.id.goal_num_text);
		goalNumberText.setText(gNumber);

		// 達成期限をセット
		/* 達成期限を画面表示用に加工する



		*/
		String processDue = "";
		TextView goalDueText = (TextView) view.findViewById(R.id.comment_edittext);
		goalDueText.setText(processDue);

		// 目標MEMOをセット
		TextView goalMemoText = (TextView) view.findViewById(R.id.memo_text);
		goalMemoText.setText(gMemo);

		// Viewコンポーネントを返却
		return view;

	}

	/**
	 * 編集ボタン押したときのリスナークラス
	 */
	class EditGoalAdapter implements OnClickListener{

		// テキストビュー
		TextView textView = null;

		/**
		 * コンストラクタ
		 */
		EditGoalAdapter(TextView textView){

			this.textView = textView;

		}

		/**
		 * 目標編集ダイアログの表示
		 */
		@Override
		public void onClick(View arg0) {

			//各値をbeanに格納する
			mokuhyoJohoBean.setGoalId(goalId);
			mokuhyoJohoBean.setmGenre();
			

			// AchieveEditDialogインスタンスを生成
			GoalEditDialog goalEditDialog = GoalEditDialog.newInstance(mokuhyoJohoBean);

			// Bundle引数存在チェック
			if(getArguments() != null){

				// <引数が渡されてきた場合>

				// 引数をgoalEditTabにセット
				goalEditDialog.setArguments(getArguments());

			}

			// ダイアログフラグメントを表示
			goalEditDialog.show(getChildFragmentManager().beginTransaction(), "GoalEditDialog");

		}

	}


}
