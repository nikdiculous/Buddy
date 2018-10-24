package com.example.willi.buddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Youtube extends YouTubeBaseActivity {

    TextView mTxtTitle;
    Button btn_back;

    private static final String TAG = "YoutubeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        mTxtTitle = findViewById(R.id.YT_txtTitle);
        btn_back = findViewById(R.id.btn_back);

        final YouTubePlayerView youTubePlayerView = findViewById(R.id.YTplayer);
        Intent intent = getIntent();
        final String YTNAME = intent.getStringExtra("YTitemName");
        final String YTURL = intent.getStringExtra("YTitemURL");

        mTxtTitle.setText(YTNAME);
        String newURL = extractYTid(YTURL);

        playVideo(newURL, youTubePlayerView);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //extracting and return youtube ID from URL
    private String extractYTid(String videoUrl) {
        final String expression = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
            if (videoUrl == null || videoUrl.trim().length() <= 0){
                return null;
            }
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(videoUrl);
            try {
                if (matcher.find())
                    return matcher.group();
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
            return null;
        }


    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        youTubePlayerView.initialize("AIzaSyD4yFOVkXHu7WHoNV9bQ_ZjWd7CvOxH7Xk",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        String temp = "jvoGw_wwquM";
                        youTubePlayer.cueVideo(temp);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
