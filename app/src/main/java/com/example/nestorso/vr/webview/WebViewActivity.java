package com.example.nestorso.vr.webview;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.nestorso.vr.MainActivity;
import com.example.nestorso.vr.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);
        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);
        else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(getIntent().getExtras().getString(MainActivity.EXTRA_URL, ""));
            //https://vr.chromeexperiments.com/
            //https://www.youtube.com/embed/Z0180hEnKUI
            
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
}
