package mokuhyouKanriApp.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.bean.DateDisplayObject;
import mokuhyouKanriApp.dao.AchieveDAO;
import mokuhyouKanriApp.dialog.fragment.AchieveEditDialog;
import mokuhyouKanriApp.logic.CalendarInfo;
import mokuhyouKanriApp.logic.DayTextViewInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * カレンダーフラグメント
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since 2015
 */
public class CalendarTab extends Fragment {

	/** Viewコンポーネント */
	private View view;

	/** 初回ページナンバー */
	private static int initPageNum = -1;

	/** 相対ページナンバー */
	private int relativePageNum;

	/** 年月表示テキストビュー */
	private TextView headerMonthText = null;

	/** 表示中の年 */
	private int displayedYear = 0;

	/** 表示中の月 */
	private int displayedMonth = 0;

	/** 現在の年 */
	private int currentYear = 0;

	/** 現在の月 */
	private int currentMonth = 0;

	/** 現在の日 */
	private int currentDate = 0;

	/** 日表示テキスト情報リスト */
	private List<DayTextViewInfo> dayTextList = new ArrayList<DayTextViewInfo>();

	/** 当月分登録済み実績マップ */
	private Map<String, String> map;

	/**
	 * コンストラクタ
	 */
	public CalendarTab(int count) {

		// 取得ページナンバーを初回のみフィールドに設定
		if (initPageNum == -1) {

			initPageNum = count;

		}

		// 相対ナンバーを設定
		relativePageNum = (count - initPageNum);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// レイアウトファイル"fragment_calendar_tab.xml"をセットしたViewコンポーネントを取得
		view = inflater.inflate(R.layout.fragment_calendar_tab, container,
				false);

		// 表示年月を計算し、フィールドにセット
		calculateDisplayedMonth();

		// 年月表示ヘッダーを取得
		this.headerMonthText = (TextView) this.view.findViewById(R.id.header_month_text);

		// 年月表示
		this.headerMonthText.setText(String.valueOf(this.displayedYear)
				+ "年" + String.valueOf(this.displayedMonth) + "月" );

		// DB検索
		String targetMonth = String.valueOf(displayedYear) + String.valueOf(displayedMonth);
		List<DateDisplayObject> displayObjectList = AchieveDAO.selectMonthDatas(getActivity(), targetMonth);

		// 検索結果取得値をマップにセット
		if(displayObjectList.size() != 0){

			// マップを生成
			this.map = new HashMap<String, String>();

			for(DateDisplayObject displayObject : displayObjectList){

				// 検索結果より実績年月日を取得し、日付を切り取る
				String fullDate = displayObject.getaDate();
				String date = fullDate.substring(fullDate.length() - 2);

				// マップに値をセット（例："07：46"（"日付：達成数"））
				this.map.put(date, String.valueOf(displayObject.getaNumber()));

			}

		}

		// カレンダー画面の各コンポーネントを初期化
		initializeControl();

		// 日付テキストの設定
		setCalendar();

		return view;

	}

	/**
	 * 表示月を計算し、フィールドにセットする
	 */
	private void calculateDisplayedMonth(){

		// Calendarインスタンスの取得
		Calendar cal = Calendar.getInstance();

		// 現在の年を取得
		this.displayedYear = cal.get(Calendar.YEAR);
		this.currentYear = this.displayedYear;

		// 現在の月を取得
		this.displayedMonth = (cal.get(Calendar.MONTH) + 1);
		this.currentMonth = this.displayedMonth;

		// 現在の日を取得
		this.currentDate = cal.get(Calendar.DATE);

		int monthAdjust = 0;
		int yearAdjust = 0;

		// 取得ページの表示月を計算
		this.displayedMonth += this.relativePageNum;

		if(this.displayedMonth%12 != 0){

			if(this.displayedMonth > 0){

				monthAdjust = this.displayedMonth%12;
				yearAdjust = (this.displayedMonth - monthAdjust)/12;

			}else{

				monthAdjust = (12 + this.displayedMonth%12);
				yearAdjust = (this.displayedMonth + (12 - monthAdjust))/12 - 1;

			}

		}else{

			monthAdjust = 12;
			yearAdjust = this.displayedMonth/12 - 1;

		}

		// 表示月をフィールドにセット
		this.displayedMonth = monthAdjust;
		this.displayedYear += yearAdjust;

	}

	/**
	 * 各カレンダーコントロール初期化メソッド
	 */
	private void initializeControl() {

		// 変数を初期化
		DayTextViewInfo info = null;

		// カレンダー第1週目
		info = new DayTextViewInfo(R.id.one_su_text, R.id.one_su_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_mo_text, R.id.one_mo_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_tu_text, R.id.one_tu_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_we_text, R.id.one_we_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_th_text, R.id.one_th_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_fr_text, R.id.one_fr_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_sa_text, R.id.one_sa_box);
		this.dayTextList.add(info);

		// カレンダー第2週目
		info = new DayTextViewInfo(R.id.two_su_text, R.id.two_su_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_mo_text, R.id.two_mo_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_tu_text, R.id.two_tu_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_we_text, R.id.two_we_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_th_text, R.id.two_th_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_fr_text, R.id.two_fr_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_sa_text, R.id.two_sa_box);
		this.dayTextList.add(info);

		// カレンダー第3週目
		info = new DayTextViewInfo(R.id.three_su_text, R.id.three_su_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_mo_text, R.id.three_mo_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_tu_text, R.id.three_tu_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_we_text, R.id.three_we_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_th_text, R.id.three_th_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_fr_text, R.id.three_fr_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_sa_text, R.id.three_sa_box);
		this.dayTextList.add(info);

		// カレンダー第4週目
		info = new DayTextViewInfo(R.id.four_su_text, R.id.four_su_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_mo_text, R.id.four_mo_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_tu_text, R.id.four_tu_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_we_text, R.id.four_we_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_th_text, R.id.four_th_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_fr_text, R.id.four_fr_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_sa_text, R.id.four_sa_box);
		this.dayTextList.add(info);

		// カレンダー第5週目
		info = new DayTextViewInfo(R.id.five_su_text, R.id.five_su_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_mo_text, R.id.five_mo_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_tu_text, R.id.five_tu_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_we_text, R.id.five_we_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_th_text, R.id.five_th_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_fr_text, R.id.five_fr_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_sa_text, R.id.five_sa_box);
		this.dayTextList.add(info);

		// カレンダー第6週目
		info = new DayTextViewInfo(R.id.six_su_text, R.id.six_su_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.six_mo_text, R.id.six_mo_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.six_tu_text, R.id.six_tu_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.six_we_text, R.id.six_we_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.six_th_text, R.id.six_th_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.six_fr_text, R.id.six_fr_box);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.six_sa_text, R.id.six_sa_box);
		this.dayTextList.add(info);

		// カウンターを初期化
		int counter = 0;

		/*
		 * テキストビューコンポーネントの設定を行い、
		 * DayTextViewInfoクラスに紐付ける
		 */
		for (int i = 0; i < 6; i++) {

			for (int k = 0; k < 7; k++) {

				// テキストビューコンポーネントを取得
				TextView textView = (TextView) view.findViewById(this.dayTextList.get(counter)
						.getTextViewId());

				// 背景を設定
				textView.setBackgroundResource(R.drawable.text_line);

				// 日曜日の場合、文字色を変更する
				if (k == 0) {
					textView.setTextColor(Color.parseColor("#FF6060"));
				}

				// 土曜日の場合、文字色を変更する
				if (k == 6) {
					textView.setTextColor(Color.parseColor("#3CDCB6"));
				}

				// テキストビューコンポーネントをDayTextViewInfoインスタンスにセット
				this.dayTextList.get(counter).setTextObject(textView);

				// カウンターをインクリメント
				counter++;

			}

		}

	}

	/**
	 * 日付テキスト設定メソッド
	 */
	private void setCalendar() {

		// テキスト表示情報初期化
		for(int i = 0 ; i < this.dayTextList.size(); i++) {

			DayTextViewInfo dtvi = this.dayTextList.get(i);

			// 当日or選択日判定
			if(dtvi.isNowDay() || dtvi.isSelected() ) {

				//当日と選択日は背景色を変更
				dtvi.getTextObject().setBackgroundResource(R.drawable.text_line);

			}

			// DayTextViewInfoインスタンスのフィールドを初期化
			dtvi.setNowDay(false);
			dtvi.setDayNum(0);
			dtvi.setSelected(false);
			dtvi.getTextObject().setText(dtvi.getDispString());

		}

		//カレンダーテーブル作成
		CalendarInfo ci = new CalendarInfo(displayedYear, displayedMonth);

		// 行カウンターを初期化
		int row = 0;

		// 列カウンターを初期化
		int col = 0;

		// 6×7でループ
		for(int i = 0 ; i < this.dayTextList.size(); i++) {

			DayTextViewInfo dtvi = this.dayTextList.get(i);

			// 日付テキスト設定
			if(ci.calendarMatrix[row][col] != 0) {

				// <日付を設定する日マスの場合>

				// 日付を設定
				dtvi.setDayNum(ci.calendarMatrix[row][col]);

				// 日付テキストをテキストビューコンポーネントにセット
				dtvi.getTextObject().setText(dtvi.getDispString());

				// ビューコンポーネント（LinearLayout）を取得
				LinearLayout linearLayout = (LinearLayout) view.findViewById(dtvi.getLinearLayoutId());

				// LinearLayoutにリスナーをセットする
				EditAchieveAdapter eaa = new EditAchieveAdapter(dtvi.getTextObject());
				linearLayout.setOnClickListener(eaa);

				// 当月分の登録済み実績をセット
				if(this.map != null){

					String dateString = String.format(Locale.JAPANESE, "%02d", ci.calendarMatrix[row][col]);
					if(this.map.containsKey(dateString)){

						// <マップに当該日のデータが存在する場合>

						// TextViewコンポーネントを生成
						TextView achieveLabel = new TextView(getContext());

						// 背景を設定
						achieveLabel.setBackgroundResource(R.drawable.registered_achievement);

						// 表示するラベルを設定（例：英単語：46）
						//achieveLabel.setText(getArguments().getString("mGenre") + "：" + this.map.get(dateString));
						achieveLabel.setText("英単語" + ":" + this.map.get(dateString));
						achieveLabel.setTextSize(8);

						// レイアウトにTextViewコンポーネントをセット
						linearLayout.addView(achieveLabel, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

					}

				}

				// 当日判定
				if(this.currentYear == this.displayedYear
					&& this.currentMonth == this.displayedMonth
					&& this.currentDate == ci.calendarMatrix[row][col]) {

					// 当日フラグを設定
					this.dayTextList.get(i).setNowDay(true);

					// 当日の背景色を変更
					dtvi.getTextObject().setBackgroundResource(R.drawable.text_now_line);

				}

			} else {

				// <日付を設定しない日マスの場合>

				// テキストビューインスタンスの親要素（LinearLayout, 日マス）を取得
				View view = (View) dtvi.getTextObject().getParent();

				// 背景を色をグレーに変更
				view.setBackgroundResource(R.drawable.date_gray_box);

			}

			// カウンターをインクリメント
			col += 1;
			if(col == 7){
				row += 1;
				col = 0;
			}

		}

	}

	/**
	 * 日マスを押したときのリスナークラス
	 */
	class EditAchieveAdapter implements OnClickListener{

		// カレンダー日付テキストビュー
		TextView textView = null;

		/**
		 * コンストラクタ
		 */
		EditAchieveAdapter(TextView textView){

			this.textView = textView;

		}

		/**
		 * 日マスを押したときの処理（目標編集ダイアログの表示）
		 */
		@Override
		public void onClick(View arg0) {

			// 年月日文字列を取得（例："20151101"）
			String year = String.valueOf(displayedYear);
			String month = String.valueOf(displayedMonth);
			String date = textView.getText().toString();

			// AchieveEditDialogインスタンスを生成
			AchieveEditDialog dialog = AchieveEditDialog.newInstance(year, month, date);

			// Bundle引数存在チェック
			if(getArguments() != null){

				// <引数が渡されてきた場合>

				// 引数をcalendarTabにセット
				dialog.setArguments(getArguments());

			}

			// ダイアログフラグメントを表示
			dialog.show(getChildFragmentManager().beginTransaction(), "AchieveEditDialog");

		}

	}

}
