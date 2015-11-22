package mokuhyouKanriApp.fragment;

import java.util.List;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.MokuhyoJohoBean;
import mokuhyouKanriApp.dao.GoalDAO;
import mokuhyouKanriApp.dialog.fragment.GoalEditDialog;
import mokuhyouKanriApp.dialog.fragment.GoalEditDialog.GoalEditCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 目標登録画面フラグメント
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class EditGoalTab extends Fragment implements GoalEditCallback{

	/** 本フラグメントインスタンス */
	private EditGoalTab thisFragment = this;

	/** 目標ジャンルテキストビュー */
	private TextView goalGenreText = null;

	/** 目標テキストビュー */
	private TextView goalText = null;

	/** 目標数テキストビュー */
	private TextView goalNumberText = null;

	/** 達成期限テキストビュー */
	private TextView goalDueText = null;

	/** memoテキストビュー */
	private TextView memoText = null;

	/** 目標情報Bean */
	private MokuhyoJohoBean goalInfo = null;

	/**
	 * 目標登録画面フラグメントの表示
	 *
	 * @param inflater LayoutInflaterインスタンス
	 * @param container ViewGroupインスタンス
	 * @param savedInstanceStateインスタンス
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// レイアウトファイル"fragment_goal_tab.xml"をセットしたViewコンポーネントを取得
		View view = inflater.inflate(R.layout.fragment_goal_tab, container, false);

		// 変数をを格納する変数を初期化
		String mGenre = "";
		String goal = "";
		int gNumber = 0;
		String gDue = "";
		String gMemo = "";

		// 目標情報DB検索
		List<MokuhyoJohoBean> goalInfoList = GoalDAO.selectAllDatas(getActivity());
		if(goalInfoList.size() != 0){

			// 取得値をフィールドにセット
			this.goalInfo = goalInfoList.get(0);
			mGenre = this.goalInfo.getmGenre();
			goal = this.goalInfo.getGoal();
			gNumber = this.goalInfo.getgNumber();
			gDue = this.goalInfo.getgDue();
			gMemo = this.goalInfo.getgMemo();

		}

		// 目標ジャンルをセット
		this.goalGenreText = (TextView) view.findViewById(R.id.goal_genre_text);
		this.goalGenreText.setText(mGenre);

		// 目標をセット
		this.goalText = (TextView) view.findViewById(R.id.goal_text);
		this.goalText.setText(goal);

		// 目標数をセット
		this.goalNumberText = (TextView) view.findViewById(R.id.goal_num_text);
		if(gNumber != 0){

			this.goalNumberText.setText(String.valueOf(gNumber));

		} else {

			this.goalNumberText.setText("");

		}

		// DBに目標が登録済みの場合、達成期限を画面表示用に加工する
		String gDueForDisplay = "";
		if(!gDue.isEmpty()){

			gDueForDisplay = getString(R.string.year_month_date, gDue.substring(0, 4), gDue.substring(4, 4+2), gDue.substring(6, 6+2));

		}

		// 達成期限をセット
		this.goalDueText = (TextView) view.findViewById(R.id.goal_due_text);
		this.goalDueText.setText(gDueForDisplay);

		// memoをセット
		this.memoText = (TextView) view.findViewById(R.id.goal_memo_text);
		this.memoText.setText(gMemo);

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

		/**
		 * 目標編集ダイアログの表示
		 */
		@Override
		public void onClick(View arg0) {

			// GoalEditDialogインスタンスを生成
			GoalEditDialog goalEditDialog = GoalEditDialog.newInstance();

			// コールバックを設定
			goalEditDialog.setCallback(thisFragment);

			// ダイアログフラグメントを表示
			goalEditDialog.show(getChildFragmentManager().beginTransaction(), "GoalEditDialog");

		}

	}

	/**
	 * DB登録後、登録済み情報を表示するコールバックメソッド
	 */
	@Override
	public void displayRegisteredDataOnCallback(MokuhyoJohoBean mokuhyoJohoBean) {

		// 目標ジャンルを設定
		this.goalGenreText.setText(mokuhyoJohoBean.getmGenre());

		// 目標を設定
		this.goalText.setText(mokuhyoJohoBean.getGoal());

		// 目標数を設定
		this.goalNumberText.setText(String.valueOf(mokuhyoJohoBean.getgNumber()));

		// 達成期限文字列を加工（例："2015年11月21日"）
		String gDue = mokuhyoJohoBean.getgDue();
		String gDueForDisplay = getString(R.string.year_month_date, gDue.substring(0, 4), gDue.substring(4, 4+2), gDue.substring(6, 6+2));

		// 達成期限を設定
		this.goalDueText.setText(gDueForDisplay);

		// memoを設定
		this.memoText.setText(mokuhyoJohoBean.getgMemo());

	}

}
