package mokuhyouKanriApp.logic;

import android.widget.TextView;

/**
 * テキストコントロールクラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since 2015
 */
public class DayTextViewInfo {

	/** TextViewID */
	private int textViewId = 0;

	/** テキストオブジェクト */
	private TextView textObject = null;

	/** 設定日付 */
	private int dayNum = 0;

	/** 当日フラグ */
	private boolean isNowDay = false;

	/** 選択フラグ */
	private boolean isSelected = false;

	/**
	 * コンストラクタ
	 *
	 * @param controlId TextViewID
	 */
	public DayTextViewInfo(int controlId) {
		this.setTextViewId(controlId);
	}

	/**
	 * @return textViewId
	 */
	public int getTextViewId() {
		return textViewId;
	}

	/**
	 * @param textViewId
	 *            セットする textViewId
	 */
	public void setTextViewId(int textViewId) {
		this.textViewId = textViewId;
	}

	/**
	 * textObject 取得
	 *
	 * @return textObject
	 */
	public TextView getTextObject() {
		return textObject;
	}

	/**
	 * @param textObject
	 *            設定 textObject
	 */
	public void setTextObject(TextView textObject) {
		this.textObject = textObject;
	}

	/**
	 * dayNum 取得
	 *
	 * @return dayNum
	 */
	public int getDayNum() {
		return dayNum;
	}

	/**
	 * @param dayNum
	 *            設定 dayNum
	 */
	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}

	/**
	 * isNowDay 取得
	 *
	 * @return isNowDay
	 */
	public boolean isNowDay() {
		return isNowDay;
	}

	/**
	 * @param isNowDay
	 *            設定 isNowDay
	 */
	public void setNowDay(boolean isNowDay) {
		this.isNowDay = isNowDay;
	}

	/**
	 * isSelected 取得
	 *
	 * @return isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected
	 *            設定 isSelected
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 表示日付文字列を返す
	 *
	 * @return
	 */
	public String getDispString() {

		if (this.dayNum != 0) {

			return String.valueOf(this.dayNum) + "\n";

		} else {

			return "";

		}
	}
}