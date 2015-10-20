package mokuhyouKanriApp.activity;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// レイアウトファイル"activity_main.xml"を紐付ける
		setContentView(R.layout.activity_main);

		// TabLayoutコンポーネントを取得
		tabLayout = (TabLayout) findViewById(R.id.tabLayout);

		// タブを追加
		tabLayout.addTab(tabLayout.newTab().setText("目標設定"));
		tabLayout.addTab(tabLayout.newTab().setText("成果記録"));
		tabLayout.addTab(tabLayout.newTab().setText("達成度"));

		// リスナーをセット
		tabLayout.setOnTabSelectedListener(new TabAdapter());

		// デフォルトタブを設定

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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * TabAdapterインナークラス
	 */
	class TabAdapter implements TabLayout.OnTabSelectedListener {

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

			// 選択されたタブに応じたフラグメントを表示
			switch (tab.getPosition()) {

			// 左のタブが選択された場合
			case 1:

				// 目標設定画面を表示
				EditGoalTab editGoalTab = new EditGoalTab();
				ft.add(editGoalTab, "rightTab");

				// コミット
				ft.commit();

				break;

			// 中央のタブが選択された場合
			case 0:

				// SwipeTabフラグメントを表示
				SwipeTab swipeTab = new SwipeTab();
				ft.add(swipeTab, "centerTab");

				// コミット
				ft.commit();

				break;

			// 右のタブが選択された場合
			case 2:

				//  進捗確認画面を表示
				CheckRecordTab checkRecordTab = new CheckRecordTab();
				ft.add(checkRecordTab, "leftTab");

				// コミット
				ft.commit();

				break;

			}

		}

		@Override
		public void onTabUnselected(TabLayout.Tab tab) {

		}

		@Override
		public void onTabReselected(TabLayout.Tab tab) {

		}

	}
}
