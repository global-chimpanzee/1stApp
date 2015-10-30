package mokuhyouKanriApp.fragment;

import mokuhyouKanriApp.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class EditGoalTab extends Fragment {

	/**
	 * 目標登録画面フラグメント
	 *
	 * @author global.chimpanzee
	 * @version 1.0
	 * @since	2015
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// レイアウトファイル"fragment_goal_tab.xml"をセットしたViewコンポーネントを取得
		View view = inflater.inflate(R.layout.fragment_goal_tab, container, false);

		//////////////////////////////////
		// ここにDB検索処理を記述
		//////////////////////////////////

		// 目標ジャンルをセット
		TextView goalGenre = (TextView) view.findViewById(R.id.goal_genre_text);
		goalGenre.setText("英単語学習");

		// 目標をセット
		TextView goal = (TextView) view.findViewById(R.id.goal_text);
		goal.setText("速読英単語の単語2000語を制覇する");

		// 目標数をセット
		TextView goalNum = (TextView) view.findViewById(R.id.goal_num_text);
		goalNum.setText("2000");

		// 達成期限をセット
		TextView due = (TextView) view.findViewById(R.id.due_text);
		due.setText("2015年12月31日");

		// memoをセット
		TextView memo = (TextView) view.findViewById(R.id.memo_text);
		memo.setText("ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");

		// Viewコンポーネントを返却
		return view;

	}

}
