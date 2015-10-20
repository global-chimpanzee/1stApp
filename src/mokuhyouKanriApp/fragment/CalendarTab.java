package mokuhyouKanriApp.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.logic.CalendarInfo;
import mokuhyouKanriApp.logic.DayTextViewInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	private static int relativePageNum;

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

		// カレンダー画面の各コンポーネントを初期化
		initializeControl();

		return view;

	}

	/**
	 * 各カレンダーコントロール初期化メソッド
	 */
	private void initializeControl() {

		// 年月表示ヘッダーを取得
		this.headerMonthText = (TextView) view.findViewById(R.id.header_month_text);

		// 変数を初期化
		DayTextViewInfo info = null;

		// カレンダー第1週目
		info = new DayTextViewInfo(R.id.one_su_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_mo_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_tu_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_we_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_th_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_fr_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.one_sa_text);
		this.dayTextList.add(info);

		// カレンダー第2週目
		info = new DayTextViewInfo(R.id.two_su_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_mo_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_tu_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_we_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_th_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_fr_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.two_sa_text);
		this.dayTextList.add(info);

		// カレンダー第3週目
		info = new DayTextViewInfo(R.id.three_su_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_mo_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_tu_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_we_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_th_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_fr_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.three_sa_text);
		this.dayTextList.add(info);

		// カレンダー第4週目
		info = new DayTextViewInfo(R.id.four_su_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_mo_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_tu_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_we_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_th_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_fr_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.four_sa_text);
		this.dayTextList.add(info);

		// カレンダー第5週目
		info = new DayTextViewInfo(R.id.five_su_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_mo_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_tu_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_we_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_th_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_fr_text);
		this.dayTextList.add(info);
		info = new DayTextViewInfo(R.id.five_sa_text);
		this.dayTextList.add(info);

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

		// カウンターを初期化
		int counter = 0;

		// 5×7のループ
		for (int i = 0; i < 5; i++) {

			for (int k = 0; k < 7; k++) {

				// テキストビューコンポーネントを取得
				TextView textView = (TextView) view.findViewById(this.dayTextList.get(counter)
						.getTextViewId());

				// リスナーをセットする
//				EditAchieveAdapter eaa = new EditAchieveAdapter();
//				textView.setOnClickListener(eaa);

				// 背景を設定
				textView.setBackgroundResource(R.drawable.text_day_line);

				// 日曜日の場合、背景色を変更する
				if (k == 0) {
					textView.setTextColor(Color.RED);
				}

				// 土曜日の場合、背景色を変更する
				if (k == 6) {
					textView.setTextColor(Color.BLUE);
				}

				// テキストビューインスタンスをDayTextViewInfoインスタンスにセット
				this.dayTextList.get(counter).setTextObject(textView);

				// カウンターをインクリメント
				counter++;

			}

		}

		this.SetCalendar(relativePageNum);

	}

	/**
	 * 日付テキスト設定メソッド
	 *
	 * @param offset 相対ページナンバー
	 */
	private void SetCalendar(int offset) {

		// 現在の表示月を取得ページの表示月に調整
		this.displayedMonth += offset;

		// 表示する月を修正
		if(displayedMonth > 12){

			this.displayedYear += 1;
			this.displayedMonth = 1;

		}else if(displayedMonth == 0){

			this.displayedYear -= 1;
			this.displayedMonth = 12;

		}

		// テキスト表示情報初期化
		for(int i = 0 ; i < this.dayTextList.size(); i++) {

			DayTextViewInfo tg = this.dayTextList.get(i);

			// 当日or選択日判定
			if(tg.isNowDay() || tg.isSelected() ) {

				//当日と選択日は背景色を変更
				tg.getTextObject().setBackgroundResource(R.drawable.text_day_line);

			}

			// DayTextViewInfoインスタンスのフィールドを初期化
			tg.setNowDay(false);
			tg.setDayNum(0);
			tg.setSelected(false);
			tg.getTextObject().setText(tg.getDispString());

		}

		//カレンダーテーブル作成
		CalendarInfo cl = new CalendarInfo(displayedYear, displayedMonth);

		// 行カウンターを初期化
		int row = 0;

		// 列カウンターを初期化
		int col = 0;

		// 5×7でループ
		for(int i = 0 ; i < this.dayTextList.size(); i++) {

			DayTextViewInfo tg = this.dayTextList.get(i);

			// 日付テキスト設定
			if(cl.calendarMatrix[row][col] != 0) {

				// 日付を設定
				tg.setDayNum(cl.calendarMatrix[row][col]);

				// 日付テキストをテキストビューコンポーネントにセット
				tg.getTextObject().setText(tg.getDispString());

				// 当日判定
				if(this.currentYear == this.displayedYear
					&& this.currentMonth == this.displayedMonth
					&& cl.calendarMatrix[row][col] == this.currentDate) {

					// 当日フラグを設定
					this.dayTextList.get(i).setNowDay(true);

					// 当日の背景色を変更
					tg.getTextObject().setBackgroundResource(R.drawable.text_now_line);

				}

			}

			// カウンターをインクリメント
			col += 1;
			if(col == 7){
				row += 1;
				col = 0;
			}

		}

		// 年月表示
		this.headerMonthText.setText(String.valueOf(this.currentYear)
				+ "年" + String.valueOf(this.currentMonth) + "月" );

	}

}