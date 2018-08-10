package com.example.tanveerhoque.qfinder;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class QrGenActivity extends AppCompatActivity {
    private WebView wb;
    ProgressDialog progress;
    WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_gen);

//        webView = (WebView) findViewById(R.id.webview);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://www.google.com");
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        wb = (WebView) findViewById(R.id.webview);
        settings = wb.getSettings();

        settings.setJavaScriptEnabled(true);

        //url = "file:///android_asset/routine.html";



        wb.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);



        wb.getSettings().setBuiltInZoomControls(true);

        //wb.getSettings().setUseWideViewPort(true);

        wb.getSettings().setLoadWithOverviewMode(true);



        progress = new ProgressDialog(this);



        startWebView("file:///android_asset/qGen/index.html","");
    }

    private void startWebView(String url, String message) {



        progress.setMessage(message);

        progress.show();



        wb.setWebViewClient(new WebViewClient() {

            @Override

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;

            }



            @Override

            public void onPageFinished(WebView view, String url) {

                if (progress.isShowing()) {

                    progress.dismiss();

                }

            }



            @Override

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                Toast.makeText(QrGenActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();



            }

        });

        wb.loadUrl(url);

    }

}
