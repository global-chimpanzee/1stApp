package mokuhyouKanriApp.dialog.fragment;

import java.util.EventListener;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.dao.AchieveDAO;
import mokuhyouKanriApp.dao.GoalDAO;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * リセット画面ダイアログ
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class ResetDialog extends DialogFragment {

	/** コールバックインターフェース */
	private ResetCallback callback = null;

	/**
	 * リセットダイアログの表示
	 *
	 * @param savedInstanceState バンドル
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// ダイアログのタイトルをセット
		String title = getString(R.string.reset_dialog_title);

		// ダイアログのメッセージをセット
		String message = getString(R.string.reset_dialog_msg);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(title).setMessage(message).setPositiveButton("削除する", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// <削除ボタンリスナーの処理>

						// 日次実績テーブルのデータを全て削除
						boolean achieveResult = AchieveDAO.deleteAllDatas(getActivity());

						// 目標情報テーブルのデータを全て削除
						boolean goalResult = GoalDAO.deleteAllDatas(getActivity());

						// トーストを表示
						if(achieveResult || goalResult){

							String resetMsg = getString(R.string.reset_msg);
							Toast.makeText(getActivity(), resetMsg, Toast.LENGTH_LONG).show();

						}

						// アクティビティを再描画
						callback.redrawOnCallback();

					}

				})
				.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// <キャンセルボタンリスナーの処理>

					}

				});

		return builder.create();

	}

	/**
	 * コールバックインターフェース
	 */
	public interface ResetCallback extends EventListener {

		/** リセット後、アクティビティを再描画する */
		public void redrawOnCallback();

	}

	/**
	 * コールバック設定メソッド
	 */
	public void setCallback(ResetCallback callback){
		this.callback = callback;
	}

}
