package mokuhyouKanriApp.activity;

import java.util.List;

import mokuhyouKanriApp.bean.dataMokuhyoJohoBean;
import mokuhyouKanriApp.dao.GoalDAO;
import mokuhyouKanriApp.dialog.fragment.ResetDialog;
import mokuhyouKanriApp.fragment.CheckRecordTab;
import mokuhyouKanriApp.fragment.EditGoalTab;
import mokuhyouKanriApp.fragment.SwipeTab;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

/**
 * メインアクティビティクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class MainActivity extends AppCompatActivity {

	/** TabLayoutコンポーネント */
	private TabLayout tabLayout;

	/** 目標情報テーブルの取得値 */
	private dataMokuhyoJohoBean goalInfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// タイトルバーを非表示にする
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

		// レイアウトファイル"activity_main.xml"を紐付ける
		setContentView(R.layout.activity_main);

		// 目標情報をDB検索し、取得値をフィールドにセット
		List<dataMokuhyoJohoBean> goalInfoList = GoalDAO.selectAllDatas(this);
		if(goalInfoList.size() != 0){
			this.goalInfo = goalInfoList.get(0);
		}

		// TabLayoutコンポーネントを取得
		tabLayout = (TabLayout) findViewById(R.id.tabLayout);

		// リスナーをセット
		tabLayout.setOnTabSelectedListener(new TabAdapter());

		// タブを追加（中央のタブをデフォルトに設定）
		tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.left_tab_icon), false);
		tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.center_tab_icon), true);
		tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.right_tab_icon), false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// FragmentManager fm = getSupportFragmentManager();
			ResetDialog resetDialog = new ResetDialog();
			resetDialog.show(getSupportFragmentManager(), "reset");
			// resetDialog.show(fm, "fragment");

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * TabAdapterインナークラス
	 */
	public class TabAdapter implements TabLayout.OnTabSelectedListener {

		/**
		 * タブ選択時処理メソッド
		 *
		 * @param tab 選択されたタブ
		 */
		@Override
		public void onTabSelected(TabLayout.Tab tab) {

			// FragmentTransactionインスタンスを取得
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			ImageView icon = null;

			// 選択されたタブに応じたフラグメントを表示
			switch (tab.getPosition()) {

			// 左のタブが選択された場合
			case 0:

				// タブのアイコンを変更
				icon = (ImageView) findViewById(R.id.leftIcon);
				icon.setImageResource(R.drawable.mokuhyou_active);

				//  EditGoalTabインスタンスを生成
				EditGoalTab editGoalTab = new EditGoalTab();

				// DBデータ存在チェック
				if(goalInfo != null){

					// <DB登録データが存在する場合>

					// フラグメントに渡す引数を設定
					// （目標ID、目標ジャンル、目標、目標数、達成期限、メモ）
					Bundle args0 = new Bundle();
					args0.putInt("goalId", goalInfo.getGoalId());
					args0.putString("mGenre", goalInfo.getmGenre());
					args0.putString("goal", goalInfo.getGoal());
					args0.putInt("gNumber", goalInfo.getgNumber());
					args0.putString("gDue", goalInfo.getgDue());
					args0.putString("gMemo", goalInfo.getgMemo());

					// フラグメントに引数をセット
					editGoalTab.setArguments(args0);

				}

				// 目標設定画面を表示
				ft.replace(R.id.main_container, editGoalTab, "rightTab");

				// コミット
				ft.commit();

				break;

			// 中央のタブが選択された場合
			case 1:

				// タブのアイコンを変更
				icon  = (ImageView) findViewById(R.id.centerIcon);
				icon.setImageResource(R.drawable.seika_active);

				// SwipeTabインスタンスを生成
				SwipeTab swipeTab = new SwipeTab();

				// DBデータ存在チェック
				if(goalInfo != null){

					// <DB登録データが存在する場合>

					// フラグメントに渡す引数を設定
					// （目標ID、目標ジャンル）
					Bundle args1 = new Bundle();
					args1.putInt("goalId", goalInfo.getGoalId());
					args1.putString("mGenre", goalInfo.getmGenre());

					// フラグメントに引数をセット
					swipeTab.setArguments(args1);

				}

				// SwipeTabフラグメントを表示
				ft.replace(R.id.main_container, swipeTab, "centerTab");

				// コミット
				ft.commit();

				break;

			// 右のタブが選択された場合
			case 2:

				// タブのアイコンを変更
				icon = (ImageView) findViewById(R.id.rightIcon);
				icon.setImageResource(R.drawable.tasseido_active);

				//  進捗確認画面を表示
				CheckRecordTab checkRecordTab = new CheckRecordTab();
				ft.replace(R.id.main_container, checkRecordTab, "leftTab");

				// コミット
				ft.commit();

				break;

			}

		}

		@Override
		public void onTabUnselected(TabLayout.Tab tab) {

			ImageView icon = null;

			// 選択が外されたタブのアイコンを変更
			switch (tab.getPosition()) {

			// 左のタブが選択された場合
			case 0:

				// タブのアイコンを変更
				icon = (ImageView) findViewById(R.id.leftIcon);
				icon.setImageResource(R.drawable.mokuhyou_non_active);

				break;

			// 中央のタブが選択された場合
			case 1:

				// タブのアイコンを変更
				icon  = (ImageView) findViewById(R.id.centerIcon);
				icon.setImageResource(R.drawable.seika_non_active);

				break;

			// 右のタブが選択された場合
			case 2:

				// タブのアイコンを変更
				icon = (ImageView) findViewById(R.id.rightIcon);
				icon.setImageResource(R.drawable.tasseido_non_active);

				break;

			}

		}

		@Override
		public void onTabReselected(TabLayout.Tab tab) {

		}

	}
}
