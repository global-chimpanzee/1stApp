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

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// タイトルバーを非表示にする
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

		// レイアウトファイル"activity_main.xml"を紐付ける
		setContentView(R.layout.activity_main);

		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);

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

				// 目標設定画面を表示
				EditGoalTab editGoalTab = new EditGoalTab();
				ft.replace(R.id.main_container, editGoalTab, "rightTab");

				// コミット
				ft.commit();

				break;

			// 中央のタブが選択された場合
			case 1:

				// タブのアイコンを変更
				icon  = (ImageView) findViewById(R.id.centerIcon);
				icon.setImageResource(R.drawable.seika_active);

				// SwipeTabフラグメントを表示
				SwipeTab swipeTab = new SwipeTab();
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
