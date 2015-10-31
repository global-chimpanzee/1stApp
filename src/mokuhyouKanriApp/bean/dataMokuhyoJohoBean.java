package mokuhyouKanriApp.bean;


/**
 * 目標登録データ格納用クラス
 *
 */
final public class dataMokuhyoJohoBean {

	private String goalId;
	private  String goalGenre;
	private  String goalNumber;
	private  String goal;
	private  String goalDue;
	private  String memo;
	private  String timeStamp;

	public dataMokuhyoJohoBean(String goalGenre, String goal, String goalNumber,
			String goalDue, String memo){

//		this.goalId = goalId;
		this.goalGenre = goalGenre;
		this.goal = goal;
		this.goalNumber = goalNumber;
		this.goalDue = goalDue;
		this.memo = memo;
//		this.timeStamp = timeStamp;

	}

	public String getGoalId() {
		return goalId;
	}

	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}


	public String getGoalGenre() {
		return goalGenre;
	}

	public void setGoalGenre(String goalGenre) {
		this.goalGenre = goalGenre;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getGoalNumber() {
		return goalNumber;
	}

	public void setGoalNumber(String goalNumber) {
		this.goalNumber = goalNumber;
	}

	public String getGoalDue() {
		return goalDue;
	}

	public void setGoalDue(String goalDue) {
		this.goalDue = goalDue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}



}
