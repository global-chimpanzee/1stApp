package mokuhyouKanriApp.fragment;

import mokuhyouKanriApp.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * 進捗確認画面フラグメント
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class CheckRecordTab extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// レイアウトファイル"fragment_record_tab.xml"をセットしたViewコンポーネントを取得
		View view = inflater.inflate(R.layout.fragment_record_tab, container, false);

		//////////////////////////////////
		// ここにDB検索処理を記述
		//////////////////////////////////

		// 目標ジャンルをセット
		TextView goalGenre = (TextView) view.findViewById(R.id.achieve_num_edittext);
		goalGenre.setText("英単語学習");

		// 目標をセット
		TextView goal = (TextView) view.findViewById(R.id.comment_edittext);
		goal.setText("速読英単語の単語2000語を制覇する");

		// 目標進捗率をセット
		TextView percentage = (TextView) view.findViewById(R.id.percentage);
		percentage.setText("達成度 70％");

		// 目標進捗率プログレスバーを設定
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		// プログレスバーの最大値を設定
		progressBar.setMax(2000);
		// プログレスバーの値を設定
		progressBar.setProgress(1400);

		// 目標達成までの1日あたりの数をセット
		TextView numPerDay = (TextView) view.findViewById(R.id.numPerDay);
		numPerDay.setText("目標達成までに1日あたり33必要です");

		// Viewコンポーネントを返却
		return view;

	}

}
