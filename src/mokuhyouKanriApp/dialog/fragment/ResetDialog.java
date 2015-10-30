package mokuhyouKanriApp.dialog.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ResetDialog extends DialogFragment {

	// 削除ボタンクリックリスナー
	private DialogInterface.OnClickListener okClickListener = null;

	// キャンセルボタンクリックリスナー
	private DialogInterface.OnClickListener cancelClickListener = null;

	/**
	 * リセットダイアログの表示
	 *
	 * @param savedInstanceState バンドル
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// ダイアログのタイトルをセット
		String title = "データのリセット";

		// ダイアログのメッセージをセット
		String message = "一度削除したデータは復元することができません。\n\n本当に全てのデータを削除してよろしいですか？\n";

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(title).setMessage(message)
				.setPositiveButton("削除する", this.okClickListener)
				.setNegativeButton("キャンセル", this.cancelClickListener);

		return builder.create();
	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		return inflater.inflate(R.layout.fragment_calendar_tab, container);

	}*/

	/**
	 * 削除ボタンクリックリスナーの登録
	 */
	public void setOnOkClickListener(DialogInterface.OnClickListener listener) {
		this.okClickListener = listener;
	}

	/**
	 * キャンセルボタンクリックリスナーの登録
	 */
	public void setOnCancelClickListener(DialogInterface.OnClickListener listener) {
		this.cancelClickListener = listener;
	}

}
