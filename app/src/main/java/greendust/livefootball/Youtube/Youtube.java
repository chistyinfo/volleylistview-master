package greendust.livefootball.Youtube;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import greendust.livefootball.R;


public class Youtube extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    // YouTube player view
    private YouTubePlayerView youTubeView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube);

//        AppInbox.startInbox(this, "SqIpX9zE2KBr4ZyXSGMjqlXZqm-x3GZsbYo0l5gepJo", "620939433634", null, null, null, "Live", "Cricket", "chistyinfo@gmail.com", Gender.MALE, 0);
//
//        linearLayout= (LinearLayout) findViewById(R.id.rzl);
//        final InboxView inbox = new InboxView(this);
//        linearLayout.addView(inbox);

//        AdView mAdView = (AdView) findViewById(R.id.adView0);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        // Initializing video player with developer key
        youTubeView.initialize(Config.DEVELOPER_KEY, this);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            Bundle extras = getIntent().getExtras();
            String url;
            if (extras != null) {
                url = extras.getString("url");

                // loadVideo() will auto play video
                // Use cueVideo() method, if you don't want to play it automatically
                player.loadVideo(url);


                // For fullscreen
//                player.setFullscreen(true);

//                // Hiding player controls
//                player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            }
        }


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}