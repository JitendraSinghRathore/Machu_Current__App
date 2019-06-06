package com.example.adit.machu_picchu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.VideoView;

public class Splash_screen extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
      //  getActionBar().hide();
        getSupportActionBar().hide();


        videoView = (VideoView) findViewById(R.id.videoView);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.machu);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });

        videoView.start();
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, Navigation_Drawaer.class));
        finish();
    }
}































//        try {
//            VideoView videoHolder = new VideoView(this);
//            setContentView(videoHolder);
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.machu);
//            videoHolder.setVideoURI(video);
//
//            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                public void onCompletion(MediaPlayer mp) {
//                    jump();
//                }
//            });
//            videoHolder.start();
//        } catch (Exception ex) {
//            jump();
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        jump();
//        return true;
//    }
//
//    private void jump() {
//        if (isFinishing())
//            return;
//        startActivity(new Intent(this, Navigation_Drawaer.class));
//        finish();
//    }
//}