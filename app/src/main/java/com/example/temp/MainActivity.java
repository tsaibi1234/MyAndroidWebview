package com.example.temp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = findViewById(R.id.webview);
        myWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.loadUrl("https://tsaibi1234.blogspot.com/2023/05/android-webview-button-click.html");
    }


    class CustomWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            final Uri uri = Uri.parse(url);
            return handleUri(uri);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            return handleUri(uri);
        }

        private boolean handleUri(final Uri uri) {
            Log.i(TAG, "Uri = " + uri);
            final String host = uri.getHost();
            final String scheme = uri.getScheme();
            return false;
//            // Based on some condition you need to determine if you are going to load the url
//            // in your web view itself or in a browser.
//            // You can use `host` or `scheme` or any part of the `uri` to decide.
//            if (/* any condition */) {
//                // Returning false means that you are going to load this url in the webView itself
//                return false;
//            } else {
//                // Returning true means that you need to handle what to do with the url
//                // e.g. open web page in a Browser
//                return true;
//            }
        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}