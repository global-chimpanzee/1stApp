package makeApplication.pic;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MovingPic implements AnimationListener{

	private TranslateAnimation translate;
	private ImageView imageView;

	public void setImageView(ImageView imageView){
		this.imageView = imageView;
	}

	public void move(){

		//アニメーション動作の設定
		this.translate = new TranslateAnimation(0,0,0,-100);
		this.translate.setRepeatMode(Animation.REVERSE);
		this.translate.setRepeatCount(5);
		this.translate.setDuration(100);

		//アニメーションリスナーをセット
		this.translate.setAnimationListener(this);

		//アニメーションを開始
		this.imageView.setAnimation(translate);
		this.imageView.startAnimation(translate);

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO 自動生成されたメソッド・スタブ


	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
