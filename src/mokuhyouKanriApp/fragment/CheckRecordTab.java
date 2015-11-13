package mokuhyouKanriApp.fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.dao.AchieveDAO;
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

		// Bundle引数を格納する変数を初期化
		int goalId = 0;
		String mGenre = "";
		String goal = "";
		int gNumber = 0;
		String gDue = "";

		// Bundle引数存在チェック
		if(getArguments() != null){

			// <引数が渡されてきた場合>

			// 引数Bundleから取得
			goalId = getArguments().getInt("goalId");
			mGenre = getArguments().getString("mGenre");
			goal = getArguments().getString("goal");
			gNumber = getArguments().getInt("gNumber");
			gDue = getArguments().getString("gDue");

		}

		// 達成数の合計値をDBより取得
		int sumOfAchieve = AchieveDAO.getSumOfAchieveNum(getActivity(), goalId);

		// 目標ジャンルをセット
		TextView goalGenreText = (TextView) view.findViewById(R.id.achieve_num_edittext);
		goalGenreText.setText(mGenre);

		// 目標をセット
		TextView goalText = (TextView) view.findViewById(R.id.comment_edittext);
		goalText.setText(goal);

		// 目標進捗率をセット
		TextView percentageText = (TextView) view.findViewById(R.id.percentage);
		String percentageString = null;
		if(gNumber != 0){

			// <Bundle引数が存在する場合（目標数が0でない場合）>

			percentageString = getString(R.string.percentage_string, sumOfAchieve/gNumber);

		}else{

			// <Bundle引数が存在しない場合（目標数が0の場合）>

			percentageString = getString(R.string.percentage_string, gNumber);

		}
		percentageText.setText(percentageString);

		// 目標進捗率プログレスバーを設定
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		// プログレスバーの最大値を設定
		progressBar.setMax(gNumber);
		// プログレスバーの値を設定
		progressBar.setProgress(sumOfAchieve);

		// 目標達成までの1日あたりの数をセット
		TextView numPerDayText = (TextView) view.findViewById(R.id.numPerDay);
		String numPerDayString = null;
		if(!gDue.isEmpty()){

			// <Bundle引数が存在する場合（達成期限が空でない場合）>

			// 達成期限にスラッシュを入れる（例："2015/11/09"）
			StringBuilder sb = new StringBuilder();
			sb.append(gDue);
			sb.insert(6, "/");
			sb.insert(4, "/");

			// 達成期限のDateインスタンスを取得
			Date goalDue = null;
			try{
				goalDue = DateFormat.getDateInstance().parse(sb.toString());
			}catch(ParseException e){
				e.printStackTrace();
			}

			// 今日のDateインスタンスを取得
			Date today = new Date();

			// 達成期限と今日の日付の差の日数を取得
			int daysLeft = differenceDays(goalDue, today);

			numPerDayString = getString(R.string.num_per_day_string, (gNumber - sumOfAchieve)/daysLeft);

		}else{

			// <Bundle引数が存在しない場合（達成期限が空の場合）>

			numPerDayString = getString(R.string.num_per_day_string, 0);

		}
		numPerDayText.setText(numPerDayString);

		// Viewコンポーネントを返却
		return view;

	}

	/**
	 * 2つの日付の差を計算
	 *
	 * @param date1 期日
	 * @param date2 当日
	 * @return 2つの日付の差
	 */
	public int differenceDays(Date date1,Date date2) {

	    long datetime1 = date1.getTime();
	    long datetime2 = date2.getTime();
	    long one_date_time = 1000 * 60 * 60 * 24;
	    long diffDays = (datetime1 - datetime2) / one_date_time;
	    return (int)diffDays;

	}

}
