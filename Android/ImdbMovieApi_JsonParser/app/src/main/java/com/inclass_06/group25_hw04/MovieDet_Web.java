package com.inclass_06.group25_hw04;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class MovieDet_Web extends AppCompatActivity {

    WebView myWebview;

    private class HelloWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Movie WebView");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_movie_det_web);

        if (getIntent().getExtras() != null) {

            String web_url = getIntent().getExtras().getString("web_view_link");

            myWebview = (WebView) findViewById(R.id.webview);
            WebSettings webSettings = myWebview.getSettings();
            webSettings.setJavaScriptEnabled(true);



            myWebview.setWebViewClient(new HelloWebViewClient());
            myWebview.loadUrl(web_url);

        }
    }
}
