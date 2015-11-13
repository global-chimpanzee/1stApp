package mokuhyouKanriApp.dialog.fragment;

import mokuhyouKanriApp.activity.R;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * お祝い画像表示ダイアログ
 *
 * @author global.chimpanzee
 * @version 1.0
 * @since	2015
 */
public class ShowImageDialog extends DialogFragment{

	private View view;

	/**
	 * インスタンスを生成し返却
	 *
	 * @return ShowImageDialogインスタンス
	 */
	public static ShowImageDialog newInstance() {

		// ShowImageDialogインスタンスの生成
		ShowImageDialog showImageDialog = new ShowImageDialog();

		// ダイアログフラグメントを返却
		return showImageDialog;

	}

	/**
	 * お祝い画像表示ダイアログフラグメントを表示する
	 * @param inflater LayoutInflaterインスタンス
	 * @param container ViewGroupインスタンス
	 * @param savedInstanceState バンドル
	 * @return お祝い画像表示画面View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// dialog_fragment_image.xmlを紐付ける
		this.view = inflater.inflate(R.layout.dialog_achieve_edit, container, false);

		// タイトルを非表示にする
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// 登録ボタンにリスナーをセット
		Button closeButton = (Button) this.view.findViewById(R.id.closebutton);
		CloseBtnAdapter cba = new CloseBtnAdapter();
		closeButton.setOnClickListener(cba);

		// ビューを返却
		return view;

	}

	/**
	 * ダイアログサイズ指定
	 *
	 * @param savedInstanceState バンドル
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		// ディスプレイ情報取得
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		lp.width = (int)(metrics.widthPixels * 0.95);
		lp.height = (int)(metrics.heightPixels * 0.95);
		dialog.getWindow().setAttributes(lp);

	}

	/**
	 * 閉じるボタンリスナークラス
	 */
	class CloseBtnAdapter implements OnClickListener {

		/**
		 * お祝い画像表示ダイアログを閉じる
		 */
		@Override
		public void onClick(View paramView) {

				// ダイアログを閉じる
				dismiss();

		}

	}

}
