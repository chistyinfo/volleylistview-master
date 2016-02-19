package greendust.livefootball.kbv;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import greendust.livefootball.MainActivity;
import greendust.livefootball.R;
import greendust.livefootball.font.FontelloTextView;


public class SplashScreenLauncherActivity extends Activity {

	static final int SPLASH_SHOW_TIME = 4000;

	public static final String SPLASH_SCREEN_OPTION = "com.example.chisty.splashscreen.SplashScreenLauncherActivity";
	public static final String SPLASH_SCREEN_OPTION_1 = "Fade in + Ken Burns";
	public static final String SPLASH_SCREEN_OPTION_3 = "Down + fade in + Ken Burns";
	
	private KenBurnsView mKenBurns;
	private FontelloTextView mLogo;
	private TextView welcomeText;

	SharedPreferences sharedpreferences;
	boolean rememberMe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); //Removing ActionBar
		setContentView(R.layout.activity_splash_screen);

		sharedpreferences = getSharedPreferences("sharedpreferences_data", Context.MODE_PRIVATE);
		rememberMe = sharedpreferences.getBoolean("remember_me",false);


		new BackgroundSplashTask().execute();
		
		mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
		mLogo = (FontelloTextView) findViewById(R.id.logo);
		welcomeText = (TextView) findViewById(R.id.welcome_text);

		//here we can define which splash screen will be shown

		String category = SPLASH_SCREEN_OPTION_3;
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(SPLASH_SCREEN_OPTION)) {
			category = extras.getString(SPLASH_SCREEN_OPTION, SPLASH_SCREEN_OPTION_3);
		}
		setAnimation(category);
	}
	
	/** Animation depends on category.
	 * */
	private void setAnimation(String category) {
		if (category.equals(SPLASH_SCREEN_OPTION_1)) {
			mKenBurns.setImageResource(R.drawable.background_media);
			animation1();

		} else if (category.equals(SPLASH_SCREEN_OPTION_3)) {
			mKenBurns.setImageResource(R.drawable.splash_screen_option_three);
			animation2();
			animation3();
		}
	}

	private void animation1() {
		ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
		scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleXAnimation.setDuration(1200);
		ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
		scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleYAnimation.setDuration(1200);
		ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
		alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		alphaAnimation.setDuration(1200);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
		animatorSet.setStartDelay(500);
		animatorSet.start();
	}
	
	private void animation2() {
		mLogo.setAlpha(1.0F);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
		mLogo.startAnimation(anim);
	}
	
	private void animation3() {
		ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
		alphaAnimation.setStartDelay(1700);
		alphaAnimation.setDuration(500);
		alphaAnimation.start();

	}


		private class BackgroundSplashTask extends AsyncTask<Void, Void, Void> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... arg0) {

				// I have just give a sleep for this thread
				// if you want to load database, make
				// network calls, load images
				// you can do here and remove the following
				// sleep

				// do not worry about this Thread.sleep
				// this is an async task, it will not disrupt the UI
				try {
					Thread.sleep(SPLASH_SHOW_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
					Intent intent = new Intent(SplashScreenLauncherActivity.this, MainActivity.class);
					startActivity(intent);





			}
	}



}
