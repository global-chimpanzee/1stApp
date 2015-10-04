package makeApplication.main;

import makeApplication.omikuji4.R;
import makeApplication.pic.MovingPic;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView text;
	private EditText textEdit;
	private Button button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//メイン画面をセット
		setContentView(R.layout.main);

		//メイン画面の各コンポーネントを取得
		text = (TextView) this.findViewById(R.id.label1);
		textEdit = (EditText) this.findViewById(R.id.textBox1);
		button2 = (Button) this.findViewById(R.id.button2);

		//button2にリスナーをセット
		button2.setOnClickListener(this.new MyClickAdapter());

	}

	public void onButtonClick(View v){

		Editable s = textEdit.getText();
		text.setText("こんにちは" + s + "さん");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.omikuji4, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class MyClickAdapter implements OnClickListener{

		//画像ファイルオブジェクトを初期化
		MovingPic movingPic = new MovingPic();

		private Button button3;

		@Override
		public void onClick(View v){

			//画面を変更
			setContentView(R.layout.pic);

			//画像コンポーネントを取得し、movingPicにセット
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			this.movingPic.setImageView(imageView);

			//movingPicインスタンスのアニメーションを作動
			this.movingPic.move();

			button3 = (Button)findViewById(R.id.button3);

			button3.setOnClickListener(new OnClickListener(){

				public void onClick(View v){

					//アニメーション動作の設定
					TranslateAnimation translate = new TranslateAnimation(0,0,0,-100);
					translate.setRepeatMode(Animation.REVERSE);
					translate.setRepeatCount(5);
					translate.setDuration(100);

					//画像コンポーネントを取得し、アニメーションをセット
					ImageView imageView = (ImageView) findViewById(R.id.imageView1);
					imageView.setAnimation(translate);

					//アニメーションを開始
					imageView.startAnimation(translate);

				}

			});

			//アニメーション動作の設定
			/*TranslateAnimation translate = new TranslateAnimation(0,0,0,-100);
			translate.setRepeatMode(Animation.REVERSE);
			translate.setRepeatCount(5);
			translate.setDuration(100);*/

			//画像のインスタンスにアニメーションを設定
			/*ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setAnimation(translate);*/

		}

	}
}
