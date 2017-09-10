package com.example.nestorso.vr.nativo;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.nestorso.vr.MainActivity;
import com.example.nestorso.vr.R;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class NativoActivity extends AppCompatActivity{

    private VrVideoView videoWidgetView;
    private SeekBar seekBar;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativo);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        setVrVideo();
    }



    @Override
    protected void onPause() {
        super.onPause();
        videoWidgetView.pauseRendering();
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoWidgetView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        videoWidgetView.shutdown();
        super.onDestroy();
    }

    private void setVrVideo() {
        videoWidgetView = (VrVideoView) findViewById(R.id.video_view);
        videoWidgetView.setEventListener(new VrVideoEventListener() {

            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                seekBar.setMax((int) videoWidgetView.getDuration());
            }

            @Override
            public void onLoadError(String errorMessage) {
                super.onLoadError(errorMessage);
                Toast.makeText(NativoActivity.this, "Error al cargar video", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick() {
                super.onClick();
                if (isPaused)
                    videoWidgetView.playVideo();
                else
                    videoWidgetView.pauseVideo();
                isPaused = !isPaused;

            }


            @Override
            public void onCompletion() {
                super.onCompletion();
                videoWidgetView.seekTo(0);
            }

            @Override
            public void onNewFrame() {
                super.onNewFrame();
                seekBar.setProgress((int) videoWidgetView.getCurrentPosition());
            }
        });
        try {
            VrVideoView.Options options = new VrVideoView.Options();
            options.inputType = VrVideoView.Options.TYPE_MONO;
            options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
            videoWidgetView.loadVideo(Uri.parse(getIntent().getExtras().getString(MainActivity.EXTRA_URL,"")), options);
            //http://video1.nytimes.com/video/360-demo/cool.mp4
            //videoWidgetView.loadVideo(Uri.parse("http://techslides.com/demos/sample-videos/small.mp4"), options);
            //videoWidgetView.loadVideo(Uri.parse("https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"), options);
            videoWidgetView.playVideo();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
