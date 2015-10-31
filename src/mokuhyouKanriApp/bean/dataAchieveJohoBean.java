package mokuhyouKanriApp.bean;


/**
 * 日次実績データ格納用クラス
 *
 */

public class dataAchieveJohoBean {

	private String goalId;
	private String achieveNumber;
	private String comment;
	private String selectYear;
	private String selectMonth;
	private String selectDate;
	private String selectDay;
	private String timeStamp;


	public dataAchieveJohoBean(String achieveNumber, String comment,
			String selectMonth, String selectDate, String selectDay){

//		this.goalId = goalId;
		this.achieveNumber = achieveNumber;
		this.comment = comment;
		this.selectMonth = selectMonth;
		this.selectDate = selectDate;
		this.selectDay = selectDay;
//		this.timeStamp = timeStamp;

	}

	public String getGoalId() {
		return goalId;
	}

	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}

	public String getAchieveNumber() {
		return achieveNumber;
	}

	public void setAchieveNumber(String achieveNumber) {
		this.achieveNumber = achieveNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(String selectYear) {
		this.selectYear = selectYear;
	}

	public String getSelectMonth() {
		return selectMonth;
	}

	public void setSelectMonth(String selectMonth) {
		this.selectMonth = selectMonth;
	}

	public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public String getSelectDay() {
		return selectDay;
	}

	public void setSelectDay(String selectDay) {
		this.selectDay = selectDay;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}




}
