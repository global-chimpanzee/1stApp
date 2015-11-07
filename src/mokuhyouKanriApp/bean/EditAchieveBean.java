package mokuhyouKanriApp.bean;

/**
 * 達成数とコメントを格納するBean（実績編集画面表示時に利用）
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class EditAchieveBean {

	/** 達成数 */
	private int aNumber;

	/** コメント */
	private String aComment;

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
