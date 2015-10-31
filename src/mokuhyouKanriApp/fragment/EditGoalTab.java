package mokuhyouKanriApp.fragment;


import java.util.List;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.activity.R.id;
import mokuhyouKanriApp.bean.dataMokuhyoJohoBean;
import mokuhyouKanriApp.dao.GoalDAO;
import mokuhyouKanriApp.dao.MySQLiteOpenHelper;
import mokuhyouKanriApp.dialog.fragment.GoalEditDialog;


public class EditGoalTab extends Fragment  implements DialogInterface.OnClickListener {


	/** SQLiteOpenHelper */
	MySQLiteOpenHelper mHelper = null;

	/** SQLiteDatabase */
	SQLiteDatabase mDb = null;

	/** データ格納用 */
	dataMokuhyoJohoBean mokuhyoJohoBean = null;

	/** 目標情報存在判定フラグ */
	boolean mHasDataNothingDB = true;

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


		// DBオープン処理
		mHelper = new MySQLiteOpenHelper(getActivity());
		mDb = mHelper.getWritableDatabase();

		// テーブル内のデータを全て取得
		final List<dataMokuhyoJohoBean> dataList = GoalDAO.goalSelect(mDb);

		mokuhyoJohoBean = null;

		if(dataList.size() == 0) {

			//目標が登録されていない場合、空白を格納する
			mokuhyoJohoBean = new dataMokuhyoJohoBean("", "", "", "", "");
		}
		else {
			// 最初のデータだけ取得する
			mokuhyoJohoBean = dataList.get(0);
			mHasDataNothingDB = false;
		}

        //編集ボタンを呼び出す
        View editButton = view.findViewById(R.id.register_button);
        ((Button)editButton).setOnClickListener(new GoalEditDialog());

      //目標ジャンル(登録情報)を呼び出す
        TextView editGoalGenreText = (TextView) view.findViewById(id.goal_genre_text);
        editGoalGenreText.setText(mokuhyoJohoBean.getGoalGenre());

        //目標ラベルを呼び出す
		//String goalLabel = getArguments().getString("goal");
        //TextView goalLabelText = (TextView) view.findViewById(id.goallabel);
        //goalLabelText.setText(goalLabel);

        //目標(登録情報)を呼び出す
        TextView editGoalText = (TextView) view.findViewById(id.goal_text);
        editGoalText.setText(mokuhyoJohoBean.getGoal());

        //目標数ラベルを呼び出す
		//String goalNumberLabel = getArguments().getString("goalnumber");
        //TextView goalNumberLabelText = (TextView) view.findViewById(id.goalnumberlabel);
        //goalNumberLabelText.setText(goalNumberLabel);

        //目標数(登録情報)を呼び出す
        TextView editGoalNumberText = (TextView) view.findViewById(id.goal_num_text);
        editGoalNumberText.setText(mokuhyoJohoBean.getGoalNumber());

        //達成期限ラベルを呼び出す
		//String goalDueLabel = getArguments().getString("goaldue");
        //TextView goalDueLabelText = (TextView) view.findViewById(id.goalduelabel);
        //goalDueLabelText.setText(goalDueLabel);

        //達成期限(登録情報)を呼び出す
        TextView editGoalDueText = (TextView) view.findViewById(id.due_text);
        editGoalDueText.setText(mokuhyoJohoBean.getGoalDue());

        //memoラベルを呼び出す
		//String memoLabel = getArguments().getString("memo");
        //TextView memoLabelText = (TextView) view.findViewById(id.memolabel);
        //memoLabelText.setText(memoLabel);

        //memo(登録情報)を呼び出す
        TextView editMemoText = (TextView) view.findViewById(id.memo_text);
        editMemoText.setText(mokuhyoJohoBean.getMemo());

		// Viewコンポーネントを返却
		return view;

	}

    /**
	 * ダイアログ表示
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO 自動生成されたメソッド・スタブ
			GoalEditDialog goalEditDialog = GoalEditDialog.newInstance(mokuhyoJohoBean, mHasDataNothingDB);
			goalEditDialog.show(getFragmentManager(), "goalEditDialog");
	}


}
