
package com.sisar.youtubeapi.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.sisar.youtubeapi.R;
import com.sisar.youtubeapi.databinding.ActivityMainBinding;
import com.sisar.youtubeapi.utils.YouTubeHelper;

public class MainActivity extends YouTubeBaseActivity {
    private String API_KEY="AIzaSyA8dvV3aWISWO0ZsQVSkxh8zEcLANw59zQ";
    private static  final String TAG="MainActivity";
   // YouTubePlayerView mYouTubePlayerView;
   YouTubeHelper mYouTubeHelper;
    ActivityMainBinding mBinding;
    YouTubePlayer.OnInitializedListener monInitializedListener;
    String youtubeUrl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        Log.d(TAG,"onClick: IntialiZing,YouTube Player");
        mYouTubeHelper = new YouTubeHelper();
        Intent intent=getIntent();
        if(intent.hasExtra("mediatype")){
            youtubeUrl=intent.getStringExtra("mediatype");
        }

        monInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
                Log.d(TAG,"onClick: Done initizaling");
                String videoId = mYouTubeHelper.extractVideoIdFromUrl(youtubeUrl);
                player.cueVideo(videoId);
               // player.loadVideo("MnbWE8ZKeBg");
               // player.loadVideo(videoId);
                Log.d(TAG,videoId);
               /* player.setPlayerStateChangeListener(playerStateChangeListener);
                player.setPlaybackEventListener(playbackEventListener);

                if(!b){
                    player.cueVideo("MnbWE8ZKeBg");
                }*/

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG,"onClick: Failure initizaling");
            }
        };
        mBinding.youtubepay.initialize(API_KEY,monInitializedListener);

       /* mBinding.btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: IntialiZing,YouTube Player");
                mBinding.youtubepay.initialize(API_KEY,monInitializedListener);
            }
        });*/

    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener=new YouTubePlayer.PlaybackEventListener(){

        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener=new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
