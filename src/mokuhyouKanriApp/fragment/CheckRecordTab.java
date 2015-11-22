package mokuhyouKanriApp.fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.MokuhyoJohoBean;
import mokuhyouKanriApp.dao.AchieveDAO;
import mokuhyouKanriApp.dao.GoalDAO;
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

	/**
	 * 進捗確認画面フラグメントの表示
	 *
	 * @param inflater LayoutInflaterインスタンス
	 * @param container ViewGroupインスタンス
	 * @param savedInstanceState バンドル
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// レイアウトファイル"fragment_record_tab.xml"をセットしたViewコンポーネントを取得
		View view = inflater.inflate(R.layout.fragment_record_tab, container, false);

		// 変数を初期化
		int goalId = 0;
		String mGenre = "";
		String goal = "";
		int gNumber = 0;
		String gDue = "";

		// 目標情報DB検索
		List<MokuhyoJohoBean> goalInfoList = GoalDAO.selectAllDatas(getActivity());
		if(goalInfoList.size() != 0){

			// 取得値をフィールドにセット
			MokuhyoJohoBean goalInfo = goalInfoList.get(0);
			goalId = goalInfo.getGoalId();
			mGenre = goalInfo.getmGenre();
			goal = goalInfo.getGoal();
			gNumber = goalInfo.getgNumber();
			gDue = goalInfo.getgDue();

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

			// <目標情報が登録済みの場合（目標数が0でない場合）>

			// sumOfAchieveとgNumberをdouble型に変換
			double sumOfAchieveDouble = sumOfAchieve;
			double gNumberDouble = gNumber;
			// 達成率を計算し、int型へ変換
			double percentage = (sumOfAchieveDouble/gNumberDouble)*100;
			int percentageInt = (int) percentage;

			percentageString = getString(R.string.percentage_string, percentageInt);

		}else{

			// <目標情報が未登録の場合（目標数が0の場合）>

			percentageString = getString(R.string.percentage_string, gNumber);

		}
		percentageText.setText(percentageString);

		// 目標進捗率プログレスバーを設定
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		// プログレスバーの最大値を設定
		progressBar.setMax(gNumber);
		// プログレスバーの値を設定
		progressBar.setProgress(sumOfAchieve);

		// プログレスバーの現在値目盛りに達成総数をセット
		TextView currentScaleText = (TextView) view.findViewById(R.id.currentScale);
		currentScaleText.setText(getString(R.string.achieve_scale, sumOfAchieve));

		// プログレスバーの最大値目盛りに目標数をセット
		TextView maxScaleText = (TextView) view.findViewById(R.id.maxScale);
		maxScaleText.setText(String.valueOf(gNumber));

		// 目標達成までの1日あたりの数をセット
		TextView numPerDayText = (TextView) view.findViewById(R.id.numPerDay);
		String numPerDayString = null;
		if(!gDue.isEmpty()){

			// <目標情報が登録済みの場合（達成期限が空でない場合）>

			if((gNumber - sumOfAchieve) > 0){

				// <目標未達成の場合>

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
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPANESE);
				String nowString = sdf.format(now);
				Date today = new Date();
				try{
					today = DateFormat.getDateInstance().parse(nowString);
				}catch(ParseException e){
					e.printStackTrace();
				}

				// 達成期限と今日の日付の差の日数を取得
				// （今日もカウントするため1を足す）
				int daysLeft = differenceDays(goalDue, today) + 1;

				if(daysLeft >= 0){

					// <達成期限が過ぎていない場合>

					numPerDayString = getString(R.string.num_per_day_string, (gNumber - sumOfAchieve)/daysLeft);

				} else {

					//<達成期限が過ぎた場合>

					// メッセージ "達成期限が過ぎています"
					numPerDayString = getString(R.string.achievement_due_over);

				}

			} else {

				// <目標達成済みの場合>

				// メッセージ"目標を達成しました！"
				numPerDayString = getString(R.string.already_achieved_msg);

			}

		}else{

			// <目標情報が未登録の場合（達成期限が空の場合）>

			numPerDayString = "";

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
