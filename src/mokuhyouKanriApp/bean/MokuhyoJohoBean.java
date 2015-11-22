package mokuhyouKanriApp.bean;


/**
 * 目標登録情報格納クラス
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
final public class MokuhyoJohoBean {

	/** 目標ID */
	private  int goalId;

	/** 目標ジャンル */
	private  String mGenre;

	/** 目標 */
	private String goal;

	/** 目標数 */
	private  int gNumber;

	/** 達成期限 */
	private String gDue;

	/** メモ */
	private String gMemo;

	/**
	 * 目標IDを取得します。
	 * @return 目標ID
	 */
	public int getGoalId() {
	    return goalId;
	}

	/**
	 * 目標IDを設定します。
	 * @param goalId 目標ID
	 */
	public void setGoalId(int goalId) {
	    this.goalId = goalId;
	}

	/**
	 * 目標ジャンルを取得します。
	 * @return 目標ジャンル
	 */
	public String getmGenre() {
	    return mGenre;
	}

	/**
	 * 目標ジャンルを設定します。
	 * @param mGenre 目標ジャンル
	 */
	public void setmGenre(String mGenre) {
	    this.mGenre = mGenre;
	}

	/**
	 * 目標を取得します。
	 * @return 目標
	 */
	public String getGoal() {
	    return goal;
	}

	/**
	 * 目標を設定します。
	 * @param goal 目標
	 */
	public void setGoal(String goal) {
	    this.goal = goal;
	}

	/**
	 * 目標数を取得します。
	 * @return 目標数
	 */
	public int getgNumber() {
	    return gNumber;
	}

	/**
	 * 目標数を設定します。
	 * @param gNumber 目標数
	 */
	public void setgNumber(int gNumber) {
	    this.gNumber = gNumber;
	}

	/**
	 * 達成期限を取得します。
	 * @return 達成期限
	 */
	public String getgDue() {
	    return gDue;
	}

	/**
	 * 達成期限を設定します。
	 * @param gDue 達成期限
	 */
	public void setgDue(String gDue) {
	    this.gDue = gDue;
	}

	/**
	 * メモを取得します。
	 * @return メモ
	 */
	public String getgMemo() {
	    return gMemo;
	}

	/**
	 * メモを設定します。
	 * @param gMemo メモ
	 */
	public void setgMemo(String gMemo) {
	    this.gMemo = gMemo;
	}

}
