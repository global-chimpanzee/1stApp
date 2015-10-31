package mokuhyouKanriApp.logic;

import java.util.Calendar;

/**
 * カレンダー情報作成クラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since 2015
 */
public class CalendarInfo {

	/** 対象の西暦年 */
	private int year;

	/** 対象の月 */
	private int month;

	/** 先頭曜日 */
	private int startDay;

	/** 月末日付 */
	private int lastDate;

	/** カレンダー情報配列 */
	public int[][] calendarMatrix = new int[6][7];

	/**
	 * コンストラクタ
	 *
	 * @param year
	 *            西暦年
	 * @param month
	 *            月
	 */
	public CalendarInfo(int year, int month) {

		this.year = year;
		this.month = month;
		this.createFields();

	}

	/**
	 * カレンダー情報配列作成
	 */
	private void createFields() {

		// Calendarインスタンスを取得
		Calendar calendar = Calendar.getInstance();

		// Calendarインスタンスのフィールドを初期化
		calendar.clear();

		// 月の初めの曜日を取得
		calendar.set(year, month - 1, 1);
		this.startDay = calendar.get(Calendar.DAY_OF_WEEK);

		// 月末の日付を取得
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		this.lastDate = calendar.get(Calendar.DATE);

		// カウンターを初期化
		int dayCount = 1;

		// 日にちカウント開始フラグを初期化
		boolean isStart = false;

		// 日にちカウント終了フラグを初期化
		boolean isEnd = false;

		// 6×7のループでカレンダー配列作成
		for (int i = 0; i < 6; i++) {
			for (int k = 0; k < 7; k++) {

				// 初期値セット
				this.calendarMatrix[i][k] = 0;

				// 先頭曜日確認
				if (isStart == false && (this.startDay - 1) == k) {

					// 日にちセット開始
					isStart = true;

				}

				// 日にちカウント開始フラグ判定
				if (isStart) {

					// 日にちカウント終了フラグ判定
					if (!isEnd) {

						// <日にちカウント終了フラグがfalseの場合>

						// カレンダー配列に日付をセット
						this.calendarMatrix[i][k] = dayCount;

						// カウンターをインクリメント
						dayCount++;

						// カウント終了チェック
						if (dayCount > this.lastDate) {

							isEnd = true;

						}

					}

				}

			}

		}

	}

}