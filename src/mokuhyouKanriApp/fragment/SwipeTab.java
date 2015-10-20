package mokuhyouKanriApp.fragment;

import mokuhyouKanriApp.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * タブ設定フラグメント
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class SwipeTab extends Fragment {

	/** ViewPagerコンポーネント */
	private ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// レイアウトファイル"fragment_swipe_tab.xml"をセットしたViewコンポーネントを取得
		View view = inflater.inflate(R.layout.fragment_swipe_tab, container, false);

		// ViewPagerコンポーネントを取得
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);

		// SwipePagerAdapterインスタンスを生成
		FragmentManager fm = getFragmentManager();
		SwipePagerAdapter swipePagerAdapter = new SwipePagerAdapter(fm);

		// アダプターをViewPagerコンポーネントにセット
		viewPager.setAdapter(swipePagerAdapter);

		// Viewコンポーネントを返却
		return view;

	}

	class SwipePagerAdapter extends FragmentPagerAdapter {

		/** 最大ページ数 */
		public static final int MAX_PAGE_NUM = 1000;

		public SwipePagerAdapter(FragmentManager fm){
			super(fm);
		}

		@Override
		public Fragment getItem(int position){

			Fragment fragment = new CalendarTab(position);

			return fragment;

		}

		@Override
		public int getCount(){
			return MAX_PAGE_NUM;
		}

	}

}
