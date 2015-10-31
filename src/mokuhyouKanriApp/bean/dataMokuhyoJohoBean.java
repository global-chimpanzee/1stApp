package mokuhyouKanriApp.bean;


/**
 * 目標登録情報格納クラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
final public class dataMokuhyoJohoBean {

	private  int goalId;
	private  String goalGenre;
	private  int goalNumber;
	private  String goal;
	private  String goalDue;
	private  String memo;
	private  String timeStamp;

    /**
	 * コンストラクタクラス
	 */
	public dataMokuhyoJohoBean(int goalId, String goalGenre, String goal, int goalNumber,
			String goalDue, String memo){

		this.goalId = goalId;
		this.goalGenre = goalGenre;
		this.goal = goal;
		this.goalNumber = goalNumber;
		this.goalDue = goalDue;
		this.memo = memo;
//		this.timeStamp = timeStamp;

	}

	public int getGoalId() {
		return goalId;
	}

	public void setGoalId(int goalId) {
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

	public int getGoalNumber() {
		return goalNumber;
	}

	public void setGoalNumber(int goalNumber) {
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
