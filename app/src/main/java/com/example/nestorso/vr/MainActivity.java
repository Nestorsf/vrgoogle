package com.example.nestorso.vr;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.nestorso.vr.nativo.NativoActivity;
import com.example.nestorso.vr.webview.WebViewActivity;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_URL="EXTRA_URL";

    private Button btnNativo;
    private Button btnWebView;
    private EditText txtUrl;

    //https://delight-vr.com/examples/elements/video/surf/get-barreled-in-tahiti-with-samsung-gear-vr_1080p.mp4


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNativo = (Button) findViewById(R.id.btnNativo);
        btnWebView = (Button) findViewById(R.id.btnWebView);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        btnWebView.setOnClickListener(this);
        btnNativo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnNativo && urlValida()) {
            abrirNativo();
        } else if (v == btnWebView && urlValida()) {
            abriWebView();
        }
    }

    private void abrirNativo() {
        Intent i = new Intent(this, NativoActivity.class);
        i.putExtra(EXTRA_URL, txtUrl.getText().toString());
        startActivity(i);
    }

    private void abriWebView(){
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra(EXTRA_URL, txtUrl.getText().toString());
        startActivity(i);
    }

    private boolean urlValida() {
        String url = txtUrl.getText().toString();
        if (URLUtil.isValidUrl(url))
            return true;
        Toast.makeText(this, getString(R.string.urlvalida), Toast.LENGTH_SHORT).show();
        return false;
    }
}