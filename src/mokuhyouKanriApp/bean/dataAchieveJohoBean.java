package mokuhyouKanriApp.bean;


/**
 * 日次実績情報格納クラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class dataAchieveJohoBean {

	private int goalId;
	private int achieveNumber;
	private String comment;
	private String selectDate;
	private String timeStamp;

	/**
	 * コンストラクタクラス
	 */
	public dataAchieveJohoBean(int goalId, int achieveNumber, String comment, String selectDate){

		this.goalId = goalId;
		this.achieveNumber = achieveNumber;
		this.comment = comment;
		this.selectDate = selectDate;
//		this.timeStamp = timeStamp;

	}

	public int getGoalId() {
		return goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public int getAchieveNumber() {
		return achieveNumber;
	}

	public void setAchieveNumber(int achieveNumber) {
		this.achieveNumber = achieveNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}




}
