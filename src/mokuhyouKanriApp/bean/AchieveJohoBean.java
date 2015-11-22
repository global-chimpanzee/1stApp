package mokuhyouKanriApp.bean;


/**
 * 日次実績情報格納クラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class AchieveJohoBean {

	/** 実績年月日 */
	private String aDate;

	/** 目標ID */
	private int aGoalId;

	/** 達成数 */
	private int aNumber;

	/** コメント */
	private String aComment;

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
	 * 目標IDを取得します。
	 * @return 目標ID
	 */
	public int getaGoalId() {
	    return aGoalId;
	}

	/**
	 * 目標IDを設定します。
	 * @param aGoalId 目標ID
	 */
	public void setaGoalId(int aGoalId) {
	    this.aGoalId = aGoalId;
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

	/**
	 * コメントを取得します。
	 * @return コメント
	 */
	public String getaComment() {
	    return aComment;
	}

	/**
	 * コメントを設定します。
	 * @param aComment コメント
	 */
	public void setaComment(String aComment) {
	    this.aComment = aComment;
	}

}
