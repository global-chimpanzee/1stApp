package mokuhyouKanriApp.bean;

/**
 * 実績年月日と達成数を格納するBean
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class DateDisplayObject {

	/** 実績年月日 */
	private String aDate;

	/** 達成数 */
	private int aNumber;

	/**
	 * 実績年月日を取得します。
	 * @return 実績年月日
	 */
	public String getaDate() {
	    return aDate;
	}

	/**
	 * 実績年月日を設定します。
	 * @param aDate 実績年月日
	 */
	public void setaDate(String aDate) {
	    this.aDate = aDate;
	}

	/**
	 * 達成数を取得します。
	 * @return 達成数
	 */
	public int getaNumber() {
	    return aNumber;
	}

	/**
	 * 達成数を設定します。
	 * @param aNumber 達成数
	 */
	public void setaNumber(int aNumber) {
	    this.aNumber = aNumber;
	}

}
