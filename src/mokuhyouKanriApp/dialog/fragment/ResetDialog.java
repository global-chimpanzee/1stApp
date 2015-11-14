package mokuhyouKanriApp.dialog.fragment;

import mokuhyouKanriApp.activity.R;
import mokuhyouKanriApp.dao.AchieveDAO;
import mokuhyouKanriApp.dao.GoalDAO;
import mokuhyouKanriApp.fragment.CalendarTab;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

						// FragmentTransactionインスタンスを取得
						FragmentManager fm = getChildFragmentManager();
						FragmentTransaction ft = fm.beginTransaction();

						// SwipeTabフラグメントを表示
						CalendarTab c = new CalendarTab(500);
						ft.replace(R.id.main_container, c);
						//ft.addToBackStack(null);
						ft.commit();

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

}
