package com.example.nestorso.vr;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private VrVideoView videoWidgetView;
    private SeekBar seekBar;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private void setVrVideo(){
        videoWidgetView = (VrVideoView) findViewById(R.id.video_view);
        videoWidgetView.setEventListener(new VrVideoEventListener(){

            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                seekBar.setMax((int) videoWidgetView.getDuration());
            }

            @Override
            public void onLoadError(String errorMessage) {
                super.onLoadError(errorMessage);
                Toast.makeText(MainActivity.this, "Error al cargar video", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick() {
                super.onClick();
                if(isPaused)
                    videoWidgetView.playVideo();
                else
                    videoWidgetView.pauseVideo();
                isPaused=!isPaused;

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
        new ReproducirVideo().execute("");
    }

    private class ReproducirVideo extends AsyncTask<String, String, String>{



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                VrVideoView.Options options = new VrVideoView.Options();
                options.inputType = VrVideoView.Options.TYPE_MONO;
                options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
                videoWidgetView.loadVideo(Uri.parse("http://video1.nytimes.com/video/360-demo/cool.mp4"), options);
                //videoWidgetView.loadVideo(Uri.parse("http://techslides.com/demos/sample-videos/small.mp4"), options);
                //videoWidgetView.loadVideo(Uri.parse("https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"), options);
                videoWidgetView.playVideo();
            }catch (IOException ex){
                ex.printStackTrace();
            }
            return null;
        }
    }
}
